package bertrand.myopengl.OpenGL;


import java.nio.IntBuffer;

public abstract class BaseShader {

        protected BaseShader() {

        }

        public int programID;
        private  int vertexShaderID;
        private  int fragmentShaderID;

        protected int createProgram(final String vertexShaderCode, final String fragmentShaderCode) {
                vertexShaderID = GPU.loadShader(GLES.GL_VERTEX_SHADER, vertexShaderCode);
                fragmentShaderID = GPU.loadShader(GLES.GL_FRAGMENT_SHADER, fragmentShaderCode);

                int programID = GLES.glCreateProgram();
                GLES.glAttachShader(programID, vertexShaderID);
                GLES.glAttachShader(programID, fragmentShaderID);
                return programID;
        }

        protected void linkProgram() {
                GLES.glLinkProgram(programID);
                //GLES.glValidateProgram(programID); // Todo

                IntBuffer linkSuccess = IntBuffer.allocate(1);
                GLES.glGetProgramiv(programID, GLES.GL_LINK_STATUS, linkSuccess);
                if (linkSuccess.get(0) == GLES.GL_FALSE) {
                        String s = GLES.glGetProgramInfoLog(programID);
                        throw new AssertionError(s);
                }
        }

        public static void stop() {
                GLES.glUseProgram(0);
        }

        public void cleanUp() {
                stop();
                GLES.glDetachShader(programID, vertexShaderID);
                GLES.glDetachShader(programID, fragmentShaderID);
                GLES.glDeleteShader(vertexShaderID);
                GLES.glDeleteShader(fragmentShaderID);
                GLES.glDeleteProgram(programID);
        }

        static protected void attributeLocation(int programID, int attributeID, String name) {
                GLES.glBindAttribLocation(programID, attributeID, name);
        }

        static protected int uniformLocation(int programID, String name) {
                return GLES.glGetUniformLocation(programID, name);
        }
}
