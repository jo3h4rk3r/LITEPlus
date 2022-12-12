package liteplus.mixin;


import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import liteplus.mods.Crasher;
import liteplus.mods.DeathExplorer;
import liteplus.mods.Flight;
import liteplus.mods.Invisible;
import liteplus.utils.XoRoShiRoRandom;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.BiomeColorCache;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.ColorResolver;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static liteplus.screens.HudOptions.SkyColorSet;

@Mixin(ClientWorld.class)
public abstract class MixinClientWorld extends World {

    private static int deathTimer = 0;
    private static boolean isDead = false;

    protected MixinClientWorld(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }


    @Shadow
    protected abstract void addParticle(BlockPos pos, BlockState state, ParticleEffect parameters, boolean bl);


    @Shadow
    @Final
    private Object2ObjectArrayMap<ColorResolver, BiomeColorCache> colorCache;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private List<AbstractClientPlayerEntity> players;

    @Shadow
    public abstract void tickEntities();

    @Shadow
    public abstract CrashReportSection addDetailsToCrashReport(CrashReport report);

    private Random redirectRandomTickRandom() {
        return new XoRoShiRoRandom();
    }

    /**
     * @reason Avoid allocations, branch code out, early-skip some code
     * @author JellySquid
     */
    public void randomBlockDisplayTick(int xCenter, int yCenter, int zCenter, int radius, Random random, boolean spawnBarrierParticles, BlockPos.Mutable pos) {
        int x = xCenter + (random.nextInt(radius) - random.nextInt(radius));
        int y = yCenter + (random.nextInt(radius) - random.nextInt(radius));
        int z = zCenter + (random.nextInt(radius) - random.nextInt(radius));

        pos.set(x, y, z);

        BlockState blockState = this.getBlockState(pos);

        if (!blockState.isAir()) {
            this.performBlockDisplayTick(blockState, pos, random, spawnBarrierParticles);
        }

        if (!blockState.isFullCube(this, pos)) {
            this.performBiomeParticleDisplayTick(pos, random);
        }

        FluidState fluidState = blockState.getFluidState();

        if (!fluidState.isEmpty()) {
            this.performFluidDisplayTick(blockState, fluidState, pos, random);
        }
    }


    protected abstract void performBiomeParticleDisplayTick(BlockPos.Mutable pos, Random random);


    private void performBlockDisplayTick(BlockState blockState, BlockPos pos, Random random, boolean spawnBarrierParticles) {
        blockState.getBlock().randomDisplayTick(blockState, this, pos, (net.minecraft.util.math.random.Random) random);
        if (spawnBarrierParticles && blockState.isOf(Blocks.BARRIER)) {
            this.performBarrierDisplayTick(pos);
        }
    }

    private void performBarrierDisplayTick(BlockPos pos) {
        this.addParticle(ParticleTypes.AMBIENT_ENTITY_EFFECT, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
    }

    private void performFluidDisplayTick(BlockState blockState, FluidState fluidState, BlockPos pos, Random random) {
        fluidState.randomDisplayTick(this, pos, (net.minecraft.util.math.random.Random) random);
        ParticleEffect particleEffect = fluidState.getParticle();
        if (particleEffect != null && random.nextInt(10) == 0) {
            boolean solid = blockState.isSideSolidFullSquare(this, pos, Direction.DOWN);
            // FIXME: don't allocate here
            BlockPos blockPos = pos.down();
            this.addParticle(blockPos, this.getBlockState(blockPos), particleEffect, solid);
        }
    }

    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void getSkyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Vec3d> ci) {
        if (SkyColorSet == 1) {
            ci.setReturnValue(Vec3d.unpackRgb(Color.BLUE.getRGB()));
        } else if (SkyColorSet == 2) {
            ci.setReturnValue(Vec3d.unpackRgb(Color.YELLOW.getRGB()));
        } else if (SkyColorSet == 3) {
            ci.setReturnValue(Vec3d.unpackRgb(Color.GREEN.getRGB()));
        } else if (SkyColorSet == 4) {
            ci.setReturnValue(Vec3d.unpackRgb(Color.ORANGE.getRGB()));
        } else if (SkyColorSet == 5) {
            ci.setReturnValue(Vec3d.unpackRgb(Color.RED.getRGB()));
        } else if (SkyColorSet == 6) {
            ci.setReturnValue(Vec3d.unpackRgb(Color.MAGENTA.getRGB()));
        } else if (SkyColorSet == 7) {
            ci.setReturnValue(Vec3d.unpackRgb(Color.PINK.getRGB()));
        } else if (SkyColorSet == 8) {
            ci.setReturnValue(Vec3d.ZERO);
        }
    }

    @Inject(method = "getCloudsColor", at = @At("HEAD"), cancellable = true)
    public void getCloudsColor(float tickDelta, CallbackInfoReturnable<Vec3d> cir) {

        // REMEMBER DO FOG COLOR and CLOUD COLOR OPTIONS!

    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void onTick(CallbackInfo ci) {
        if (!(client.player == null)) {
            Crasher.onTick();
            Invisible.onTick();
            DeathExplorer.onTick();
            Flight.onTick();
        }
    }




}