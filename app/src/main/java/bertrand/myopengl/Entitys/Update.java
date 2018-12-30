package bertrand.myopengl.Entitys;

import android.opengl.Matrix;
import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.GPU;

public class Update {
        public static void locations(
                @NotNull final SparseArray<Box.Location> outputs,
                @NotNull final SparseArray<Box.Periode> ios,
                final float dt_ms
        ) {
                int size = outputs.size();
                for (int i = 0; i < size; i++) {
                        Box.Location out = outputs.valueAt(i);
                        Box.Periode io = ios.get(out.entity_ID);
                        switch(io.type) {
                                case ROTATE:
                                        io.angle += dt_ms * 360/io.period_ms;
                                        out.rotation.y = (float)io.angle;
                                        break;
                                case SWING:
                                        io.angle += dt_ms * 360/io.period_ms;
                                        io.angle %= 360;
                                        final double rad = Math.toRadians(io.angle);
                                        out.position.x = (float)(4 * Math.sin(rad));
                                        break;
                                case UNDEF:
                                        break;
                                default:
                                        break;
                        }
                }
        }

        public static void bodys(
                @NotNull final SparseArray<Box.Body> outputs,
                @NotNull final float[] parentModelViewMatrix,
                @NotNull final SparseArray<Box.Location> inputs

        ) {
                int size = outputs.size();
                for (int i = 0; i < size; i++) {
                        Box.Body out = outputs.valueAt(i);
                        Box.Location in = inputs.get(out.entity_ID);
                        float[] matrix = new float[16];
                        Matrix.setIdentityM(matrix, 0);
                        Matrix.translateM(matrix,0,in.position.x, in.position.y, in.position.z);
                        Matrix.rotateM(matrix,0, in.rotation.x, 1, 0, 0);
                        Matrix.rotateM(matrix,0, in.rotation.y, 0, 1, 0);
                        Matrix.rotateM(matrix,0, in.rotation.z,  0, 0, 1);
                        Matrix.scaleM(matrix,0, in.scale.x, in.scale.y, in.scale.z);
                        Matrix.multiplyMM(
                                out.modelViewMatrix,
                                0,
                                parentModelViewMatrix,
                                0,
                                matrix,
                                0
                        );
                }
        }

        /* curring partially applied function argument */
        public static void gpu_shader_projectionMatrix(
                @NotNull final SparseArray<Box.ShaderProgam> shaders,
                @NotNull final float[] projectionMatrix
        ) {
                int size = shaders.size();
                for (int i = 0; i < size; i++) {
                        Box.ShaderProgam shader = shaders.valueAt(i);
                        GPU.useProgram(shader.programID);
                        GPU.loadMatrix(shader.u_ProjectionMatrix, projectionMatrix);
                }
                GPU.useProgram(0);
        }

}

