package bertrand.myopengl.OpenGL;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

import bertrand.myopengl.Tool.Vec3;

public class GPU {
        public static int createVertexArrayObject() {
                final int[] vaoID = new int[1];
                GLES.glGenVertexArrays(1, vaoID,0);
                GLES.glBindVertexArray(vaoID[0]);
                return vaoID[0];
        }

        public static void vertexArray0() {
                GLES.glBindVertexArray(0);
        }

        public static void deleteVertexArrayObject(int vaoId) {
                final int[] vaoID = new int[1];
                vaoID[0] = vaoId;
                GLES.glDeleteVertexArrays(1, vaoID,0);
        }

        public static int loadAttribute(int attributeID, int coordinateSize, @NotNull final ByteBuffer b) {
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

        public static void loadFloat(int uniformID, float f) {
                GLES.glUniform1f(uniformID,f);
        }

        public static void load3Float(int uniformID, float f0, float f1, float f2) {
                GLES.glUniform3f(uniformID, f0, f1, f2);
        }

        public static void loadVec3(int uniformID,@NotNull Vec3 vector) {
                GLES.glUniform3f(uniformID, vector.x, vector.y, vector.z);
        }

        public static void loadMatrix(int uniformID,@NotNull float[] matrix) {
                if (matrix.length != 16) {
                        throw new AssertionError("Is not 4x4 matrix ");
                }
                GLES.glUniformMatrix4fv(
                        uniformID,
                        1,
                        false,
                        matrix,
                        0
                );
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
                GLES.glClearColor(0.8f,0.8f,0.8f,1.0f);
                GLES.glClear(GLES.GL_COLOR_BUFFER_BIT |GLES.GL_DEPTH_BUFFER_BIT);
                GLES.glEnable(GLES.GL_DEPTH_TEST);
                GLES.glEnable(GLES.GL_CULL_FACE);
        }

        private static int generateVBO() {
                final int vboID[] = new int[1];
                GLES.glGenBuffers(1, vboID,0);
                return vboID[0];
        }

        public static void deleteVBOs(int[] vboIDs) {
                GLES.glDeleteBuffers(vboIDs.length, vboIDs,0);
        }

        private static int generateTextureID() {
                final int texID[] = new int[1];
                GLES.glGenTextures(1, texID,0);
                return texID[0];
        }

        public static void deleteTextureID(int texId) {
                final int texID[] = new int[1];
                texID[0] = texId;
                GLES.glDeleteBuffers(1, texID,0);
        }

        static int loadShader(int type, String shaderCode) {
                int shader = GLES.glCreateShader(type);
                GLES.glShaderSource(shader, shaderCode);
                GLES.glCompileShader(shader);
                return shader;
        }

        public static void useProgram(int programID) {
                GLES.glUseProgram(programID);
        }

        /*
        public static void error() {
                //GLES.GLenum err;
                int err;
                int i = 0;
                while((err = GLES.glGetError()) != GLES.GL_NO_ERROR) {
                        i++;
                }
        }e
        */
}
