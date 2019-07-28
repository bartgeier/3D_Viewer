package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.Circle;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.OBJ_FILE.ModelData;
import bertrand.myopengl.Tool.OBJ_FILE.OBJParser;
import bertrand.myopengl.Tool.Positions;
import bertrand.myopengl.Tool.Str;
import bertrand.myopengl.Tool.Vec3;

public final class Test_3 {
        public static class Press implements Box.TabAction.Function_IF {
                @Override
                public void f(Box.Tab tab) {

                }
        };
        public static class Release implements Box.TabAction.Function_IF {
                @Override
                public void f(Box.Tab tab) {
                        if (Box.backGround.color.b == 0f) {
                                Box.backGround.color.r = 0.8f;
                                Box.backGround.color.g = 0.8f;
                                Box.backGround.color.b = 0.8f;
                        } else {
                                Box.backGround.color.r = 1f;
                                Box.backGround.color.g = 0.4f;
                                Box.backGround.color.b = 0;
                        }
                }
        };
        public static class Change implements Box.TabAction.Change_IF {
                @Override
                public void f(Box.Tab tab, Box.Touch t) {

                }
        };
        public static class Entry implements Box.TabAction.Function_IF {
                @Override
                public void f(Box.Tab tab) {

                }
        };
        public static class Exit implements Box.TabAction.Function_IF {
                @Override
                public void f(Box.Tab tab) {

                }
        };
        static Press press = new Press();
        static Release release = new Release();
        static Change change = new Change();
        static Entry entry = new Entry();
        static Exit exit = new Exit();
        public static void createScene(@NotNull AssetManager asset) {
        try {
                Box.Camera camera = Box.cameras.atId(0);
                Box.Display display = Box.displays.atId(0);
                float normal = 2.0f/display.width; //normal per pixel

                final int guiRoot_location_ID = Box.guiLocations.add(
                        new Box.Location(
                                0,
                                0,
                                0,
                                0,
                                0
                        )
                );

                int shaderProgram_ID = Load.quadShader(
                        Box.guiShaders,
                        Str.inputStreamToString(asset.open( "Shader/shader_gui_vert.txt")),
                        Str.inputStreamToString(asset.open( "Shader/shader_gui_frag.txt"))
                );
                int mesh_ID = Load.texturedQuad(
                        Box.meshes
                );
                Bitmap bitmap = BitmapFactory.decodeStream(
                        asset.open("PlusButton.png")
                );
                int release_texture_ID = Load.textureLinear(
                        Box.textures,
                        bitmap
                );
                bitmap = BitmapFactory.decodeStream(
                        asset.open("PlusButton_x.png")
                );
                int press_texture_ID = Load.textureLinear(
                        Box.textures,
                        bitmap
                );

                Box.Location  guiLocation = new Box.Location(
                        guiRoot_location_ID,
                        shaderProgram_ID,
                        Box.meshes.atId(mesh_ID).vao,
                        Box.textures.atId(release_texture_ID).texNo,
                        Box.meshes.atId(mesh_ID).indicesCount
                );
                Matrix.translateM(guiLocation.TF,0, -0.7f, -0.7f/camera.aspectRatio,0f);
                Matrix.scaleM(guiLocation.TF,0, normal*128, normal*128f,1.0f);
                int guiLocation_id = Box.guiLocations.add(guiLocation);

                int layer = 0;
                Box.TabAction tabAction = new Box.TabAction(
                        Box.textures.atId(press_texture_ID).texNo, //press
                        Box.textures.atId(release_texture_ID).texNo, //release
                        Box.textures.atId(release_texture_ID).texNo, //hover
                        press, release, change,  entry, exit
                );
                int tabAction_ID = Box.tabActions.add(tabAction);
                Box.Tab tab = new Box.Tab(++layer, tabAction_ID,guiLocation_id);
                Box.circleColliders.add(new Box.CircleCollider(tab, normal*72.0f));

                //////////// 3D /////////////
                Box.Location root3D = new Box.Location(
                        0,
                        0,
                        0,
                        0,
                        0
                );
                final int root_location_ID = Box.locations.add(root3D);

                camera.location_ID = root_location_ID;
                Mathe.translationXYZ(camera.T,0,0,-8);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);

                add.light(
                        Box.lights,
                        0f,-0.5f,-1f,
                        1,1,1
                );
                add.backGroundColor(Box.backGround,0.8f,0.8f,0.8f);

        } catch (IOException e) {
                e.printStackTrace();
        }
        }
}

