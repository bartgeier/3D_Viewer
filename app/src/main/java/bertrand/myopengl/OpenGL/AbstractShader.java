package bertrand.myopengl.OpenGL;


import java.nio.IntBuffer;

public abstract class AbstractShader {
        public class Uniform_Location {
                public int modelViewMatrix = 0;
                public int projectionMatrix = 0;

                public int lightAmbientIntens = 0;
                public int lightAmbientColor = 0;

                public int lightDiffuseIntens = 0;
                public int lightDirection = 0;

                public int matSpecularIntensity = 0;
                public int shininess = 0;
        }

        public class Attribute_Location {
                public int position = 0;
                public int color = 0;
                public int normal = 0;
        }

        protected AbstractShader () {
                attributeID = new Attribute_Location();
                u = new Uniform_Location();
        }

        public int programID;
        public Attribute_Location attributeID;
        public Uniform_Location u;

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
