package bertrand.myopengl.Models;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Light.Light;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Shaders.ColoredShader;
import bertrand.myopengl.Tool.Arr;
import bertrand.myopengl.Tool.Vec3;

public class ColoredModel extends RawModel {
        public ColoredModel() {}
        public ColoredModel(
                final ColoredShader s,
                final int[] indices,
                final float[] positions,
                final float[] colors,
                final float[] normals
        ) {
                int vao = GPU.createVertexArrayObject();
                int[] vbos = new int[4];
                vbos[0] = GPU.loadIndecisBuffer(Arr.allocateBuffer(indices));
                vbos[1] = GPU.loadAttribute(ColoredShader.a_Position, 3, Arr.allocateBuffer(positions));
                vbos[2] = GPU.loadAttribute(ColoredShader.a_Color, 4, Arr.allocateBuffer(colors));
                vbos[3] = GPU.loadAttribute(ColoredShader.a_Normal, 3, Arr.allocateBuffer(normals));
                GPU.vertexArray0();
                shader = s;
                this.vao = vao;
                this.vbos = vbos;
                this.indicesCount = indices.length;
        }

        public void cleanUp() {
                super.cleanUp();
        }

        private ColoredShader shader;

        @Override
        public void render(@NotNull final float[] parentModelViewMatrix, @NotNull Light light) {
                float[] modelVieMatrix = new float[16];
                Matrix.multiplyMM(
                        modelVieMatrix,
                        0,
                        parentModelViewMatrix,
                        0,
                        modelMatrix(),
                        0
                );
                prepareToDraw(shader,modelVieMatrix, light);
                GPU.render(vao, indicesCount);
        }

        private static void prepareToDraw(
                @NotNull final ColoredShader shader,
                @NotNull final float[] modelViewMatrix,
                @NotNull Light light
        ) {
                GPU.useProgram(shader.programID);
                GPU.loadMatrix(shader.u_ModelViewMatrix, modelViewMatrix);

                //Vec3 lightDirection = Vec3.normalize(0,0.8f,-1);
                Vec3 lightDirection = light.position.normalize();
                GPU.loadVec3(shader.u_Light_Direction, lightDirection);

                GPU.load3Float(shader.u_Light_Color, light.color.r, light.color.g, light.color.b);
                GPU.loadFloat(shader.u_Light_AmbientIntens, 0.2f);
                GPU.loadFloat(shader.u_Light_DiffuseIntens, 0.7f);
                GPU.loadFloat(shader.u_MatSpecularIntensity, 2.0f);
                GPU.loadFloat(shader.u_Shininess, 8.0f);
        }
}

