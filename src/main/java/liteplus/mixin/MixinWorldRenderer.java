package liteplus.mixin;

import liteplus.utils.Capture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer implements Capture {
    private static final int MOON_PHASES = 0;
    private static final int SUN = 0;
    @Shadow
    @Final
    private MinecraftClient client;

   /* @Override
    public Frustum capturedFrustum() {
        return null;
    }

    */

    @Shadow @Nullable private ClientWorld world;

    @Shadow protected abstract void renderEndSky(MatrixStack matrices);

    @Shadow @Nullable private VertexBuffer lightSkyBuffer;
    @Shadow @Nullable private VertexBuffer starsBuffer;
    @Shadow @Nullable private VertexBuffer darkSkyBuffer;
    @Unique private Frustum frustum;

    @ModifyVariable(
            method = "render",
            at = @At(value = "INVOKE",
                    target = "net/minecraft/client/render/BackgroundRenderer.render(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/world/ClientWorld;IF)V")
    )



    private Frustum captureFrustrum(Frustum frustum2) {
        return frustum = frustum2;
    }

    @Override
    public Frustum capturedFrustum() {
        return frustum;
    }

    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At("HEAD"), cancellable = true)
    private void preRenderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        // Cancels sky rendering when the camera is submersed underwater.
        // This prevents the sky from being visible through chunks culled by Sodium's fog occlusion.
        // Fixes https://bugs.mojang.com/browse/MC-152504.
        // Credit to bytzo for noticing the change in 1.18.2.
        if (camera.getSubmersionType() == CameraSubmersionType.WATER) {
            ci.cancel();
        }






/*
            runnable.run();
            if (!bl) {
                CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();

                if (this.client.world.getDimensionEffects().getSkyType() == DimensionEffects.SkyType.END) {
                    this.renderEndSky(matrices);
                } else if (this.client.world.getDimensionEffects().getSkyType() == DimensionEffects.SkyType.NORMAL) {
                    RenderSystem.disableTexture();
                    Vec3d vec3d = this.world.getSkyColor(this.client.gameRenderer.getCamera().getPos(), tickDelta);
                    float f = (float) vec3d.x;
                    float g = (float) vec3d.y;
                    float h = (float) vec3d.z;
                    BackgroundRenderer.setFogBlack();
                    BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                    RenderSystem.depthMask(false);
                    RenderSystem.setShaderColor(f, g, h, 0.4F);
                    Shader shader = RenderSystem.getShader();
                    this.lightSkyBuffer.bind();
                    this.lightSkyBuffer.draw(matrices.peek().getPositionMatrix(), projectionMatrix, shader);
                    VertexBuffer.unbind();
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    float[] fs = this.world.getDimensionEffects().getFogColorOverride(this.world.getSkyAngle(tickDelta), tickDelta);
                    float i;
                    float k;
                    float o;
                    float p;
                    float q;
                    if (fs != null) {
                        RenderSystem.setShader(GameRenderer::getPositionColorShader);
                        RenderSystem.disableTexture();
                        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                        matrices.push();
                        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                        i = MathHelper.sin(this.world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(i));
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
                        float j = fs[0];
                        k = fs[1];
                        float l = fs[2];
                        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
                        bufferBuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(j, k, l, fs[3]).next();
                        boolean m = true;

                        for (int n = 0; n <= 16; ++n) {
                            o = (float) n * 6.2831855F / 16.0F;
                            p = MathHelper.sin(o);
                            q = MathHelper.cos(o);
                            bufferBuilder.vertex(matrix4f, p * 120.0F, q * 120.0F, -q * 40.0F * fs[3]).color(fs[0], fs[1], fs[2], 0.0F).next();
                        }

                        BufferRenderer.drawWithShader(bufferBuilder.end());
                        matrices.pop();
                    }

                    RenderSystem.enableTexture();
                    RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
                    matrices.push();
                    i = 4.0F - this.world.getRainGradient(tickDelta);
                    RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, i);
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(this.world.getSkyAngle(tickDelta) * 360.0F));
                    Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
                    k = 30.0F;
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, SUN);
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                    bufferBuilder.vertex(matrix4f2, -k, 100.0F, -k).texture(0.0F, 0.0F).next();
                    bufferBuilder.vertex(matrix4f2, k, 100.0F, -k).texture(1.0F, 0.0F).next();
                    bufferBuilder.vertex(matrix4f2, k, 100.0F, k).texture(1.0F, 1.0F).next();
                    bufferBuilder.vertex(matrix4f2, -k, 100.0F, k).texture(0.0F, 1.0F).next();
                    BufferRenderer.drawWithShader(bufferBuilder.end());
                    k = 20.0F;
                    RenderSystem.setShaderTexture(0, MOON_PHASES);
                    int r = this.world.getMoonPhase();
                    int s = r % 4;
                    int m = r / 4 % 2;
                    float t = (float) (s + 0) / 4.0F;
                    o = (float) (m + 0) / 2.0F;
                    p = (float) (s + 1) / 4.0F;
                    q = (float) (m + 1) / 2.0F;
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                    bufferBuilder.vertex(matrix4f2, -k, -100.0F, k).texture(p, q).next();
                    bufferBuilder.vertex(matrix4f2, k, -100.0F, k).texture(t, q).next();
                    bufferBuilder.vertex(matrix4f2, k, -100.0F, -k).texture(t, o).next();
                    bufferBuilder.vertex(matrix4f2, -k, -100.0F, -k).texture(p, o).next();
                    BufferRenderer.drawWithShader(bufferBuilder.end());
                    RenderSystem.disableTexture();
                    float u = this.world.method_23787(tickDelta) * i;
                    if (u > 0.0F) {
                        RenderSystem.setShaderColor(u, u, u, u);
                        BackgroundRenderer.clearFog();
                        this.starsBuffer.bind();
                        this.starsBuffer.draw(matrices.peek().getPositionMatrix(), projectionMatrix, GameRenderer.getPositionShader());
                        VertexBuffer.unbind();
                        runnable.run();
                    }

                    RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                    RenderSystem.disableBlend();
                    matrices.pop();
                    RenderSystem.disableTexture();
                    RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                    double d = this.client.player.getCameraPosVec(tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight(this.world);
                    if (d < 0.0D) {
                        matrices.push();
                        matrices.translate(0.0D, 12.0D, 0.0D);
                        this.darkSkyBuffer.bind();
                        this.darkSkyBuffer.draw(matrices.peek().getPositionMatrix(), projectionMatrix, shader);
                        VertexBuffer.unbind();
                        matrices.pop();
                    }

                    if (this.world.getDimensionEffects().isAlternateSkyColor()) {
                        RenderSystem.setShaderColor(f * 0.0F + 0.04F, g * 0.0F + 0.04F, h * 0.0F + 0.1F, 1.0F);
                    } else {
                        RenderSystem.setShaderColor(f, g, h, 1.0F);
                    }

                    RenderSystem.enableTexture();
                    RenderSystem.depthMask(true);
                }

            }

 */



    }








/*
    @Inject(method = "render", at = @At("HEAD"))
    private void reset(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera,
                       GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f,
                       CallbackInfo ci) {
        ChunkCache.resetCaches();
    }
*/



}