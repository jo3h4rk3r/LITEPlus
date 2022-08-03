package liteplus.mixin;


import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import liteplus.litepluscore;
import liteplus.screens.HudOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL32C;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements litepluscore.WindowHolder {
    private final LongArrayFIFOQueue fences = new LongArrayFIFOQueue();


    @Shadow
    @Final
    private Window window;

    @Override
    public Window getWindow() {
        return window;
    }


    @Inject(method = "render", at = @At("HEAD"))
    private void preRender(boolean tick, CallbackInfo ci) {
        if (HudOptions.testVar) {
            while (this.fences.size() > HudOptions.PreRenderFrameCount) {
                var fence = this.fences.dequeueLong();
                GL32C.glClientWaitSync(fence, GL32C.GL_SYNC_FLUSH_COMMANDS_BIT, Long.MAX_VALUE);
                GL32C.glDeleteSync(fence);
            }
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void postRender(boolean tick, CallbackInfo ci) {
        if (HudOptions.testVar) {
        var fence = GL32C.glFenceSync(GL32C.GL_SYNC_GPU_COMMANDS_COMPLETE, 0);

        if (fence == 0) {
            throw new RuntimeException("Failed to create fence object");
        }
        this.fences.enqueue(fence);
        }
    }


}