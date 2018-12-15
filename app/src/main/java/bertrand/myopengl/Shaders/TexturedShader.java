package bertrand.myopengl.Shaders;

import bertrand.myopengl.OpenGL.BaseShader;

public class TexturedShader extends BaseShader {
        /* uniform locations */
                public int u_ModelViewMatrix;
                //public int u_ProjectionMatrix; see super

                public int u_Light_AmbientIntens;
                public int u_Light_AmbientColor;

                public int u_Light_DiffuseIntens;
                public int u_Light_Direction;

                public int u_MatSpecularIntensity;
                public int u_Shininess;

                public int u_Texture;

        /* attribute locations */
                public static final int a_Position = 0;
                public static final int a_TexCoord = 1;
                public static final int a_Normal = 2;

        public TexturedShader() {
                final String vertexShaderCode =
                "uniform highp mat4 u_ModelViewMatrix;" +
                "uniform highp mat4 u_ProjectionMatrix;" +

                "attribute vec4 a_Position;" +
                "attribute vec3 a_Normal;" +
                "attribute vec2 a_TexCoord;" +

                "varying lowp vec3 frag_Normal;" +
                "varying lowp vec3 frag_Position;" +
                "varying lowp vec2 frag_TexCoord;" +
                "void main() {" +
                        "gl_Position = u_ProjectionMatrix * u_ModelViewMatrix * a_Position;" +
                        "frag_TexCoord = a_TexCoord;" +
                        "frag_Normal = (u_ModelViewMatrix * vec4(a_Normal, 0.0)).xyz;" +
                        "frag_Position = (u_ModelViewMatrix * a_Position).xyz;" +
                "}";

                final String fragmentShaderCode =
                "varying lowp vec3 frag_Normal;" +
                "varying lowp vec3 frag_Position;" +
                "varying lowp vec2 frag_TexCoord;" +

                "uniform highp float u_MatSpecularIntensity;" +
                "uniform highp float u_Shininess;" +
                "uniform sampler2D u_Texture;" +

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
                        "texture2D(u_Texture, frag_TexCoord) *" +
                        "vec4((AmbientColor + DiffuseColor + SpecularColor), 1.0);" +
                "}";

                programID = createProgram(vertexShaderCode,fragmentShaderCode);
                attributeLocation(programID, a_Position, "a_Position");
                attributeLocation(programID, a_TexCoord, "a_TexCoord");
                attributeLocation(programID, a_Normal,  "a_Normal");
                linkProgram();

                u_ModelViewMatrix = uniformLocation(programID, "u_ModelViewMatrix");
                u_ProjectionMatrix = uniformLocation(programID, "u_ProjectionMatrix");

                u_Light_AmbientIntens = uniformLocation(programID, "u_Light.AmbientIntensity");
                u_Light_AmbientColor = uniformLocation(programID, "u_Light.Color");
                u_Light_DiffuseIntens = uniformLocation(programID, "u_Light.DiffuseIntensity");
                u_Light_Direction = uniformLocation(programID, "u_Light.Direction");

                u_MatSpecularIntensity = uniformLocation(programID, "u_MatSpecularIntensity");
                u_Shininess = uniformLocation(programID, "u_Shininess");

                u_Texture = uniformLocation(programID, "u_Texture");
        }
}
