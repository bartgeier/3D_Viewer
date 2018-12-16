package bertrand.myopengl.Camera;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Tool.Vec3;

public class Camera {
        static private float[] projectionMatrix = new float[16];
        static private float[] position = new float[16];
        static private boolean isInit = false;

        static private float fovyZoomAngle = 85f;
        static private float aspectRatio = 1f;
        static private float near = 0.1f;
        static private float far = 150f;
        static private Vec3 pos = new Vec3(0,0,0);
        static private Vec3 rotation = new Vec3(0,0,0);

        public static void init() {
                if(isInit) {
                        return;
                } else {
                        isInit = true;
                }
                pos.z = -5;
                rotation.x = 20;

                calculateProjectionMatrix();
                calculatePosition();
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

        private static void calculatePosition() {
                /* calculate ModelViewMatrix */
                Matrix.setIdentityM(position, 0);
                Matrix.translateM(position,0,pos.x, pos.y, pos.z);
                Matrix.rotateM(position,0, rotation.x, 1, 0, 0);
                Matrix.rotateM(position,0, rotation.y, 0, 1, 0);
                Matrix.rotateM(position,0, rotation.z,  0, 0, 1);
        }

        @NotNull
        public static float[] projectionMatrix() {
                return projectionMatrix;
        }

        @NotNull
        public static float[] position() {
                return position;
        }

        public static void position(float x, float y, float z) {
                pos.x = x;
                pos.y = y;
                pos.z = z;
                calculatePosition();
        }
        public static void rotation(float x, float y, float z) {
                rotation.x = x;
                rotation.y = y;
                rotation.z = z;
                calculatePosition();
        }

        public static void aspectRatio(float r) {
                aspectRatio = r;
                calculateProjectionMatrix();
        }
}
