package bertrand.myopengl.Models;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

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
                vbos[1] = GPU.loadAttribute(s.a_Position, 3, Arr.allocateBuffer(positions));
                vbos[2] = GPU.loadAttribute(s.a_Color, 4, Arr.allocateBuffer(colors));
                vbos[3] = GPU.loadAttribute(s.a_Normal, 3, Arr.allocateBuffer(normals));
                GPU.vertexArray0();
                shader = s;
                this.vao = vao;
                this.indicesCount = indices.length;
        }

        public void cleanUp() {
                shader.cleanUp();
                super.cleanUp();
        }

        private ColoredShader shader;

        public void render(float[] parentModelViewMatrix) {
                float[] modelVieMatrix = new float[16];
                Matrix.multiplyMM(
                        modelVieMatrix,
                        0,
                        parentModelViewMatrix,
                        0,
                        modelMatrix(),
                        0
                );
                prepareToDraw(shader,modelVieMatrix);
                GPU.render(vao, indicesCount);
        }

        private void prepareToDraw(@NotNull ColoredShader shader, float[] modelViewMatrix) {
                GPU.useProgram(shader.programID);
                GPU.loadMatrix(shader.u_ModelViewMatrix, modelViewMatrix);
                GPU.loadMatrix(shader.u_ProjectionMatrix, projectionMatrix);

                Vec3 lightDirection = Vec3.normalize(0,-0.5f,-1);
                GPU.loadVec3(shader.u_Light_Direction, lightDirection);

                final float red = 1;
                final float green = 1;
                final float blue = 1;

                GPU.load3Float(shader.u_Light_Color, red, green, blue);
                GPU.loadFloat(shader.u_Light_AmbientIntens, 0.1f);
                GPU.loadFloat(shader.u_Light_DiffuseIntens, 0.7f);
                GPU.loadFloat(shader.u_MatSpecularIntensity, 2.0f);
                GPU.loadFloat(shader.u_Shininess, 8.0f);
        }
}

