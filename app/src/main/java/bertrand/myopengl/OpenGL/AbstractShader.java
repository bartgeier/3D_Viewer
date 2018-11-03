package bertrand.myopengl.OpenGL;


import java.nio.IntBuffer;

public abstract class AbstractShader {


        protected AbstractShader () {

        }

        public int programID;


        static protected int loadSchader(final String vertexShaderCode, final String fragmentShaderCode) {
                final int vertexShaderID = GPU.loadShader(GLES.GL_VERTEX_SHADER, vertexShaderCode);
                final int fragmentShaderID = GPU.loadShader(GLES.GL_FRAGMENT_SHADER, fragmentShaderCode);

                int programID = GLES.glCreateProgram();
                GLES.glAttachShader(programID, vertexShaderID);
                GLES.glAttachShader(programID, fragmentShaderID);
                GLES.glLinkProgram(programID);

                IntBuffer linkSuccess = IntBuffer.allocate(1);
                GLES.glGetProgramiv(programID, GLES.GL_LINK_STATUS, linkSuccess);
                if (linkSuccess.get(0) == GLES.GL_FALSE) {
                        String s = GLES.glGetProgramInfoLog(programID);
                        throw new AssertionError(s);
                }
                return programID;
        }

        static protected int attributeLocation(int programID, String name) {
                return GLES.glGetAttribLocation(programID, name);
        }

        static protected int uniformLocation(int programID, String name) {
                return GLES.glGetUniformLocation(programID, name);
        }
}
