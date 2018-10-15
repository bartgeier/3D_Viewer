package bertrand.myopengl.OpenGL;


import android.opengl.Matrix;
import android.renderscript.Float3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import bertrand.myopengl.Tool.VectorMath;


public class ObjectModel {
        public class Position {
                public float x = 0;
                public float y = 0;
                public float z = 0;
        }

        public ObjectModel() {
                matrix = new float[16];
                position = new Position();
                rotation = new Position();
                scale = new Position();
        }

        public final void init(final float[] vectors ,  final byte[] indices, final Shader shader) {
                this.fragments = ByteBuffer.allocateDirect(vectors.length * 4);
                this.fragments.order(ByteOrder.nativeOrder());
                this.fragments.asFloatBuffer().put(vectors);
                this.fragments.position(0);

                this.indices = ByteBuffer.allocateDirect(indices.length);
                this.indices.order(ByteOrder.nativeOrder());
                this.indices.put(indices);
                this.indices.position(0);

                this.shader = shader;
        }

        private static final int byte_per_element = 4;
        public static final int VERTEX_SIZE = 3; //x,y,z
        public static final int VERTEX_OFFSET = 0;
        public static final int COLOR_SIZE = 4; //r,g,b,a
        public static final int COLOR_OFFSET = VERTEX_SIZE * byte_per_element;
        public static final int NORMAL_SIZE = 3; //x,y,z
        public static final int NORMAL_OFFSET = (VERTEX_SIZE + COLOR_SIZE) * byte_per_element;

        public static final int FRAGMENT_SIZE = VERTEX_SIZE + COLOR_SIZE + NORMAL_SIZE;
        public static final int STRIDE = FRAGMENT_SIZE * byte_per_element;

        public Shader shader;
        public int gpuVaoName;
        public int gpuVectorName;
        public ByteBuffer fragments;
        public int gpuIndexName;
        public ByteBuffer indices;

        public Position position;
        public Position rotation;
        public Position scale;

        private float[] matrix;
        public float[] modelMatrix() {
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

        public void render(float[] parentModelViewMatrix) {
                matrix = modelMatrix();
                Matrix.multiplyMM(
                        matrix,
                        0,
                        parentModelViewMatrix,
                        0,
                        matrix,
                        0
                );

                prepareToDraw(shader);

                GLES.glBindVertexArray(gpuVaoName);
                GLES.glDrawElements(
                        GLES.GL_TRIANGLES,
                        indices.capacity(),
                        GLES.GL_UNSIGNED_BYTE,
                        0
                );
                GLES.glBindVertexArray(0);
        }

        private final void prepareToDraw(Shader shader) {
                GLES.glUseProgram(shader.program);
                GLES.glUniformMatrix4fv(
                        shader.modelViewMatrix_Uniform_Location,
                        1,
                        false,
                        matrix,
                        0
                );
                GLES.glUniformMatrix4fv(
                        shader.projectionMatrix_Uniform_Location,
                        1,
                        false,
                        shader.projectionMatrix,
                        0
                );

                final float red = 1;
                final float green = 1;
                final float blue = 1;
                GLES.glUniform3f(shader.lightAmbientColor_Uniform_Location, red, green, blue);
                GLES.glUniform1f(shader.lightAmbientIntens_Uniform_Location,0.1f);
                Float3 lightDirection = VectorMath.vector3Normalize(new Float3(0,1f,-1));
                GLES.glUniform3f(
                        shader.lightDirection_Uniform_Location,
                        lightDirection.x,
                        lightDirection.y,
                        lightDirection.z);
                GLES.glUniform1f(shader.lightDiffuseIntens_Uniform_Location,0.7f);

                GLES.glUniform1f(shader.matSpecularIntensity_Uniform_Location,2.0f);
                GLES.glUniform1f(shader.shininess_Uniform_Location,8.0f);

        }

}

