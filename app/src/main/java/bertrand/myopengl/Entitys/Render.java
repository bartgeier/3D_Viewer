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

        public static void entitys(
                @NotNull final float[] parentModelViewMatrix,
                @NotNull final SparseArray<Box.Light> lights,
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull final SparseArray<Box.Shader> shaders
        ) {
                Box.Shader shader = null;
                int lastShader_id = -1;
                final float[] modelViewMatrix = new float[16];
                for (int i = 0; i < locations.size(); i++) {
                        final Box.Location location = locations.at(i);

                        Matrix.multiplyMM(
                                modelViewMatrix,
                                0,
                                parentModelViewMatrix,
                                0,
                                location.transformationMatrix,
                                0
                        );

                        if (location.shader_ID != lastShader_id) {
                                lastShader_id = location.shader_ID;
                                shader = shaders.atId(location.shader_ID);
                                GPU.useProgram(shader.programID);
                                GPU.loadFloat(shader.u_Light_AmbientIntens, 0.2f);
                                GPU.loadFloat(shader.u_Light_DiffuseIntens, 0.7f);
                                GPU.loadFloat(shader.u_MatSpecularIntensity, 2.0f);
                                GPU.loadFloat(shader.u_Shininess, 8.0f);
                                switch (shader.shader_type_ID) {
                                        case ShaderType.Colored.shader_type_ID:
                                                break;
                                        case ShaderType.Textured.shader_type_ID:
                                                //GPU.loadFloat(shader.u_Texture, 0);
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
                        GPU.loadMatrix(shader.u_ModelViewMatrix, modelViewMatrix);
                        GPU.render(location.vao, location.indicesCount);
                }
        }
}
