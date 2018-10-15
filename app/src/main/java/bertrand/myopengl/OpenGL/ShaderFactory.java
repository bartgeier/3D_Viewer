package bertrand.myopengl.OpenGL;

import java.nio.IntBuffer;

public class ShaderFactory {
        private final String vertexShaderCode =
                "uniform highp mat4 u_ModelViewMatrix;" +
                "uniform highp mat4 u_ProjectionMatrix;" +

                "attribute vec4 a_Position;" +
                "attribute vec4 a_Color;" +
                "attribute vec3 a_Normal;" +

                "varying lowp vec4 frag_Color;" +
                "varying lowp vec3 frag_Normal;" +
                "varying lowp vec3 frag_Position;" +
                "void main() {" +
                        "frag_Color = a_Color;" +
                        "gl_Position = u_ProjectionMatrix * u_ModelViewMatrix * a_Position;" +
                        "frag_Normal = (u_ModelViewMatrix * vec4(a_Normal, 0.0)).xyz;" +
                        "frag_Position = (u_ModelViewMatrix * a_Position).xyz;" +
                "}";

        private final String fragmentShaderCode =
                "varying lowp vec4 frag_Color;" +
                "varying lowp vec3 frag_Normal;" +
                "varying lowp vec3 frag_Position;" +

                "uniform highp float u_MatSpecularIntensity;" +
                "uniform highp float u_Shininess;" +

                "struct Light {" +
                        "lowp vec3 Color;" +
                        "lowp float AmbientIntensity;" +
                        "lowp float DiffuseIntensity;" +
                        "lowp vec3 Direction;" +
                "};" +
                "uniform Light u_Light;" +

                "void main() {" +
                        //Ambient
                        "lowp vec3 AmbientColor = u_Light.Color * u_Light.AmbientIntensity;" +

                        //Diffuse
                        "lowp vec3 Normal = normalize(frag_Normal);" +
                        "lowp float DiffuseFactor = max(-dot(Normal, u_Light.Direction), 0.0);" +
                        "lowp vec3 DiffuseColor = " +
                                "u_Light.Color * " +
                                "u_Light.DiffuseIntensity *" +
                                "DiffuseFactor;" +

                        //Specular
                        "lowp vec3 Eye = normalize(frag_Position);" +
                        "lowp vec3 Reflection = reflect(u_Light.Direction, Normal);" +
                        "lowp float SpecularFactor = pow(max(0.0, -dot(Reflection, Eye)), u_Shininess);" +
                        "lowp vec3 SpecularColor = " +
                                "u_Light.Color * " +
                                "u_MatSpecularIntensity *" +
                                "SpecularFactor;" +

                        "gl_FragColor = " +
                                "frag_Color * " +
                                "vec4((AmbientColor + DiffuseColor + SpecularColor), 1.0);" +

/*
                        "gl_FragColor = " +
                        "frag_Color * " +
                        "vec4((AmbientColor + DiffuseColor), 1.0);" +
*/
                "}";

        public ShaderFactory() {

        }

        public Shader simpleProgram() {
                Shader shader = new Shader();

                int vertexShader = loadShader(GLES.GL_VERTEX_SHADER, vertexShaderCode);
                int fragmentShader = loadShader(GLES.GL_FRAGMENT_SHADER, fragmentShaderCode);

                shader.program = GLES.glCreateProgram();
                GLES.glAttachShader(shader.program, vertexShader);
                GLES.glAttachShader(shader.program, fragmentShader);
                GLES.glLinkProgram(shader.program);

                shader.position_Attrib_Location = GLES.glGetAttribLocation(shader.program, "a_Position");
                shader.color_Attrib_Location = GLES.glGetAttribLocation(shader.program, "a_Color");
                shader.normal_Attrib_Location = GLES.glGetAttribLocation(shader.program, "a_Normal");

                shader.modelViewMatrix_Uniform_Location =
                        GLES.glGetUniformLocation(shader.program, "u_ModelViewMatrix");
                shader.projectionMatrix_Uniform_Location =
                        GLES.glGetUniformLocation(shader.program, "u_ProjectionMatrix");

                shader.lightAmbientIntens_Uniform_Location =
                        GLES.glGetUniformLocation(shader.program, "u_Light.AmbientIntensity");
                shader.lightAmbientColor_Uniform_Location =
                        GLES.glGetUniformLocation(shader.program, "u_Light.Color");

                shader.lightDiffuseIntens_Uniform_Location =
                        GLES.glGetUniformLocation(shader.program, "u_Light.DiffuseIntensity");
                shader.lightDirection_Uniform_Location =
                        GLES.glGetUniformLocation(shader.program, "u_Light.Direction");

                shader.matSpecularIntensity_Uniform_Location =
                        GLES.glGetUniformLocation(shader.program, "u_MatSpecularIntensity");
                shader.shininess_Uniform_Location =
                        GLES.glGetUniformLocation(shader.program, "u_Shininess");



                IntBuffer linkSuccess = IntBuffer.allocate(1);
                GLES.glGetProgramiv(shader.program, GLES.GL_LINK_STATUS, linkSuccess);
                if (linkSuccess.get(0) == GLES.GL_FALSE) {
                        String s = GLES.glGetProgramInfoLog(shader.program);
                        throw new AssertionError(s);
                }
                return shader;
        }

        private static int loadShader(int type, String shaderCode) {
                int shader = GLES.glCreateShader(type);
                GLES.glShaderSource(shader, shaderCode);
                GLES.glCompileShader(shader);
                return shader;
        }
}
