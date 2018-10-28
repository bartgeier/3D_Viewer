package bertrand.myopengl.OpenGL;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class GPU {
        public static int createVertexArrayObject() {
                final int vaoID[] = new int[1];
                GLES.glGenVertexArrays(1, vaoID,0);
                GLES.glBindVertexArray(vaoID[0]);
                return vaoID[0];
        }

        public static int loadFragmentBuffer(int attributeID, int coordinateSize, @NotNull final ByteBuffer b) {
                final int vboID = GPU.generateVBO();
                GLES.glBindBuffer(GLES.GL_ARRAY_BUFFER, vboID);
                GLES.glBufferData(
                        GLES.GL_ARRAY_BUFFER,
                        b.capacity(),
                        b.asIntBuffer(),
                        GLES.GL_STATIC_DRAW
                );
                GLES.glVertexAttribPointer(
                        attributeID,
                        coordinateSize,
                        GLES.GL_FLOAT,
                        false,
                        0,
                        0
                );
                GLES.glEnableVertexAttribArray(attributeID);
                GLES.glBindBuffer(GLES.GL_ARRAY_BUFFER, 0);
                return vboID;
        }

        public static int loadIndecisBuffer(@NotNull final ByteBuffer b) {
                final int vboID = GPU.generateVBO();
                GLES.glBindBuffer(GLES.GL_ELEMENT_ARRAY_BUFFER, vboID);
                GLES.glBufferData(
                        GLES.GL_ELEMENT_ARRAY_BUFFER,
                        b.capacity(),
                        b.asIntBuffer(),
                        GLES.GL_STATIC_DRAW
                );
                return vboID;
        }

        public static void draw(int gpuVaoName, int numOfIndecis) {
                GLES.glBindVertexArray(gpuVaoName);
                GLES.glDrawElements(
                        GLES.GL_TRIANGLES,
                        numOfIndecis,
                        GLES.GL_UNSIGNED_INT,
                        0
                );
                GLES.glBindVertexArray(0);
        }

        public static int generateVBO() {
                final int vboID[] = new int[1];
                GLES.glGenBuffers(1, vboID,0);
                error();
                return vboID[0];
        }

        public static int loadShader(int type, String shaderCode) {
                int shader = GLES.glCreateShader(type);
                GLES.glShaderSource(shader, shaderCode);
                GLES.glCompileShader(shader);
                return shader;
        }

        public static void error() {
                //GLES.GLenum err;
                int err;
                int i = 0;
                while((err = GLES.glGetError()) != GLES.GL_NO_ERROR) {
                        i++;
                }


        }
}
