package bertrand.myopengl.Entitys;

import android.opengl.Matrix;


import org.jetbrains.annotations.NotNull;

import java.util.Vector;

import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.Vec3;
import bertrand.myopengl.Tool.SparseArray.SparseArray;

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
        public static SparseArray<Light> lights = new SparseArray<>(null,50);


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
        public static SparseArray<Shader> shaders = new SparseArray<>(null,5);
        public static SparseArray<Shader> guiShaders = new SparseArray<>(null,5);


        public static class Mesh{
                public final int vao;
                public final int[] vbos;
                //public final int texId;
                public final int indicesCount;
                public Mesh(
                        final int vao,
                        @NotNull final int[] vbos,
                       // final int texId,
                        final int indicesCount
                ) {
                        this.vao = vao;
                        this.vbos = vbos;
                        //this.texId = texId;
                        this.indicesCount = indicesCount;
                }
        }
        public static SparseArray<Mesh> meshes = new SparseArray<>(null,50);

        public static class Texture {
                public final int texNo;
                public Texture(final int texId) {
                        this.texNo = texId;
                }
        }
        public static SparseArray<Texture> textures =  new SparseArray<>(null,50);


        public static class Location {
                public int parentIdx;
                public float[] TF = new float[16]; // transformation matrix
                public float[] MV = new float[16]; // model view matrix
                public final int shader_ID;
                public final int vao;
                public int texId;
                public final int indicesCount;
                public Location(
                        int parentIdx,
                        final int shaderProgram_ID,
                        final int vao,
                        final int texId,
                        final int indicesCount
                ) {
                        this.parentIdx = parentIdx;
                        this.shader_ID = shaderProgram_ID;
                        this.vao = vao;
                        this.texId = texId;
                        this.indicesCount = indicesCount;
                        Matrix.setIdentityM(TF, 0);

                }
        }
        public static SparseArray<Location> locations = new SparseArray<>(null,1000);

        public static class Swing {
                public final int location_ID;
                public final float period_ms;
                public float angle; //rad
                public final float amplitude;
                public final float phaseShift; //rad
                public Swing(
                        final int location_ID,
                        final float period_ms,
                        final float start_angle,
                        final float amplitude,
                        final float phaseShift
                ) {
                        this.location_ID = location_ID;
                        this.period_ms = period_ms;
                        this.angle = start_angle;
                        this.amplitude = amplitude;
                        this.phaseShift = phaseShift;
                }

        }
        public static SparseArray<Swing> swings = new SparseArray<>(null,1000);


        public static class Spin {
                public int location_ID;
                public float period_ms;
                public float axis_x,axis_y,axis_z;
                public Spin(
                        final int location_ID,
                        final float period_ms,
                        final float axis_x, final float axis_y, final float axis_z
                ) {
                        this.location_ID = location_ID;
                        this.period_ms = period_ms;
                        this.axis_x = axis_x;
                        this.axis_y = axis_y;
                        this.axis_z = axis_z;
                }
        }
        public static SparseArray<Spin> spins = new SparseArray<>(null,1000);


        public static class Camera {
                public int location_ID;
                public float aspectRatio;         // screen => (float)width / height
                public float fovyZoomAngle;       // 85f degrees
                public float near;                // 0.1f always bigger then 0
                public float far;                 // 300f
                public float[] T = new float[16]; // translation matrix
                public float[] R = new float[16]; // rotation matrix
                public float[] R_Offset = new float[16]; // rotation offset matrix
                public boolean enableRot = true;
                public Camera(
                        final int location_ID,
                        final float aspectRatio,
                        final float fovyZoomAngle,
                        final float near,
                        final float far
                ) {
                        this.location_ID = location_ID;
                        this.aspectRatio = aspectRatio;
                        this.fovyZoomAngle = fovyZoomAngle;
                        this.near = near;
                        this.far = far;
                        this.enableRot = true;
                        Matrix.setIdentityM(this.T, 0);
                        Matrix.setIdentityM(this.R, 0);
                        Matrix.setIdentityM(this.R_Offset, 0);
                }
        }
        public static SparseArray<Camera> cameras = new SparseArray<>(null,2);

        public static class Display {
                public int width;
                public int height;
                public Display( final int width, final int height) {
                        this.width = width;
                        this.height = height;
                }
        }
        public static SparseArray<Display> displays = new SparseArray<>(null,2);


/////////////////////////////////////////////////////////////////////////////////////////////
        //touchs parallel to platform container for finger touchs
        public static class Touch {
                public Vec2 point = new Vec2();
                public Vec2 delta = new Vec2();
                public Touch (final Vec2 p) {
                        this.point.copy(p);
                        this.delta.copy(p);
                }
                public Touch (final Vec2 p, final Vec2 d) {
                        this.point.copy(p);
                        this.delta.copy(d);
                }
        }
        public static SparseArray<Touch> touchs = new SparseArray<>(null, 20);

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////
        //GUI
        public static class DragButton {
                public int state_ID;
                public int tabAction_ID;
                public int collider_ID;
                public int guiLocation_ID;
                public int userAction_ID;
                public int mesh_ID; //unitQuad_mesh_ID
                public int texturePress_ID;
                public int textureRelease_ID;
                public int textureHover_ID;
                public DragButton(
                        final int state_ID,
                        final int tabAction_ID,
                        final int collider_ID,
                        final int guiLocation_ID,
                        final int userAction_ID,
                        final int mesh_ID,
                        final int texturePress_ID,
                        final int textureRelease_ID,
                        final int textureHover_ID
                ) {
                        this.state_ID = state_ID;
                        this.tabAction_ID = tabAction_ID;
                        this.collider_ID = collider_ID;
                        this.guiLocation_ID = guiLocation_ID;
                        this.userAction_ID = userAction_ID;
                        this.mesh_ID = mesh_ID;
                        this.texturePress_ID = texturePress_ID;
                        this.textureRelease_ID = textureRelease_ID;
                        this.textureHover_ID = textureHover_ID;
                }
        }
        public static SparseArray<DragButton> dragButtons =
                new SparseArray<>(null, 10);

        public static class DragState {
                public boolean pressed;
                public Vec2 drag;
                public Vec2 posA;
                public Vec2 posB;
                public boolean A;
                public int guiLocation_ID;
                public DragState(
                        final boolean pressed,
                        final Vec2 drag,
                        final Vec2 posA,
                        final Vec2 posB,
                        final boolean A,
                        final int guiLocation_ID
                ) {
                        this.pressed = pressed;
                        this.drag = drag;
                        this.posA = posA;
                        this.posB = posB;
                        this.A = A;
                        this.guiLocation_ID = guiLocation_ID;
                }
        }
        public static SparseArray<DragState> dragStates =
                new SparseArray<> (null,10);




        public static class UserAction {
                public interface Function_IF {
                        void f();
                }
                public Function_IF actionA;
                public Function_IF actionB;
                public Function_IF actionAB;
                public Function_IF actionBA;
                public UserAction(
                        Function_IF actionA,
                        Function_IF actionB,
                        Function_IF actionAB,
                        Function_IF actionBA
                ) {
                        this.actionA = actionA;
                        this.actionB = actionB;
                        this.actionAB = actionAB;
                        this.actionBA = actionBA;
                }
        }
        public static SparseArray<UserAction> userActions =
                new SparseArray<> (null,10);



        public static class Tab {
                public int layer;
                public int tabAction_ID;
                public int entity_ID;
                public Tab(int layer, int tabAction_ID, int entity_ID) {
                        this.layer = layer;
                        this.tabAction_ID = tabAction_ID;
                        this.entity_ID = entity_ID;
                }
        }
        public static Vector<Tab> tabs = new Vector<>(20,20);

        public static class CircleCollider {
                public int entity_ID;
                public int layer;
                public int guiLocation_ID;
                public float radius;
                public int tabAction_ID;
                public CircleCollider(
                        final int entity_ID,
                        final int layer,
                        final int guiLocation_ID, float radius,
                        final int tabAction_ID
                ) {
                        this.entity_ID = entity_ID;
                        this.layer = layer;
                        this.guiLocation_ID = guiLocation_ID;
                        this.radius = radius;
                        this.tabAction_ID = tabAction_ID;
                }
        }
        public static SparseArray<CircleCollider> circleColliders =
                new SparseArray<>(null, 10);



        public static class TabAction {
                public interface Function_IF {
                        void f(int entity_ID);
                }
                public interface Change_IF {
                        void f(int entity_ID, Vec2 delta);
                }
                public Function_IF press;
                public Function_IF release;
                public Change_IF entry;
                public Change_IF change;
                public Change_IF exit;
                public TabAction(
                        Function_IF press, Function_IF release,
                        Change_IF entry,  Change_IF change, Change_IF exit
                ) {
                        this.press = press;
                        this.release = release;
                        this.change = change;
                        this.entry = entry;
                        this.exit = exit;
                }
        }

        public static SparseArray<TabAction> tabActions = new SparseArray<>(null,20);


        public static SparseArray<Location> guiLocations = new SparseArray<>(null,10);


}
