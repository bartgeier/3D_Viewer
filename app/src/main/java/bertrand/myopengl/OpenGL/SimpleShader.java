package bertrand.myopengl.OpenGL;

import java.nio.IntBuffer;

public class SimpleShader extends AbstractShader{
        private static final int byte_per_element = 4;
        private static final int VERTEX_SIZE = 3; //x,y,z
        private static final int VERTEX_OFFSET = 0;
        private static final int COLOR_SIZE = 4; //r,g,b,a
        private static final int COLOR_OFFSET = VERTEX_SIZE * byte_per_element;
        private static final int NORMAL_SIZE = 3; //x,y,z
        private static final int NORMAL_OFFSET = (VERTEX_SIZE + COLOR_SIZE) * byte_per_element;

        private static final int FRAGMENT_SIZE = VERTEX_SIZE + COLOR_SIZE + NORMAL_SIZE;
        private static final int STRIDE = FRAGMENT_SIZE * byte_per_element;

        public SimpleShader() {
                final String vertexShaderCode =
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

                final String fragmentShaderCode =
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
                "}";


                final int vertexShader = loadShader(GLES.GL_VERTEX_SHADER, vertexShaderCode);
                final int fragmentShader = loadShader(GLES.GL_FRAGMENT_SHADER, fragmentShaderCode);

                program = GLES.glCreateProgram();
                GLES.glAttachShader(program, vertexShader);
                GLES.glAttachShader(program, fragmentShader);
                GLES.glLinkProgram(program);

                a.position = GLES.glGetAttribLocation(program, "a_Position");
                a.color = GLES.glGetAttribLocation(program, "a_Color");
                a.normal = GLES.glGetAttribLocation(program, "a_Normal");

                u.modelViewMatrix =
                        GLES.glGetUniformLocation(program, "u_ModelViewMatrix");
                u.projectionMatrix =
                        GLES.glGetUniformLocation(program, "u_ProjectionMatrix");

                u.lightAmbientIntens =
                        GLES.glGetUniformLocation(program, "u_Light.AmbientIntensity");
                u.lightAmbientColor =
                        GLES.glGetUniformLocation(program, "u_Light.Color");

                u.lightDiffuseIntens =
                        GLES.glGetUniformLocation(program, "u_Light.DiffuseIntensity");
                u.lightDirection =
                        GLES.glGetUniformLocation(program, "u_Light.Direction");

                u.matSpecularIntensity =
                        GLES.glGetUniformLocation(program, "u_MatSpecularIntensity");
                u.shininess =
                        GLES.glGetUniformLocation(program, "u_Shininess");

                IntBuffer linkSuccess = IntBuffer.allocate(1);
                GLES.glGetProgramiv(program, GLES.GL_LINK_STATUS, linkSuccess);
                if (linkSuccess.get(0) == GLES.GL_FALSE) {
                        String s = GLES.glGetProgramInfoLog(program);
                        throw new AssertionError(s);
                }
        }

        @Override
        public void enableVertexAttribArray(){
                GLES.glEnableVertexAttribArray(a.position);
                GLES.glVertexAttribPointer(
                        a.position,
                        VERTEX_SIZE,
                        GLES.GL_FLOAT,
                        false,
                        STRIDE,
                        VERTEX_OFFSET
                );
                GLES.glEnableVertexAttribArray(a.color);
                GLES.glVertexAttribPointer(
                        a.color,
                        COLOR_SIZE,
                        GLES.GL_FLOAT,
                        false,
                        STRIDE,
                        COLOR_OFFSET
                );
                GLES.glEnableVertexAttribArray(a.normal);
                GLES.glVertexAttribPointer(
                        a.normal,
                        NORMAL_SIZE,
                        GLES.GL_FLOAT,
                        false,
                        STRIDE,
                        NORMAL_OFFSET
                );
        }



}
