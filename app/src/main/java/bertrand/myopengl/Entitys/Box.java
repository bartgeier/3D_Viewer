package bertrand.myopengl.Entitys;

import android.opengl.Matrix;
import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.IDGenerator;
import bertrand.myopengl.Tool.Vec3;

public class Box {


        public static class BackGround {
                public Color4f color = null;
        }
        public static BackGround backGround = new BackGround();


        public static class Light {
                public Vec3 position;
                public Color4f color;
                public Vec3 attenuation = new Vec3(1, 0, 0);

                public Light(
                        float x, float y, float z,
                        float red, float green, float blue
                ) {
                        position = new Vec3(x, y, z);
                        color = new Color4f(red, green, blue);
                }

                public Light(final int light_ID, Vec3 position, float red, float green, float blue){
                        color = new Color4f(red, green, blue);
                        this.position = position;
                }
        }
        public static SparseArray<Light> lights = new SparseArray<>();
        public static IDGenerator light_ID_Generator = new IDGenerator();


        public static class Shader {
                public final int shader_type_ID;
                public final int programID;
                public final int vertexShaderID;
                public final int fragmentShaderID;
                public final int u_ModelViewMatrix;
                public final int u_ProjectionMatrix;
                public final int u_Light_AmbientIntens;
                public final int u_Light_Color;
                public final int u_Light_DiffuseIntens;
                public final int u_Light_Direction;
                public final int u_MatSpecularIntensity;
                public final int u_Shininess;
                public final int u_Texture;
                public Shader(
                        int shader_type_ID,
                        int programID,
                        int vertexShaderID,
                        int fragmentShaderID,
                        int u_ModelViewMatrix,
                        int u_ProjectionMatrix,
                        int u_Light_AmbientIntens,
                        int u_Light_Color,
                        int u_Light_DiffuseIntens,
                        int u_Light_Direction,
                        int u_MatSpecularIntensity,
                        int u_Shininess,
                        int u_Texture
                ) {
                        this.shader_type_ID = shader_type_ID;
                        this.programID = programID;
                        this.vertexShaderID = vertexShaderID;
                        this.fragmentShaderID = fragmentShaderID;
                        this.u_ModelViewMatrix = u_ModelViewMatrix;
                        this.u_ProjectionMatrix = u_ProjectionMatrix;
                        this.u_Light_AmbientIntens = u_Light_AmbientIntens;
                        this.u_Light_Color = u_Light_Color;
                        this.u_Light_DiffuseIntens = u_Light_DiffuseIntens;
                        this.u_Light_Direction = u_Light_Direction;
                        this.u_MatSpecularIntensity = u_MatSpecularIntensity;
                        this.u_Shininess = u_Shininess;
                        this.u_Texture = u_Texture;
                }
        }
        public static SparseArray<Shader> shaders = new SparseArray<>();
        public static IDGenerator shader_ID_Generator = new IDGenerator();


        public static class Mesh{
                public final int vao;
                public final int[] vbos;
                public final int texId;
                public final int indicesCount;
                public Mesh(
                        final int vao,
                        @NotNull final int[] vbos,
                        final int texId,
                        final int indicesCount
                ) {
                        this.vao = vao;
                        this.vbos = vbos;
                        this.texId = texId;
                        this.indicesCount = indicesCount;
                }
        }
        public static SparseArray<Mesh> meshes = new SparseArray<>();
        public static IDGenerator mesh_ID_Generator = new IDGenerator();


        static class Location {
                public final Vec3 position;
                public final Vec3 rotation; // degrees
                public final Vec3 scale;
                public final float[] transformationMatrix = new float[16];
                public final int shader_ID;
                public final int vao;
                public final int indicesCount;
                public Location(
                        final Vec3 position, final Vec3 rotation, final Vec3 scale,
                        final int shaderProgram_ID, final int vao, final int indicesCount
                ) {
                        this.position = position;
                        this.rotation = rotation;
                        this.scale = scale;

                        this.shader_ID = shaderProgram_ID;
                        this.vao = vao;
                        this.indicesCount = indicesCount;

                        Matrix.setIdentityM(transformationMatrix, 0);
                        Matrix.translateM(transformationMatrix,0,position.x, position.y, position.z);
                        Matrix.rotateM(transformationMatrix,0, rotation.x, 1, 0, 0);
                        Matrix.rotateM(transformationMatrix,0, rotation.y, 0, 1, 0);
                        Matrix.rotateM(transformationMatrix,0, rotation.z,  0, 0, 1);
                        Matrix.scaleM(transformationMatrix,0, scale.x, scale.y, scale.z);
                }
        }
        public static SparseArray<Location> locations = new SparseArray<>();
        public static IDGenerator location_ID_Generator = new IDGenerator();


        public static class Periode {
                public enum Type {
                        ROTATE_X,
                        ROTATE_Y,
                        ROTATE_Z,
                        SWING
                }
                public final int location_ID;
                public final Type type;
                public final double period_ms;
                public double angle;
                public Periode(
                        final int location_ID,
                        final Type type,
                        final double period_ms,
                        final double start_angle
                ) {
                        this.location_ID = location_ID;
                        this.type = type;
                        this.period_ms = period_ms;
                        this.angle = start_angle;
                }

        }
        public static SparseArray<Periode> periods = new SparseArray<>();
        public static IDGenerator periode_ID_Generator = new IDGenerator();
}
