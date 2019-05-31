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



}
