package bertrand.myopengl.Models;

import android.graphics.Bitmap;
import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Light.Light;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Shaders.TexturedShader;
import bertrand.myopengl.Tool.Arr;
import bertrand.myopengl.Tool.Vec3;

public class TexturedModel extends RawModel {
        private TexturedShader shader;
        private int texId;

        public TexturedModel() {}
        public TexturedModel(
                @NotNull final TexturedShader s,
                @NotNull final Bitmap bitmap,
                @NotNull final int[] indices,
                @NotNull final float[] positions,
                @NotNull final float[] texCoords,
                @NotNull final float[] normals
        ) {
                int vao = GPU.createVertexArrayObject();
                int[] vbos = new int[4];
                vbos[0] = GPU.loadIndecisBuffer(Arr.allocateBuffer(indices));
                vbos[1] = GPU.loadAttribute(TexturedShader.a_Position, 3, Arr.allocateBuffer(positions));
                vbos[2] = GPU.loadAttribute(TexturedShader.a_TexCoord, 2, Arr.allocateBuffer(texCoords));
                vbos[3] = GPU.loadAttribute(TexturedShader.a_Normal, 3, Arr.allocateBuffer(normals));
                texId = GPU.loadTexture(bitmap);
                GPU.vertexArray0();
                shader = s;
                this.vao = vao;
                this.vbos = vbos;
                this.indicesCount = indices.length;
        }

        public void cleanUp() {
                GPU.deleteTextureID(texId);
                super.cleanUp();
        }

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
                @NotNull final TexturedShader shader,
                @NotNull final float[] modelViewMatrix,
                @NotNull Light light
        ) {
                GPU.useProgram(shader.programID);
                GPU.loadMatrix(shader.u_ModelViewMatrix, modelViewMatrix);

                //Vec3 lightDirection = Vec3.normalize(0,-0.5f,-1);
                Vec3 lightDirection = light.position.normalize();
                GPU.loadVec3(shader.u_Light_Direction, lightDirection);

                GPU.load3Float(shader.u_Light_AmbientColor, light.color.r, light.color.g, light.color.b);
                GPU.loadFloat(shader.u_Light_AmbientIntens, 0.2f);
                GPU.loadFloat(shader.u_Light_DiffuseIntens, 0.7f);
                GPU.loadFloat(shader.u_MatSpecularIntensity, 2.0f);
                GPU.loadFloat(shader.u_Shininess, 8.0f);
                GPU.loadFloat(shader.u_Texture, 0);
        }
}

