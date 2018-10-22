package bertrand.myopengl.OpenGL;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import bertrand.myopengl.Tool.Vec3;
import bertrand.myopengl.Tool.VectorMath;

public class ObjectModel {
        public ObjectModel() {
                position = new Vec3(0,0,0);
                rotation = new Vec3(0,0,0);
                scale = new Vec3(1,1,1);
        }

        public final void init(final float[] vectors ,  final int[] indices, final AbstractShader s) {
                fragments = ByteBuffer.allocateDirect(vectors.length * 4);
                fragments.order(ByteOrder.nativeOrder());
                fragments.asFloatBuffer().put(vectors);
                fragments.position(0);
                this.indices = ByteBuffer.allocateDirect(indices.length * 4);
                this.indices.order(ByteOrder.nativeOrder());
                this.indices.asIntBuffer().put(indices);
                this.indices.position(0);
                shader = s;

                vao = GPU.createVertexArrayObject();
                GPU.loadFragmentBuffer(this.fragments);
                GPU.loadIndecisBuffer(this.indices);
                shader.enableVertexAttribArray();

                GLES.glBindVertexArray(0);
                GLES.glBindBuffer(GLES.GL_ARRAY_BUFFER,0);
                GLES.glBindBuffer(GLES.GL_ELEMENT_ARRAY_BUFFER,0);
        }

        private AbstractShader shader;
        private int vao = 0;

        private ByteBuffer fragments;
        private ByteBuffer indices;

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
                GPU.draw(vao, indices.asIntBuffer().capacity());
        }

        private void prepareToDraw(@NotNull AbstractShader shader, float[] modelVieMatrix) {
                GLES.glUseProgram(shader.program);
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

