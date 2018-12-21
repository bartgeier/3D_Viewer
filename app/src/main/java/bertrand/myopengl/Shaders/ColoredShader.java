package bertrand.myopengl.Shaders;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

import bertrand.myopengl.OpenGL.BaseShader;
import bertrand.myopengl.Tool.Str;

public final class ColoredShader extends BaseShader {
        /* uniform locations */
                public final int u_ModelViewMatrix;
                private final int u_ProjectionMatrix;

                public final int u_Light_AmbientIntens;
                public final int u_Light_Color;

                public final int u_Light_DiffuseIntens;
                public final int u_Light_Direction;

                public final int u_MatSpecularIntensity;
                public final int u_Shininess;

        /* attribute locations */
                public static final int a_Position = 0;
                public static final int a_Color = 1;
                public static final int a_Normal = 2;

        public ColoredShader(
                @NotNull final String vertexCode,
                @NotNull final String fragmentCode
        ) {
                super(vertexCode, fragmentCode);
                attributeLocation(programID, a_Position, "a_Position");
                attributeLocation(programID, a_Color, "a_Color");
                attributeLocation(programID, a_Normal,  "a_Normal");
                linkProgram();

                u_ModelViewMatrix = uniformLocation(programID, "u_ModelViewMatrix");
                u_ProjectionMatrix = uniformLocation(programID, "u_ProjectionMatrix");

                u_Light_AmbientIntens = uniformLocation(programID, "u_Light.AmbientIntensity");
                u_Light_Color = uniformLocation(programID, "u_Light.Color");
                u_Light_DiffuseIntens = uniformLocation(programID, "u_Light.DiffuseIntensity");
                u_Light_Direction = uniformLocation(programID, "u_Light.Direction");

                u_MatSpecularIntensity = uniformLocation(programID, "u_MatSpecularIntensity");
                u_Shininess = uniformLocation(programID, "u_Shininess");
        }

        public int u_PojectionMatrix() {
                return u_ProjectionMatrix;
        }
}
