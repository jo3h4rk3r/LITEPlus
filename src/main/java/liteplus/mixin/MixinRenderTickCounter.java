package liteplus.mixin;

import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static liteplus.mods.Overclock.TimerActive;

@Mixin(RenderTickCounter.class)
public class MixinRenderTickCounter {
    @Shadow
    public float lastFrameDuration;

    public int ticksThisFrame;

    @Shadow
    public float tickDelta;

    @Shadow
    private long prevTimeMillis;

    @Mutable
    @Final
    @Shadow
    private float tickTime;

    @Inject(at = @At("HEAD"), method = "beginRenderTick", cancellable = true)
    public void beginRenderTick(long long_1, CallbackInfoReturnable<Integer> cir) {


        if (TimerActive) {
            this.lastFrameDuration = (long_1 - this.prevTimeMillis) / this.tickTime;
            lastFrameDuration *= 20;
            this.prevTimeMillis = 1;
            this.tickDelta += this.lastFrameDuration;
            this.ticksThisFrame = (int) this.tickDelta;
            this.tickDelta -= this.ticksThisFrame;

        }
    }
}