package bertrand.myopengl.Entitys;

import android.opengl.Matrix;

import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.SparseArray.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.GPU;

public class Update {
        public static void spins(
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull final SparseArray<Box.Spin> spins,
                final float dt_ms
        ) {
                for (int i = 0; i < spins.size(); i++) {
                        Box.Spin spin = spins.at(i);
                        Box.Location l = locations.atId(spin.location_ID);
                        Matrix.rotateM(
                                l.TF,0,
                                dt_ms * 360/spin.period_ms,
                                spin.axis_x,spin.axis_y,spin.axis_z
                        );
                }
        }

        public static void swings(
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull final SparseArray<Box.Swing> swings,
                final float dt_ms
        ) {
                for (int i = 0; i < swings.size(); i++) {
                        Box.Swing swing = swings.at(i);
                        Box.Location l = locations.atId(swing.location_ID);

                        final float w = (float)(2*Math.PI/swing.period_ms); //Kreisfrequenz
                        swing.angle += (dt_ms*w + swing.phaseShift);
                        swing.angle %= 2*Math.PI;
                        final float deflection = swing.amplitude * (float)Math.sin(swing.angle);
                        final float delta = deflection - Mathe.Tx(l.TF);
                        Matrix.translateM(
                                l.TF,0,
                                delta, 0, 0
                        );
                }
        }

        public static float[] matrix = new float[16];
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

