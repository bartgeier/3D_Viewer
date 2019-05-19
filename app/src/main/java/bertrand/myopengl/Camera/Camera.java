package bertrand.myopengl.Camera;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Tool.Vec3;

import static java.lang.StrictMath.abs;

public class Camera {
        static private float fovyZoomAngle = 85f; // degrees
        static private float aspectRatio = 1f;
        static private float near = 0.1f; // always bigger then 0
        static private float far = 300f;
        static private float[] projectionMatrix = new float[16];

        static private Vec3 pos = new Vec3(0,0,0);
        static private Vec3 rotation = new Vec3(0,0,0); // degrees
        static private float[] translationMatrix = new float[16];
        static private float[] rotationMatrix = new float[16];
        static private float[] modelMatrix = new float[16];

        public static void init() {
                Matrix.setIdentityM(translationMatrix, 0);
                Matrix.setIdentityM(rotationMatrix, 0);
                calculateProjectionMatrix();
                calculateModelMatrix();
        }

        @NotNull
        public static float[] projectionMatrix() {
                return projectionMatrix;
        }

        @NotNull
        public static float[] modelMatrix() {
                return modelMatrix;
        }

        @NotNull
        public static float[] rotationMatrix() {
                return rotationMatrix;
        }

        public static void setDistance(final float factor) {
                final float f = 1/(100*factor);
                final float offset = 0.01f;
                float distance = abs(pos.z);
                if (factor > 1) {
                        /* approching */
                        distance -=  (offset + distance * f);
                } else if (factor < 1) {
                        /* increase distance */
                        distance +=  (offset + distance * f);
                } else {
                        return;
                }
                if (pos.z > 0) {
                        pos.z = 0;
                }
                translation(pos.x, pos.y, -distance);
        }

        public static void translation(float x, float y, float z) {
                pos.x = x;
                pos.y = y;
                pos.z = z;
                Matrix.setIdentityM(translationMatrix, 0);
                Matrix.translateM(translationMatrix,0,pos.x, pos.y, pos.z);
                calculateModelMatrix();
        }

        public static void rotation(float x, float y, float z) {
                rotation.x = x;
                rotation.y = y;
                rotation.z = z;
                Matrix.setIdentityM(rotationMatrix, 0);
                Matrix.rotateM(rotationMatrix,0, rotation.x, 1, 0, 0);
                Matrix.rotateM(rotationMatrix,0, rotation.y, 0, 1, 0);
                Matrix.rotateM(rotationMatrix,0, rotation.z,  0, 0, 1);
                calculateModelMatrix();
        }

        public static void rotation(float[] rotationMatrix) {
                Camera.rotationMatrix = rotationMatrix;
                calculateModelMatrix();
        }

        public static void aspectRatio(float r) {
                aspectRatio = r;
                calculateProjectionMatrix();
        }

        private static void calculateProjectionMatrix() {
                Matrix.perspectiveM(
                        projectionMatrix,
                        0,
                        fovyZoomAngle,
                        aspectRatio,
                        near,
                        far
                );
        }

        private static void calculateModelMatrix() {
                /* calculate ModelViewMatrix */
                Matrix.multiplyMM(
                        modelMatrix,
                        0,
                        translationMatrix,
                        0,
                        rotationMatrix,
                        0
                );
        }
}
