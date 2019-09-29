package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.Gui.CircleDragButton;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.Str;
import bertrand.myopengl.Tool.Vec2;

public final class Drag_Button {
        public static class AAction implements Box.UserAction.Function_IF {
                @Override
                public void f() {
                        Box.backGround.color.r = 0f;
                        Box.backGround.color.g = 0f;
                        Box.backGround.color.b = 1f;
                }
        }
        public static class BAction implements Box.UserAction.Function_IF {
                @Override
                public void f() {
                        Box.backGround.color.r = 1f;
                        Box.backGround.color.g = 1f;
                        Box.backGround.color.b = 0f;
                }
        }
        public static class ABAction implements Box.UserAction.Function_IF {
                @Override
                public void f() {
                        Box.backGround.color.r = 0f;
                        Box.backGround.color.g = 1f;
                        Box.backGround.color.b = 0;
                }
        }
        public static class BAAction implements Box.UserAction.Function_IF {
                @Override
                public void f() {
                        Box.backGround.color.r = 1f;
                        Box.backGround.color.g = 0f;
                        Box.backGround.color.b = 0f;

                }
        }
        private static AAction black_action = new AAction();
        private static BAction orange_action = new BAction();
        private static ABAction ab_action = new ABAction();
        private static BAAction ba_action = new BAAction();



        public static void createScene(@NotNull AssetManager asset) {
        try {
                Box.Camera camera = Box.cameras.atId(0);
                Box.Display display = Box.displays.atId(0);
                final float normal = 2.0f/display.width; //normal per pixel

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

                int release_texture_ID = Load.textureLinear(
                        Box.textures,
                        BitmapFactory.decodeStream(asset.open("PlusButton.png"))
                );

                int press_texture_ID = Load.textureLinear(
                        Box.textures,
                        BitmapFactory.decodeStream(asset.open("PlusButton_x.png"))
                );

                int layer = 0;

                CircleDragButton.factory(
                        ++layer,
                        camera.aspectRatio,
                        press_texture_ID,
                        release_texture_ID,
                        release_texture_ID,
                        guiRoot_location_ID,
                        shaderProgram_ID,
                        mesh_ID,
                        normal*128, //128Pixel
                        normal*72, //72Pixel
                        new Vec2(-0.7f,-0.7f/camera.aspectRatio), //posA
                        new Vec2(0.7f,-0.7f/camera.aspectRatio),  //posB
                        true,
                        black_action,
                        orange_action,
                        ab_action,
                        ba_action
                );

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

