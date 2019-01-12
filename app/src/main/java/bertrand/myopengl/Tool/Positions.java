package bertrand.myopengl.Tool;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

public class Positions {
        public static @NotNull final float[]
        boundingBox(@NotNull final float[] positions) {
                if (positions.length % 3 != 0) {
                        throw new AssertionError(
                                "Positions.boundingBox, positions.length % 3 "
                        );
                }
                float minX = positions[0];
                float minY = positions[1];
                float minZ = positions[2];
                float maxX = minX;
                float maxY = minY;
                float maxZ = minZ;
                for(int i = 0; i < positions.length; i += 3){
                        if (positions[i] < minX) {
                               minX = positions[i];
                        }
                        if (positions[i + 1] < minY) {
                                minY = positions[i + 1];
                        }
                        if (positions[i + 2] < minZ) {
                                minZ = positions[i + 2];
                        }
                        if (positions[i] > maxX) {
                                maxX = positions[i];
                        }
                        if (positions[i + 1] > maxY) {
                                maxY = positions[i + 1];
                        }
                        if (positions[i + 2] > maxZ) {
                                maxZ = positions[i + 2];
                        }
                }
                return new float[] {minX, minY, minZ, maxX, maxY, maxZ};
        }

        public static @NotNull final Vec3
        offset(@NotNull final float[] positions) {
                @NotNull final float[] boundingBox = boundingBox(positions);
                Vec3 offset = new Vec3();
                offset.x = (boundingBox[0] + boundingBox[3])/2;
                offset.y = (boundingBox[1] + boundingBox[4])/2;
                offset.z = (boundingBox[2] + boundingBox[5])/2;
                return offset;
        }

        public static @NotNull final float[]
        multiplyMatrix(
                @NotNull float[] transformationMatrix,
                @NotNull final float[] positions
        ) {
                if (positions.length % 3 != 0) {
                        throw new AssertionError(
                                "Positions.multiplyMatrix, positions.length % 3 "
                        );
                }
                final float[] newPositions = new float[positions.length];
                final float[] input = new float[4];
                final float[] output = new float[4];
                for (int i = 0; i < positions.length; i += 3) {
                        input[0] = positions[i];
                        input[1] = positions[i + 1];
                        input[2] = positions[i + 2];
                        input[3] = 1;
                        Matrix.multiplyMV(
                                output,
                                0,
                                transformationMatrix,
                                0,
                                input,
                                0
                        );
                        newPositions[i] = output[0];
                        newPositions[i + 1] = output[1];
                        newPositions[i + 2] = output[2];
                }
                return newPositions;
        }


}