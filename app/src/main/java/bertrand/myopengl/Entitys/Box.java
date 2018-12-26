package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import bertrand.myopengl.Tool.Vec3;

public class Box {
        static class ShaderUniforms {
                public final int shader_type_ID;
                //public float[] matrix;
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

                public ShaderUniforms(
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
        public static SparseArray<ShaderUniforms> shadersUniforms = new SparseArray<>();

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

        static class Location {
                public int entity_ID;
                public Vec3 position;
                public Vec3 rotation;
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

        static class Periode {
                public enum Type {
                        UNDEF,
                        ROTATE,
                        SWING
                }
                public int entity_ID;
                public Type type;
                public double period_ms;
                public double angle;
                public Periode(int entity_ID, Type type, double period_ms, double angle) {
                        this.entity_ID = entity_ID;
                        this.type = type;
                        this.period_ms = period_ms;
                        this.angle = angle;
                }

        }
        public static SparseArray<Periode> periods = new SparseArray<>();


}
