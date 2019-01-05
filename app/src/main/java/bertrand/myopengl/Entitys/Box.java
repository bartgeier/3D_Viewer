package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.IDGenerator;
import bertrand.myopengl.Tool.Vec3;

public class Box {
        public static IDGenerator entity_ID_Generator = new IDGenerator();
        public static IDGenerator light_ID_Generator = new IDGenerator();

        public static class BackGround {
                public Color4f color = null;
        }
        public static BackGround backGround = new BackGround();


        public static class Light {
                public final int light_ID;
                public Vec3 position;
                public Color4f color;
                public Vec3 attenuation = new Vec3(1, 0, 0);

                public Light(
                        final int light_ID,
                        float x, float y, float z,
                        float red, float green, float blue
                ) {
                        this.light_ID = light_ID;
                        position = new Vec3(x, y, z);
                        color = new Color4f(red, green, blue);
                }

                public Light(final int light_ID, Vec3 position, float red, float green, float blue){
                        this.light_ID = light_ID;
                        color = new Color4f(red, green, blue);
                        this.position = position;
                }
        }
        public static SparseArray<Light> lights = new SparseArray<>();


        public static class ShaderProgam {
                public final int shader_type_ID;
                public final int programID;
                public final int u_ModelViewMatrix;
                public final int u_ProjectionMatrix;
                public final int u_Light_AmbientIntens;
                public final int u_Light_Color;
                public final int u_Light_DiffuseIntens;
                public final int u_Light_Direction;
                public final int u_MatSpecularIntensity;
                public final int u_Shininess;
                public final int u_Texture;

                public ShaderProgam(
                        int shader_type_ID,
                        int programID,
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
        public static SparseArray<ShaderProgam> shadersPrograms = new SparseArray<>();


        public static class ShaderDeleteInfo {
                public final int programID;
                public final int vertexShaderID;
                public final int fragmentShaderID;

                public ShaderDeleteInfo(
                        final int programID,
                        final int vertexShaderID,
                        final int fragmentShaderID

                ) {
                        this.programID = programID;
                        this.vertexShaderID = vertexShaderID;
                        this.fragmentShaderID = fragmentShaderID;
                }
        }
        public static SparseArray<ShaderDeleteInfo> shadersDeleteInfos = new SparseArray<>();


        static class Body{
                public int entity_ID;
                public int shader_type_ID;
                public int vao;
                public int indicesCount;
                float[] modelViewMatrix = new float[16];
                public Body(int entity_ID, int shader_type_ID, int vao, int indicesCount) {
                        this.entity_ID =  entity_ID;
                        this.shader_type_ID = shader_type_ID;
                        this.vao = vao;
                        this.indicesCount = indicesCount;
                }
        }
        public static SparseArray<Body> bodys = new SparseArray<>();


        public static class BodyDeleteInfo {
                public final int vao;
                public final int[] vbos;
                public final int texId;
                public BodyDeleteInfo(
                        final int vao,
                        @NotNull final int[] vbos,
                        final int texId
                ) {
                        this.vao = vao;
                        this.vbos = vbos;
                        this.texId = texId;
                }
        }
        public static ArrayList<BodyDeleteInfo> bodyDeleteInfos = new ArrayList<>();


        static class Location {
                public int entity_ID;
                public Vec3 position;
                public Vec3 rotation; // degrees
                public Vec3 scale;
                
                public Location(int entity_ID, Vec3 position, Vec3 rotation, Vec3 scale) {
                        this.entity_ID = entity_ID;
                        this.position = position;
                        this.rotation = rotation;
                        this.scale = scale;

                }
                public Location(int entity_ID) {
                        this.entity_ID = entity_ID;
                        position = new Vec3(0,0,0);
                        rotation = new Vec3(0,0,0);
                        scale = new Vec3(1,1,1);
                }
        }
        public static SparseArray<Location> locations = new SparseArray<>();


        public static class Periode {
                public enum Type {
                        UNDEF,
                        ROTATE,
                        SWING
                }
                public int entity_ID;
                public Type type;
                public double period_ms;
                public double angle;
                public Periode(int entity_ID, Type type, double period_ms, double start_angle) {
                        this.entity_ID = entity_ID;
                        this.type = type;
                        this.period_ms = period_ms;
                        this.angle = start_angle;
                }

        }
        public static SparseArray<Periode> periods = new SparseArray<>();
}
