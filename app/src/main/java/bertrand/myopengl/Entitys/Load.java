package bertrand.myopengl.Entitys;

import android.graphics.Bitmap;
import bertrand.myopengl.Tool.SparseArray.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.GLES;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.ShaderTypes.ShaderType;
import bertrand.myopengl.Tool.Arr;

public class Load {
        public static int coloredModel(
                @NotNull final SparseArray<Box.Mesh> meshes,
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

                Box.Mesh mesh = new Box.Mesh(
                        vao,
                        vbos,
                        0,
                        indices.length
                );
                return meshes.add(mesh); //return SparseArray-ID
        }

        public static int texturedModel(
                @NotNull final SparseArray<Box.Mesh> meshes,
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

                Box.Mesh mesh = new Box.Mesh(
                        vao,
                        vbos,
                        texId,
                        indices.length
                );
                return meshes.add(mesh); //return SparseArray-ID
        }


        public static int coloredShader(
                @NotNull final SparseArray<Box.Shader> shaders,
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

                Box.Shader sProg = new Box.Shader(
                        ShaderType.Colored.shader_type_ID,
                        programID,
                        vertexID,
                        fragmentID,
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
                return shaders.add(sProg); //return SparseArray-ID //sProg.shader_type_ID;
        }

        public static int texturedShader(
                @NotNull final SparseArray<Box.Shader> shaders,
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

                Box.Shader sProg = new Box.Shader(
                        ShaderType.Textured.shader_type_ID,
                        programID,
                        vertexID,
                        fragmentID,
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
                return shaders.add(sProg); //return SparseArray-ID //sProg.shader_type_ID;
        }
}
