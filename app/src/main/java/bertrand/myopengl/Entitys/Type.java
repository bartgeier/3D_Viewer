package bertrand.myopengl.Entitys;

public class Type {
        public static final class ColoredShader {
                public static final int shader_type_ID = 0;
                public static final int a_Position = 0;
                public static final int a_Color = 1;
                public static final int a_Normal = 2;
        }
        public static final class TexturedShader {
                public static final int shader_type_ID = 1;
                public static final int a_Position = 0;
                public static final int a_TexCoord = 1;
                public static final int a_Normal = 2;
        }
}
