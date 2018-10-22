package bertrand.myopengl.OpenGL;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class GPU {
        public static int createVertexArrayObject() {
                final int oeNames[] = new int[1];
                GLES.glGenVertexArrays(1, oeNames,0);
                GLES.glBindVertexArray(oeNames[0]);
                return oeNames[0];
        }

        public static void loadFragmentBuffer(@NotNull final ByteBuffer b) {
                final int h = GPU.gpuName();
                GLES.glBindBuffer(GLES.GL_ARRAY_BUFFER, h);
                GLES.glBufferData(
                        GLES.GL_ARRAY_BUFFER,
                        b.capacity(),
                        b.asIntBuffer(),
                        GLES.GL_STATIC_DRAW
                );
        }

        public static void loadIndecisBuffer(@NotNull final ByteBuffer b) {
                final int h = GPU.gpuName();
                GLES.glBindBuffer(GLES.GL_ELEMENT_ARRAY_BUFFER, h);
                GLES.glBufferData(
                        GLES.GL_ELEMENT_ARRAY_BUFFER,
                        b.capacity(),
                        b.asIntBuffer(),
                        GLES.GL_STATIC_DRAW
                );
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

        public static int gpuName() {
                final int gpuNames[] = new int[1];
                GLES.glGenBuffers(1, gpuNames,0);
                return gpuNames[0];
        }

        public static int loadShader(int type, String shaderCode) {
                int shader = GLES.glCreateShader(type);
                GLES.glShaderSource(shader, shaderCode);
                GLES.glCompileShader(shader);
                return shader;
        }
}
