package liteplus.mixin;


import liteplus.mods.ResourceLimiter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    /**
     Limits system resources
     */
    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void onRender(CallbackInfo callbackInfo) {
        if (!ResourceLimiter.checkForRender()) {
            callbackInfo.cancel();
        }
    }

    /**
     cancels world rendering under certain conditions
     */
    @Inject(at = @At("HEAD"), method = "renderWorld", cancellable = true)
    private void onRenderWorld(CallbackInfo callbackInfo) {

        if (client.getOverlay() instanceof SplashOverlay) {
            callbackInfo.cancel();
        }
    }
}