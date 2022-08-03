package liteplus.utils;

//import org.joml.FrustumIntersection;
//import org.joml.Matrix4f;
//import org.joml.Vector3f;

/**
 * Default frustum implementation which extracts planes from a model-view-projection matrix.
 */
public class JomlFrustum implements Frustum {
    @Override
    public Visibility testBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return null;
    }
   /* private final FrustumIntersection intersection;
    private final Vector3f offset;


    public JomlFrustum(Matrix4f matrix, Vector3f offset) {
        this.intersection = new FrustumIntersection(matrix, false);
        this.offset = offset;

    }


    @Override
    public Visibility testBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return switch (this.intersection.intersectAab(minX - this.offset.x, minY - this.offset.y, minZ - this.offset.z,
                maxX - this.offset.x, maxY - this.offset.y, maxZ - this.offset.z)) {
            case FrustumIntersection.INTERSECT -> Visibility.INTERSECT;
            case FrustumIntersection.INSIDE -> Visibility.INSIDE;
            default -> Visibility.OUTSIDE;
        };
    }
    GAY
    */

}
