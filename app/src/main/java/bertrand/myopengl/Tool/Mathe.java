package bertrand.myopengl.Tool;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Entitys.Box;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;
import static java.lang.StrictMath.toDegrees;

public class Mathe {
        /** Temporary memory for operations that need temporary matrix data. */
        private final static float[] sTemp = new float[32];

        public static Vec3 eulerAngels(
                @NotNull final float[] R
        ) {
                Vec3 v = new Vec3();
                v.z = -(float)toDegrees(atan2(R[4],R[0]));
                v.y = -(float)toDegrees(atan2(-R[8],sqrt(pow(R[9],2)+pow(R[10],2))));
                v.x = -(float)toDegrees(atan2(R[9],R[10]));
                if (v.z < 0) {
                        v.z = 360 + v.z;
                }
                if (v.y < 0) {
                        v.y = 360 + v.y;
                }
                if (v.x < 0) {
                        v.x = 360 + v.x;
                }
                v.x = v.x % 360;
                v.y = v.y % 360;
                v.z = v.z % 360;
                return v;
        }

        public static void rotationXYZ(
                @NotNull float[] rotationMatrix,
                final float x,
                final float y,
                final float z
        ) {
                Matrix.setIdentityM(rotationMatrix, 0);
                Matrix.rotateM(rotationMatrix,0, x, 1, 0, 0);
                Matrix.rotateM(rotationMatrix,0, y, 0, 1, 0);
                Matrix.rotateM(rotationMatrix,0, z, 0, 0, 1);
        }

        public static void rotationDeltaXYZ(
                @NotNull float[] rotationMatrix,
                final float dx,
                final float dy,
                final float dz
        ) {
                Matrix.rotateM(rotationMatrix,0, dx, 1, 0, 0);
                Matrix.rotateM(rotationMatrix,0, dy, 0, 1, 0);
                Matrix.rotateM(rotationMatrix,0, dz, 0, 0, 1);
        }


        /* x *= m */
        public static void multiplyMM(float[] x, float[] m) {
                synchronized(sTemp) {
                        Matrix.multiplyMM(sTemp, 0, x,0, m, 0);
                        System.arraycopy(sTemp, 0, x, 0, 16);
                }
        }


        public static float adjustDistance(
                float distance,
                final float factor,
                final float offset
        ) {
                float r = abs(distance);
                final float f = abs(factor) - 1;
                float o = abs(offset);
                if (f < 0) {
                        /* shorten route */
                        o = -o;
                }
                float result = r + r * f + o;
                if (result < 0 ) {
                        /* zero-crossing */
                        result = 0f;
                }
                return result;
        }

        public static float Tx(final float translationMatrix[]) {
                return translationMatrix[12];
        }

        public static float Ty(final float translationMatrix[]) {
                return translationMatrix[13];
        }

        public static float Tz(final float translationMatrix[]) {
                return translationMatrix[14];
        }

        public static void Tz(final float translationMatrix[], final float z) {
                translationMatrix[14] = z;
        }

        public static void translationXYZ(
                final float T[],
                final float x,
                final float y,
                final float z
        ) {
                Matrix.setIdentityM(T, 0);
                Matrix.translateM(T,0,x, y, z);
        }

        public static void rotateM_withQuaternion(
                float[] m,
                float w, float x, float y, float z
        ) {
                synchronized(sTemp) {
                        quatToMatrix(sTemp,  w, x, y, z);
                        Matrix.multiplyMM(sTemp, 16, m, 0, sTemp, 0);
                        System.arraycopy(sTemp, 16, m, 0, 16);
                }
        }

        /* https://www.euclideanspace.com */
        /* /maths/geometry/rotations/conversions/quaternionToMatrix/index.htm */
        public static void quatToMatrix(
                final float R[],
                final float w,
                final float x,
                final float y,
                final float z
        ) {
                final float ww = w * w;
                final float xx = x * x;
                final float yy = y * y;
                final float zz = z * z;
                final float xy = x * y;
                final float zw = z * w;
                final float xz = x * z;
                final float yw = y * w;
                final float yz = y * z;
                final float xw = x * w;
                // invs (inverse square length)
                // is only required if quaternion is not already normalised
                final float invs = 1 / (xx + yy + zz + ww);
                // since ww + xx + yy + zz = 1 / invs * invs
                R[0] = (xx - yy - zz + ww) * invs;
                R[5] = (-xx + yy - zz + ww) * invs;
                R[10] = (-xx - yy + zz + ww) * invs;
                R[15] = 1;

                R[1] = 2.0f * (xy + zw) * invs;
                R[4] = 2.0f * (xy - zw) * invs;

                R[2] = 2.0f * (xz - yw) * invs;
                R[8] = 2.0f * (xz + yw) * invs;

                R[6] = 2.0f * (yz + xw) * invs;
                R[9] = 2.0f * (yz - xw) * invs;

                R[3] = 0;
                R[7] = 0;
                R[11] = 0;
                R[12] = 0;
                R[13] = 0;
                R[14] = 0;
        }





}
