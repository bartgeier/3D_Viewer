package bertrand.myopengl.OpenGL;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import bertrand.myopengl.Tool.Vec3;
import bertrand.myopengl.Tool.VectorMath;

public class ColoredModel {
        public ColoredModel() {
                position = new Vec3(0,0,0);
                rotation = new Vec3(0,0,0);
                scale = new Vec3(1,1,1);
        }

        public void init (
                final int[] indices,
                final float[] positions,
                final float[] colors,
                final float[] normals,
                final AbstractShader s) {
                vao = GPU.createVertexArrayObject();
                vbos = new int[4];
                vbos[0] = GPU.loadIndecisBuffer(allocateBuffer(indices));
                vbos[1] = GPU.loadFragmentBuffer(s.a.position, 3, allocateBuffer(positions));
                vbos[2] = GPU.loadFragmentBuffer(s.a.color, 4, allocateBuffer(colors));
                vbos[3] = GPU.loadFragmentBuffer(s.a.normal, 3, allocateBuffer(normals));
                GLES.glBindVertexArray(0);

                shader = s;
                indicesCount = indices.length;
        }


        private ByteBuffer allocateBuffer(float[] array) {
                ByteBuffer buffer;
                buffer = ByteBuffer.allocateDirect(array.length * 4);
                buffer.order(ByteOrder.nativeOrder());
                buffer.asFloatBuffer().put(array);
                buffer.position(0);
                return buffer;
        }
        private ByteBuffer allocateBuffer(int[] array) {
                ByteBuffer buffer;
                buffer = ByteBuffer.allocateDirect(array.length * 4);
                buffer.order(ByteOrder.nativeOrder());
                buffer.asIntBuffer().put(array);
                buffer.position(0);
                return buffer;
        }

        private AbstractShader shader;
        private int vao = 0;
        private int[] vbos;
        private int indicesCount;

        public Vec3 position;
        public Vec3 rotation;
        public Vec3 scale;

        public static float[] projectionMatrix = new float[16];

        private float[] modelMatrix() {
                float[] matrix = new float[16];
                Matrix.setIdentityM(matrix, 0);
                Matrix.translateM(matrix,0,position.x, position.y, position.z);
                Matrix.rotateM(matrix,0, rotation.x, 1, 0, 0);
                Matrix.rotateM(matrix,0, rotation.y, 0, 1, 0);
                Matrix.rotateM(matrix,0, rotation.z,  0, 0, 1);
                Matrix.scaleM(matrix,0, scale.x, scale.y, scale.z);
                return matrix;
        }

        public void updateWithDelta(float dt_ms) {

        }

        public static void renderBackground() {
                GLES.glClearColor(0.8f,0.5f,0.0f,1.0f);
                GLES.glClear(GLES.GL_COLOR_BUFFER_BIT |GLES.GL_DEPTH_BUFFER_BIT);
                GLES.glEnable(GLES.GL_DEPTH_TEST);
                GLES.glEnable(GLES.GL_CULL_FACE);
        }

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
                //GPU.draw(vao, indices.asIntBuffer().capacity());
                GPU.draw(vao, indicesCount);
        }

        private void prepareToDraw(@NotNull AbstractShader shader, float[] modelVieMatrix) {
                GLES.glUseProgram(shader.programID);
                GLES.glUniformMatrix4fv(
                        shader.u.modelViewMatrix,
                        1,
                        false,
                        modelVieMatrix,
                        0
                );
                GLES.glUniformMatrix4fv(
                        shader.u.projectionMatrix,
                        1,
                        false,
                        projectionMatrix,
                        0
                );

                final float red = 1;
                final float green = 1;
                final float blue = 1;
                GLES.glUniform3f(shader.u.lightAmbientColor, red, green, blue);
                GLES.glUniform3f(shader.u.lightAmbientColor, red, green, blue);

                GLES.glUniform1f(shader.u.lightAmbientIntens,0.1f);
                Vec3 lightDirection = VectorMath.vector3Normalize(new Vec3(0,1f,-1));
                GLES.glUniform3f(
                        shader.u.lightDirection,
                        lightDirection.x,
                        lightDirection.y,
                        lightDirection.z);
                GLES.glUniform1f(shader.u.lightDiffuseIntens,0.7f);

                GLES.glUniform1f(shader.u.matSpecularIntensity,2.0f);
                GLES.glUniform1f(shader.u.shininess,8.0f);
        }

}

