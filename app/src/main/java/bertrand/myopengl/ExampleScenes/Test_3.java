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
import bertrand.myopengl.Tool.Tst;
import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.Vec3;

import static bertrand.myopengl.Entitys.Box.guiLocations;
import static bertrand.myopengl.Entitys.Box.tabActions;

public final class Test_3 {
        private static float normal;
        // Button aspekt functions //
        public static boolean button_press(int button_ID) {
                Box.CircleButton ciButton = Box.circleButtons.atId(button_ID);
                Box.Location guiLocation = guiLocations.atId(ciButton.guiLocation_ID);
                guiLocation.texId = Box.textures.atId(ciButton.texturePress_ID).texNo;
                ciButton.pressed = true;
                return ciButton.pressed;
        }
        public static boolean button_release(int button_ID) {
                Box.CircleButton ciButton = Box.circleButtons.atId(button_ID);
                Box.Location guiLocation = guiLocations.atId(ciButton.guiLocation_ID);
                guiLocation.texId = Box.textures.atId(ciButton.textureRelease_ID).texNo;
                boolean x = ciButton.pressed;
                ciButton.pressed = false;
                ciButton.drag.sub(ciButton.drag);//set zero
                return x;
        }

        // Collider callback functions //
        public static class Press implements Box.TabAction.Function_IF {
                @Override
                public void f(int button_ID) {
                        button_press(button_ID);
                }
        };
        public static class Release implements Box.TabAction.Function_IF {
                @Override
                public void f(int circleButton_ID) {
                        if (button_release(circleButton_ID)) {
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
                }
        };
        public static class Entry implements Box.TabAction.Change_IF {
                @Override
                public void f(int circleButton_ID, Vec2 delta) {
                }
        };
/*
        public static class Change implements Box.TabAction.Change_IF {
                @Override
                public void f(int circleButton_ID, Vec2 delta) {
                        Box.CircleButton ciButton = Box.circleButtons.atId(circleButton_ID);
                        Box.Location guiLocation = guiLocations.atId(ciButton.guiLocation_ID);
                        if(ciButton.pressed) {
                                Matrix.scaleM(guiLocation.TF, 0, 1 / (normal * 128), 1 / (normal * 128f), 1.0f);
                                Matrix.translateM(guiLocation.TF, 0, delta.x, delta.y, 0f);
                                Matrix.scaleM(guiLocation.TF, 0, normal * 128, normal * 128f, 1.0f);
                        }
                }
        };
        public static class Exit implements Box.TabAction.Change_IF {
                @Override
                public void f(int circleButton_ID, Vec2 delta) {
                        Box.CircleButton ciButton = Box.circleButtons.atId(circleButton_ID);
                        Box.Location guiLocation = guiLocations.atId(ciButton.guiLocation_ID);
                        if(ciButton.pressed) {
                                Matrix.scaleM(guiLocation.TF, 0, 1 / (normal * 128), 1 / (normal * 128f), 1.0f);
                                Matrix.translateM(guiLocation.TF, 0, delta.x, delta.y, 0f);
                                Matrix.scaleM(guiLocation.TF, 0, normal * 128, normal * 128f, 1.0f);
                        }
                }
        };
*/
        public static class Change implements Box.TabAction.Change_IF {
                @Override
                public void f(int circleButton_ID, Vec2 delta) {
                        Box.CircleButton ciButton = Box.circleButtons.atId(circleButton_ID);
                        Box.Location guiLocation = guiLocations.atId(ciButton.guiLocation_ID);
                        if(Vec2.length(ciButton.drag) > 0.05) {
                                ciButton.pressed = false;

                                float[] matrixA = new float[16];
                                float[] matrixB = new float[16];
                                Matrix.setIdentityM(matrixA,0);
                                Matrix.translateM(matrixA, 0, delta.x, delta.y, 0f);

                                Matrix.multiplyMM(
                                        matrixB,0,
                                        matrixA,0,
                                        guiLocation.TF,0
                                );
                                guiLocation.TF = matrixB;

                                Matrix.multiplyMM(
                                        matrixB,0,
                                        matrixA,0,
                                        guiLocation.MV,0
                                );
                                guiLocation.MV = matrixB;

                                /*
                                Matrix.scaleM(guiLocation.TF, 0, 1 / (normal * 128), 1 / (normal * 128f), 1.0f);
                                Matrix.translateM(guiLocation.TF, 0, delta.x, delta.y, 0f);
                                Matrix.scaleM(guiLocation.TF, 0, normal * 128, normal * 128f, 1.0f);
                                */
                        } else if (ciButton.pressed) {
                                ciButton.drag.add(delta);

                        }
                }
        };


        public static class Exit implements Box.TabAction.Change_IF {
                @Override
                public void f(int circleButton_ID, Vec2 delta) {
                        Box.CircleButton ciButton = Box.circleButtons.atId(circleButton_ID);
                        Box.Location guiLocation = guiLocations.atId(ciButton.guiLocation_ID);
                        if(Vec2.length(ciButton.drag) > 0.05) {
                                ciButton.pressed = false;

                                float[] matrixA = new float[16];
                                float[] matrixB = new float[16];
                                Matrix.setIdentityM(matrixA,0);
                                Matrix.translateM(matrixA, 0, delta.x, delta.y, 0f);

                                Matrix.multiplyMM (
                                        matrixB,0,
                                        matrixA,0,
                                        guiLocation.TF,0
                                );
                                guiLocation.TF = matrixB;

                                Matrix.multiplyMM(
                                        matrixB,0,
                                        matrixA,0,
                                        guiLocation.MV,0
                                );
                                guiLocation.MV = matrixB;

/*
                                Matrix.scaleM(guiLocation.TF, 0, 1 / (normal * 128), 1 / (normal * 128f), 1.0f);
                                Matrix.translateM(guiLocation.TF, 0, delta.x, delta.y, 0f);
                                Matrix.scaleM(guiLocation.TF, 0, normal * 128, normal * 128f, 1.0f);
                                */
                        } else if (ciButton.pressed) {
                                button_release(circleButton_ID);
                        }
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
                normal = 2.0f/display.width; //normal per pixel


                final int guiRoot_location_ID = guiLocations.add(
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
                int guiLocation_id = guiLocations.add(guiLocation);

                int layer = 0;
                Box.TabAction tabAction = new Box.TabAction(
                        press, release, entry, change, exit
                );
                int tabAction_ID = tabActions.add(tabAction);

                Box.CircleCollider circleCollider = new Box.CircleCollider(
                        0,
                        ++layer,
                        guiLocation_id,
                        normal*72.0f,
                        tabAction_ID
                );
                int collider_id = Box.circleColliders.add(circleCollider);

                circleCollider.entity_ID = Box.circleButtons.add(
                        new Box.CircleButton(
                                false,
                              new Vec2(0,0),
                              //  collider_id,
                                guiLocation_id,
                                press_texture_ID,
                                release_texture_ID,
                                release_texture_ID //hover
                        )
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

