package liteplus.utils;

public interface FrustumAdapter {
    Frustum sodium$createFrustum();

    static Frustum adapt(net.minecraft.client.render.Frustum frustum) {
        return ((FrustumAdapter) frustum).sodium$createFrustum();
    }
}
