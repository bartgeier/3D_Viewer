package bertrand.myopengl.Entitys;

import android.opengl.Matrix;
import bertrand.myopengl.Tool.SparseArray.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.ShaderTypes.ShaderType;
import bertrand.myopengl.Tool.Vec3;

public class Render {
        public static void background(@NotNull final Box.BackGround backGround) {
                GPU.renderBackground(backGround.color);
        }

        public static float[] matrixA = new float[16];
        public static float[] matrixB = new float[16];
        public static float[] matrixC = new float[16];

        /* output global_TF relative to 0,0,0 */
        /* calculate the global transformation matrix from location_ID  */
        /* ATTENTION */
        /* Except the index from location_ID is the root index (index == 0) then */
        /* returns a identity matrix */
        /* That's allows us to move the Origin, the hole world and a camera attached to root */
        /* looks still at 0,0,0.  see MainRenderer.onTouchScreenMoving */
        public static void camera (
                @NotNull final float[] global_TF,
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull final int location_ID
        ) {
                Matrix.setIdentityM(global_TF,0);
                int index = locations.getIndex(location_ID);
                final float[] m = locations.at(index).TF.clone();
                while (index != 0) {
                        index = locations.at(index).parentIdx;
                        Matrix.multiplyMM(
                                global_TF,0,
                                locations.at(index).TF,0, // local transformation
                                m,0
                        );
                        System.arraycopy(
                                /* m = parentModelViewMatrix */
                                global_TF, 0,
                                m, 0,
                                global_TF.length //16
                        );
                }
        }

        public static void entitys(
                @NotNull final float[] parentModelViewMatrix,
                @NotNull final SparseArray<Box.Light> lights,
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull final SparseArray<Box.Shader> shaders
        ) {
                if (locations.size() == 0) {
                        return;
                }
                Box.Shader shader = null;
                int lastShader_id = -1;

                final Box.Location root = locations.at(0);
                Matrix.multiplyMM(
                        root.MV,
                        0,
                        parentModelViewMatrix,
                        0,
                        root.TF,
                        0
                );

                for (int i = 1; i < locations.size(); i++) {
                        final Box.Location l = locations.at(i);
                        Matrix.multiplyMM(
                                l.MV,
                                0,
                                locations.at(l.parentIdx).MV,
                                0,
                                l.TF,
                                0
                        );

                        if (l.shader_ID != lastShader_id) {
                                lastShader_id = l.shader_ID;
                                shader = shaders.atId(l.shader_ID);
                                GPU.useProgram(shader.programID);
                                GPU.loadFloat(shader.u_Light_AmbientIntens, 0.2f);
                                GPU.loadFloat(shader.u_Light_DiffuseIntens, 0.7f);
                                GPU.loadFloat(shader.u_MatSpecularIntensity, 2.0f);
                                GPU.loadFloat(shader.u_Shininess, 8.0f);
                                switch (shader.shader_type_ID) {
                                        case ShaderType.Colored.shader_type_ID:
                                                break;
                                        case ShaderType.Textured.shader_type_ID:
                                                GPU.loadInt(shader.u_Texture, 0);
                                                break;
                                        default:
                                                break;
                                }
                                Vec3 lightDirection = lights.at(0).position.normalize();
                                GPU.loadVec3(shader.u_Light_Direction, lightDirection);
                                GPU.load3Float(
                                        shader.u_Light_Color,
                                        lights.at(0).color.r,
                                        lights.at(0).color.g,
                                        lights.at(0).color.b
                                );
                        }
                        GPU.selectTexture(l.texId);
                        GPU.loadMatrix(shader.u_ModelViewMatrix, l.MV);
                        GPU.render(l.vao, l.indicesCount);
                }
        }


        public static void update_gui(
                @NotNull final float[] parentModelViewMatrix,
                @NotNull final SparseArray<Box.Location> locations
        ) {
                if (locations.size() == 0) {
                        return;
                }

                final Box.Location root = locations.at(0);
                Matrix.multiplyMM(
                        root.MV,
                        0,
                        parentModelViewMatrix,
                        0,
                        root.TF,
                        0
                );

                for (int i = 1; i < locations.size(); i++) {
                        final Box.Location l = locations.at(i);
                        Matrix.multiplyMM(
                                l.MV,
                                0,
                                locations.at(l.parentIdx).MV,
                                0,
                                l.TF,
                                0
                        );
                }
        }

        public static void guis(
                @NotNull final float[] parentModelViewMatrix,
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull final SparseArray<Box.Shader> shaders
        ) {
                if (locations.size() == 0) {
                        return;
                }
                Box.Shader shader = null;
                int lastShader_id = -1;

                final Box.Location root = locations.at(0);
                Matrix.multiplyMM(
                        root.MV,
                        0,
                        parentModelViewMatrix,
                        0,
                        root.TF,
                        0
                );


                for (int i = 1; i < locations.size(); i++) {
                        final Box.Location l = locations.at(i);
                        Matrix.multiplyMM(
                                l.MV,
                                0,
                                locations.at(l.parentIdx).MV,
                                0,
                                l.TF,
                                0
                        );

                        if (l.shader_ID != lastShader_id) {
                                lastShader_id = l.shader_ID;
                                shader = shaders.atId(l.shader_ID);
                                GPU.useProgram(shader.programID);
                                switch (shader.shader_type_ID) {
                                        case ShaderType.Quad.shader_type_ID:
                                                GPU.loadInt(shader.u_Texture, 0);
                                                break;
                                        default:
                                                break;
                                }
                        }
                        GPU.selectTexture(l.texId);
                        GPU.loadMatrix(shader.u_ModelViewMatrix, l.MV);
                        GPU.render(l.vao, l.indicesCount);
                }
        }


}
