package liteplus.mixin;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher {
    @Redirect(method = "drawShadowVertex", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;vertex(Lorg/joml/Matrix4f;FFF)Lnet/minecraft/client/render/VertexConsumer;"))
    private static VertexConsumer preWriteVertex(VertexConsumer instance, Matrix4f matrix, float x, float y, float z) {
        // FIX: Render the shadow slightly above the block to fix clipping issues
        // This happens in vanilla too, but is exacerbated by the Compact Vertex Format option.
        return instance.vertex(matrix, x, y + 0.001f, z);
    }

}