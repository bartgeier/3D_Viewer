package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Tool.Vec3;

public class Render {
        public static void background(@NotNull final Box.BackGround backGround) {
                GPU.renderBackground(backGround.color);
        }

        public static void entitys(
                @NotNull final SparseArray<Box.Light> lights,
                @NotNull final SparseArray<Box.Body> bodys,
                @NotNull final SparseArray<Box.ShaderProgam> shaders
        ) {
                int size = bodys.size();
                for (int i = 0; i < size; i++) {
                        Box.Body body = bodys.valueAt(i);
                        Box.ShaderProgam shader = shaders.get(body.shader_type_ID);
                        Box.Light light = lights.valueAt(0);

                        GPU.useProgram(shader.programID);
                        GPU.loadMatrix(shader.u_ModelViewMatrix, body.modelViewMatrix);
                        Vec3 lightDirection = lights.valueAt(0).position.normalize();
                        GPU.loadVec3(shader.u_Light_Direction, lightDirection);
                        GPU.load3Float(
                                shader.u_Light_Color,
                                light.color.r,
                                light.color.g,
                                light.color.b
                        );
                        GPU.loadFloat(shader.u_Light_AmbientIntens, 0.2f);
                        GPU.loadFloat(shader.u_Light_DiffuseIntens, 0.7f);
                        GPU.loadFloat(shader.u_MatSpecularIntensity, 2.0f);
                        GPU.loadFloat(shader.u_Shininess, 8.0f);

                        switch(shader.shader_type_ID) {
                                case 0:
                                        break;
                                case 1:
                                        GPU.loadFloat(shader.u_Texture, 0);
                                        break;
                                default:
                                        break;
                        }
                        GPU.render(body.vao, body.indicesCount);
                }
        }
}
