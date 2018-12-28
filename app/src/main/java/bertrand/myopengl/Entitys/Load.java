package bertrand.myopengl.Entitys;

import android.graphics.Bitmap;
import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.GLES;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.ShaderTypes.ShaderType;
import bertrand.myopengl.Tool.Arr;

public class Load {
        public static class Info {
                int shader_type_ID;
                int vao;
                int indicesCount;
                public Info(int shader_type_ID, int vao, int indicesCount) {
                        this.shader_type_ID = shader_type_ID;
                        this.vao = vao;
                        this.indicesCount = indicesCount;
                }
        }
        public static Info coloredModel(
                @NotNull final int[] indices,
                @NotNull final float[] positions,
                @NotNull final float[] colors,
                @NotNull final float[] normals
        ) {
                int vao = GPU.createVertexArrayObject();
                int[] vbos = new int[4];
                vbos[0] = GPU.loadIndecisBuffer(Arr.allocateBuffer(indices));
                vbos[1] = GPU.loadAttribute(ShaderType.Colored.a_Position, 3, Arr.allocateBuffer(positions));
                vbos[2] = GPU.loadAttribute(ShaderType.Colored.a_Color, 4, Arr.allocateBuffer(colors));
                vbos[3] = GPU.loadAttribute(ShaderType.Colored.a_Normal, 3, Arr.allocateBuffer(normals));
                GPU.vertexArray0();
                return new Info(ShaderType.Colored.shader_type_ID, vao, indices.length);
        }

        public static Info texturedModel(
                @NotNull final Bitmap bitmap,
                @NotNull final int[] indices,
                @NotNull final float[] positions,
                @NotNull final float[] texCoords,
                @NotNull final float[] normals
        ) {
                int vao = GPU.createVertexArrayObject();
                int[] vbos = new int[4];
                vbos[0] = GPU.loadIndecisBuffer(Arr.allocateBuffer(indices));
                vbos[1] = GPU.loadAttribute(ShaderType.Textured.a_Position, 3, Arr.allocateBuffer(positions));
                vbos[2] = GPU.loadAttribute(ShaderType.Textured.a_TexCoord, 2, Arr.allocateBuffer(texCoords));
                vbos[3] = GPU.loadAttribute(ShaderType.Textured.a_Normal, 3, Arr.allocateBuffer(normals));
                int texId = GPU.loadTexture(bitmap);
                GPU.vertexArray0();
                return new Info(ShaderType.Textured.shader_type_ID, vao, indices.length);
        }

        public static void coloredShader(
                @NotNull final SparseArray<Box.ShaderUniforms> shaders,
                @NotNull final String vertexShaderCode,
                @NotNull final String fragmentShaderCode
        ) {
                final int vertexID = GPU.loadShader(GLES.GL_VERTEX_SHADER, vertexShaderCode);
                final int fragmentID = GPU.loadShader(GLES.GL_FRAGMENT_SHADER, fragmentShaderCode);
                final int programID = GPU.createShaderProgram(vertexID, fragmentID);

                GPU.attributeLocation(programID, ShaderType.Colored.a_Position, "a_Position");
                GPU.attributeLocation(programID, ShaderType.Colored.a_Color, "a_Color");
                GPU.attributeLocation(programID, ShaderType.Colored.a_Normal,  "a_Normal");
                GPU.linkProgram(programID);

                final int u_ModelViewMatrix = GPU.uniformLocation(programID, "u_ModelViewMatrix");
                final int u_ProjectionMatrix = GPU.uniformLocation(programID, "u_ProjectionMatrix");

                final int u_Light_AmbientIntens = GPU.uniformLocation(programID, "u_Light.AmbientIntensity");
                final int u_Light_Color = GPU.uniformLocation(programID, "u_Light.Color");
                final int u_Light_DiffuseIntens = GPU.uniformLocation(programID, "u_Light.DiffuseIntensity");
                final int u_Light_Direction = GPU.uniformLocation(programID, "u_Light.Direction");

                final int u_MatSpecularIntensity = GPU.uniformLocation(programID, "u_MatSpecularIntensity");
                final int u_Shininess = GPU.uniformLocation(programID, "u_Shininess");

                Box.ShaderUniforms shader = new Box.ShaderUniforms(
                        ShaderType.Colored.shader_type_ID,
                        programID,
                        u_ModelViewMatrix,
                        u_ProjectionMatrix,
                        u_Light_AmbientIntens,
                        u_Light_Color,
                        u_Light_DiffuseIntens,
                        u_Light_Direction,
                        u_MatSpecularIntensity,
                        u_Shininess,
                        0
                );
                shaders.append(shader.shader_type_ID, shader);
        }

        public static void texturedShader(
                @NotNull final SparseArray<Box.ShaderUniforms> shaders,
                @NotNull final String vertexShaderCode,
                @NotNull final String fragmentShaderCode
        ) {
                final int vertexID = GPU.loadShader(GLES.GL_VERTEX_SHADER, vertexShaderCode);
                final int fragmentID = GPU.loadShader(GLES.GL_FRAGMENT_SHADER, fragmentShaderCode);
                final int programID = GPU.createShaderProgram(vertexID, fragmentID);

                GPU.attributeLocation(programID, ShaderType.Textured.a_Position, "a_Position");
                GPU.attributeLocation(programID, ShaderType.Textured.a_TexCoord, "a_TexCoord");
                GPU.attributeLocation(programID, ShaderType.Textured.a_Normal,  "a_Normal");
                GPU.linkProgram(programID);

                final int u_ModelViewMatrix = GPU.uniformLocation(programID, "u_ModelViewMatrix");
                final int u_ProjectionMatrix = GPU.uniformLocation(programID, "u_ProjectionMatrix");

                final int u_Light_AmbientIntens = GPU.uniformLocation(programID, "u_Light.AmbientIntensity");
                final int u_Light_Color = GPU.uniformLocation(programID, "u_Light.Color");
                final int u_Light_DiffuseIntens = GPU.uniformLocation(programID, "u_Light.DiffuseIntensity");
                final int u_Light_Direction = GPU.uniformLocation(programID, "u_Light.Direction");

                final int u_MatSpecularIntensity = GPU.uniformLocation(programID, "u_MatSpecularIntensity");
                final int u_Shininess = GPU.uniformLocation(programID, "u_Shininess");
                final int u_Texture = GPU.uniformLocation(programID, "u_Texture");

                Box.ShaderUniforms shader = new Box.ShaderUniforms(
                        ShaderType.Textured.shader_type_ID,
                        programID,
                        u_ModelViewMatrix,
                        u_ProjectionMatrix,
                        u_Light_AmbientIntens,
                        u_Light_Color,
                        u_Light_DiffuseIntens,
                        u_Light_Direction,
                        u_MatSpecularIntensity,
                        u_Shininess,
                        u_Texture
                );
                shaders.append(shader.shader_type_ID, shader);
        }
}
