package bertrand.myopengl.Shaders;

import bertrand.myopengl.OpenGL.AbstractShader;

public class ColoredShader extends AbstractShader {
        public ColoredShader() {
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

                programID = loadSchader(vertexShaderCode,fragmentShaderCode);
                attributeID.position = attributeLocation(programID, "a_Position");
                attributeID.color = attributeLocation(programID, "a_Color");
                attributeID.normal = attributeLocation(programID, "a_Normal");

                u.modelViewMatrix = uniformLocation(programID, "u_ModelViewMatrix");
                u.projectionMatrix = uniformLocation(programID, "u_ProjectionMatrix");

                u.lightAmbientIntens = uniformLocation(programID, "u_Light.AmbientIntensity");
                u.lightAmbientColor = uniformLocation(programID, "u_Light.Color");

                u.lightDiffuseIntens = uniformLocation(programID, "u_Light.DiffuseIntensity");
                u.lightDirection = uniformLocation(programID, "u_Light.Direction");

                u.matSpecularIntensity = uniformLocation(programID, "u_MatSpecularIntensity");
                u.shininess = uniformLocation(programID, "u_Shininess");
        }



}
