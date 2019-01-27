package bertrand.myopengl.Entitys;

import android.opengl.Matrix;
import bertrand.myopengl.Tool.SparseArray.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.GPU;

public class Update {
        public static void periods(
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull final SparseArray<Box.Periode> periods,
                final float dt_ms
        ) {
                for (int i = 0; i < periods.size(); i++) {
                        Box.Periode periode = periods.at(i);
                        Box.Location l = locations.atId(periode.location_ID);
                        switch(periode.type) {
                                case ROTATE_X:
                                        periode.angle += dt_ms * 360/periode.period_ms;
                                        l.rotation.x = (float)periode.angle;
                                        break;
                                case ROTATE_Y:
                                        periode.angle += dt_ms * 360/periode.period_ms;
                                        l.rotation.y = (float)periode.angle;
                                        break;
                                case ROTATE_Z:
                                        periode.angle += dt_ms * 360/periode.period_ms;
                                        l.rotation.z = (float)periode.angle;
                                        break;
                                case SWING:
                                        periode.angle += dt_ms * 360/periode.period_ms;
                                        periode.angle %= 360;
                                        final double rad = Math.toRadians(periode.angle);
                                        l.position.x = (float)(4 * Math.sin(rad));
                                        break;
                                default:
                                        break;
                        }
                        Matrix.setIdentityM(l.transformationMatrix, 0);
                        Matrix.translateM(l.transformationMatrix,0,l.position.x, l.position.y, l.position.z);
                        Matrix.rotateM(l.transformationMatrix,0, l.rotation.x, 1, 0, 0);
                        Matrix.rotateM(l.transformationMatrix,0, l.rotation.y, 0, 1, 0);
                        Matrix.rotateM(l.transformationMatrix,0, l.rotation.z,  0, 0, 1);
                        Matrix.scaleM(l.transformationMatrix,0, l.scale.x, l.scale.y, l.scale.z);
                }
        }

        /* curring partially applied function argument */
        public static void gpu_shader_projectionMatrix(
                @NotNull final SparseArray<Box.Shader> shaders,
                @NotNull final float[] projectionMatrix
        ) {
                int size = shaders.size();
                for (int i = 0; i < size; i++) {
                        Box.Shader shader = shaders.at(i);
                        GPU.useProgram(shader.programID);
                        GPU.loadMatrix(shader.u_ProjectionMatrix, projectionMatrix);
                }
                GPU.useProgram(0);
        }

}

