package liteplus.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class MixinFuckingFog {

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void fuckFog(CallbackInfo ci) {





       // RenderSystem.setShaderFogColor(255, 0,0);




        //RenderSystem.
     //   System.out.println("Fog deleted");
            //ci.cancel();

    }



}
