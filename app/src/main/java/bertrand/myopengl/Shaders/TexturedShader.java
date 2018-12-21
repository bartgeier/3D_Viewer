package bertrand.myopengl.Shaders;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

import bertrand.myopengl.OpenGL.BaseShader;
import bertrand.myopengl.Tool.Str;

public final class TexturedShader extends BaseShader {
        /* uniform locations */
                public final int u_ModelViewMatrix;
                public final int u_ProjectionMatrix;

                public final int u_Light_AmbientIntens;
                public final int u_Light_AmbientColor;

                public final int u_Light_DiffuseIntens;
                public final int u_Light_Direction;

                public final int u_MatSpecularIntensity;
                public final int u_Shininess;

                public final int u_Texture;

        /* attribute locations */
                public static final int a_Position = 0;
                public static final int a_TexCoord = 1;
                public static final int a_Normal = 2;

        public TexturedShader(
                @NotNull final String vertexCode,
                @NotNull final String fragmentCode
        ) {
                super(vertexCode, fragmentCode);
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

        public int u_PojectionMatrix() {
                return u_ProjectionMatrix;
        }
}
