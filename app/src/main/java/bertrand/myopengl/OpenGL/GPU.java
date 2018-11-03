package bertrand.myopengl.OpenGL;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class GPU {
        public static int createVertexArrayObject() {
                final int[] vaoID = new int[1];
                GLES.glGenVertexArrays(1, vaoID,0);
                GLES.glBindVertexArray(vaoID[0]);
                return vaoID[0];
        }

        public static void deleteVertexArrayObject(int vaoId) {
                final int[] vaoID = new int[1];
                vaoID[0] = vaoId;
                GLES.glDeleteVertexArrays(1, vaoID,0);
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

        public static int loadTexture(final Bitmap bitmap) {
                final int texID = GPU.generateTextureID();
                GLES.glBindTexture(GLES.GL_TEXTURE_2D, texID);
                GLES.glTexParameteri( // Set filtering
                        GLES.GL_TEXTURE_2D,
                        GLES.GL_TEXTURE_MIN_FILTER,
                        GLES.GL_NEAREST
                );
                GLES.glTexParameteri( // Set filtering
                        GLES.GL_TEXTURE_2D,
                        GLES.GL_TEXTURE_MAG_FILTER,
                        GLES.GL_NEAREST
                );
                GLUtils.texImage2D(GLES.GL_TEXTURE_2D, 0, bitmap, 0);

                GLES.glActiveTexture(GLES.GL_TEXTURE0);

                bitmap.recycle();
                return texID;
        }

        public static void render(int gpuVaoName, int numOfIndecis) {
                GLES.glBindVertexArray(gpuVaoName);
                GLES.glDrawElements(
                        GLES.GL_TRIANGLES,
                        numOfIndecis,
                        GLES.GL_UNSIGNED_INT,
                        0
                );
                GLES.glBindVertexArray(0);
        }

        public static void renderBackground() {
                //GLES.glClearColor(0.8f,0.5f,0.0f,1.0f);
                GLES.glClearColor(0.8f,0.8f,0.8f,1.0f);
                GLES.glClear(GLES.GL_COLOR_BUFFER_BIT |GLES.GL_DEPTH_BUFFER_BIT);
                GLES.glEnable(GLES.GL_DEPTH_TEST);
                GLES.glEnable(GLES.GL_CULL_FACE);
        }

        public static int generateVBO() {
                final int vboID[] = new int[1];
                GLES.glGenBuffers(1, vboID,0);
                return vboID[0];
        }

        public static void deleteVBOs(int[] vboIDs) {
                GLES.glDeleteBuffers(vboIDs.length, vboIDs,0);
        }

        public static int generateTextureID() {
                final int texID[] = new int[1];
                GLES.glGenTextures(1, texID,0);
                return texID[0];
        }

        public static void deleteTextureID(int texId) {
                final int texID[] = new int[1];
                texID[0] = texId;
                GLES.glDeleteBuffers(1, texID,0);
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
