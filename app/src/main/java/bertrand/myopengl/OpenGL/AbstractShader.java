package bertrand.myopengl.OpenGL;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;


public abstract class AbstractShader {
        public class Uniform_Location {
                int modelViewMatrix = 0;
                int projectionMatrix = 0;

                int lightAmbientIntens = 0;
                int lightAmbientColor = 0;

                int lightDiffuseIntens = 0;
                int lightDirection = 0;

                int matSpecularIntensity = 0;
                int shininess = 0;
        }

        public class Attribute_Location {
                int position = 0;
                int color = 0;
                int normal = 0;
        }

        AbstractShader () {
                a = new Attribute_Location();
                u = new Uniform_Location();
        }

        int program;
        Attribute_Location a;
        Uniform_Location u;

        public int createVertexArrayObject() {
                final int oeNames[] = new int[1];
                GLES.glGenVertexArrays(1, oeNames,0);
                GLES.glBindVertexArray(oeNames[0]);
                return oeNames[0];
        }

        public static void loadFragmentBuffer(@NotNull final ByteBuffer b) {
                final int h = gpuName();
                GLES.glBindBuffer(GLES.GL_ARRAY_BUFFER, h);
                GLES.glBufferData(
                        GLES.GL_ARRAY_BUFFER,
                        b.capacity(),
                        b.asIntBuffer(),
                        GLES.GL_STATIC_DRAW
                );
        }

        public static void loadIndecisBuffer(@NotNull final ByteBuffer b) {
                final int h = gpuName();
                GLES.glBindBuffer(GLES.GL_ELEMENT_ARRAY_BUFFER, h);
                GLES.glBufferData(
                        GLES.GL_ELEMENT_ARRAY_BUFFER,
                        b.capacity(),
                        b,
                        GLES.GL_STATIC_DRAW
                );
        }

        public abstract void enableVertexAttribArray();

        public void draw(int gpuVaoName, int numOfIndecis) {
                GLES.glBindVertexArray(gpuVaoName);
                GLES.glDrawElements(
                        GLES.GL_TRIANGLES,
                        numOfIndecis,
                        GLES.GL_UNSIGNED_BYTE,
                        0
                );
                GLES.glBindVertexArray(0);
        }

        private static int gpuName() {
                final int gpuNames[] = new int[1];
                GLES.glGenBuffers(1, gpuNames,0);
                return gpuNames[0];
        }

        static int loadShader(int type, String shaderCode) {
                int shader = GLES.glCreateShader(type);
                GLES.glShaderSource(shader, shaderCode);
                GLES.glCompileShader(shader);
                return shader;
        }
}
