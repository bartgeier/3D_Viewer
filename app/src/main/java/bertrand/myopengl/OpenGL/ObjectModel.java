package bertrand.myopengl.OpenGL;


import android.opengl.Matrix;
import android.renderscript.Float3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import bertrand.myopengl.Tool.VectorMath;

public abstract class ObjectModel {
        public class Position {
                public float x = 0;
                public float y = 0;
                public float z = 0;
        }

        public ObjectModel() {
                position = new Position();
                rotation = new Position();
                scale = new Position();
        }

        public final void init(final float[] vectors ,  final byte[] indices, final AbstractShader s) {
                this.fragments = ByteBuffer.allocateDirect(vectors.length * 4);
                this.fragments.order(ByteOrder.nativeOrder());
                this.fragments.asFloatBuffer().put(vectors);
                this.fragments.position(0);

                this.indices = ByteBuffer.allocateDirect(indices.length);
                this.indices.order(ByteOrder.nativeOrder());
                this.indices.put(indices);
                this.indices.position(0);

                this.shader = s;

                gpu = shader.createVertexArrayObject();

                shader.loadFragmentBuffer(this.fragments);
                shader.loadIndecisBuffer(this.indices);
                shader.enableVertexAttribArray();

                GLES.glBindVertexArray(0);
                GLES.glBindBuffer(GLES.GL_ARRAY_BUFFER,0);
                GLES.glBindBuffer(GLES.GL_ELEMENT_ARRAY_BUFFER,0);
        }

        public AbstractShader shader;
        public int gpu = 0;

        public ByteBuffer fragments;
        public ByteBuffer indices;

        public Position position;
        public Position rotation;
        public Position scale;

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

        public abstract void updateWithDelta(float dt_ms);

        public static void renderBackground() {
                GLES.glClearColor(0.8f,0.5f,0.0f,1.0f);
                GLES.glClear(GLES.GL_COLOR_BUFFER_BIT |GLES.GL_DEPTH_BUFFER_BIT);
                //GLES.glEnable(GLES.GL_DEPTH_TEST);
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
                shader.draw(gpu, indices.capacity());
        }

        private void prepareToDraw(AbstractShader shader, float[] modelVieMatrix) {
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
                Float3 lightDirection = VectorMath.vector3Normalize(new Float3(0,1f,-1));
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

