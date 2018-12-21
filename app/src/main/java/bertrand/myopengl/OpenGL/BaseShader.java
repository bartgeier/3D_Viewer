package bertrand.myopengl.OpenGL;


import org.jetbrains.annotations.NotNull;

import java.nio.IntBuffer;

public abstract class BaseShader {
        public final int programID;
        private final int vertexShaderID;
        private final int fragmentShaderID;

        public abstract int u_PojectionMatrix();

        protected BaseShader(
                @NotNull final String vertexShaderCode,
                @NotNull final String fragmentShaderCode
        ) {
                vertexShaderID = GPU.loadShader(GLES.GL_VERTEX_SHADER, vertexShaderCode);
                fragmentShaderID = GPU.loadShader(GLES.GL_FRAGMENT_SHADER, fragmentShaderCode);

                programID = GLES.glCreateProgram();
                GLES.glAttachShader(programID, vertexShaderID);
                GLES.glAttachShader(programID, fragmentShaderID);
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

        public void cleanUp() {
                GLES.glUseProgram(0);
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
