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
import bertrand.myopengl.Tool.Gui.CircleDragButton;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.OBJ_FILE.ModelData;
import bertrand.myopengl.Tool.OBJ_FILE.OBJParser;
import bertrand.myopengl.Tool.Positions;
import bertrand.myopengl.Tool.Str;
import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.Vec3;


public final class Stall {
        static float[] tempMatrixA = new float[16];
        static float[] tempMatrixB = new float[16];
        static float[] delta = new float[16];
        public static class A_Action implements Box.UserAction.Function_IF {
                @Override
                public void f() {
                        /* freeze_unFreeze_action */
                        Box.Camera camera = Box.cameras.atId(0);
                        Box.Location cameraLocation = Box.locations.atId(camera.location_ID);
                        Box.Location gohstLocation = Box.locations.atId(1);
                        if (!camera.enableRot) {
                                /* unfreeze */
                                camera.enableRot = true;
                                Matrix.invertM(
                                        tempMatrixA,0,
                                        camera.R_Offset, 0
                                );
                                Matrix.multiplyMM(
                                        delta,0,
                                        tempMatrixA, 0,
                                        tempMatrixB,0
                                );
                                System.arraycopy(
                                        gohstLocation.TF, 0,
                                        tempMatrixA, 0,
                                        16
                                );
                                Matrix.multiplyMM(
                                        gohstLocation.TF,0,
                                        delta,0,
                                        tempMatrixA, 0

                                );
                                float[] vector = new float[4];
                                vector[0] = Mathe.Tx(cameraLocation.TF);
                                vector[1] = Mathe.Ty(cameraLocation.TF);
                                vector[2] = Mathe.Tz(cameraLocation.TF);
                                vector[3] = Mathe.Tw(cameraLocation.TF);
                                float[] vectorb = new float[4];
                                Matrix.multiplyMV(
                                        vectorb,0,
                                        delta,0,
                                        vector,0);
                                Mathe.Tx(cameraLocation.TF, vectorb[0]);
                                Mathe.Ty(cameraLocation.TF, vectorb[1]);
                                Mathe.Tz(cameraLocation.TF, vectorb[2]);
                                Mathe.Tw(cameraLocation.TF, vectorb[3]);
                                //gray
                                Box.backGround.color.r = 0.8f;
                                Box.backGround.color.g = 0.8f;
                                Box.backGround.color.b = 0.8f;

                        } else {
                                /* freeze */
                                camera.enableRot = false;
                                System.arraycopy(
                                        camera.R, 0,
                                        tempMatrixB, 0,
                                        16
                                );
                                //red
                                Box.backGround.color.r = 1f;
                                Box.backGround.color.g = 0f;
                                Box.backGround.color.b = 0f ;
                        }
                }
        }
        public static class B_Action implements Box.UserAction.Function_IF {
                @Override
                public void f() {
                        /* moveToA_action moves Button to PosA and relative modus */
                        Box.Camera camera = Box.cameras.atId(0);
                        Box.DragState dragState = Box.dragStates.atId(0);
                        dragState.A = true;
                        camera.enableRot = true;
                        freeze_unFreeze_action.f();
                }
        }


        public static class AB_Action implements Box.UserAction.Function_IF {
                @Override
                public void f() {
                        /* reset_action */
                        Box.Camera camera = Box.cameras.atId(0);
                        Box.Location location = Box.locations.atId(1);
                        Matrix.setIdentityM(camera.R_Offset, 0);
                        Matrix.setIdentityM(location.TF, 0);
                        camera.enableRot = true;
                        //gray
                        Box.backGround.color.r = 0.8f;
                        Box.backGround.color.g = 0.8f;
                        Box.backGround.color.b = 0.8f;
                }
        }



        public static class BA_Action implements Box.UserAction.Function_IF {
                @Override
                public void f() {
                        /* freeze_action */
                        Box.Camera camera = Box.cameras.atId(0);
                        camera.enableRot = true;
                        freeze_unFreeze_action.f();

                }
        }
        private static A_Action freeze_unFreeze_action = new A_Action();
        private static B_Action moveToA_action = new B_Action();
        private static AB_Action reset_action = new AB_Action();
        private static BA_Action freeze_action = new BA_Action();

        public static void createScene(@NotNull AssetManager asset) {
        try {
                        create_gui(asset);

                        final int root_location_ID = Box.locations.add(
                                new Box.Location(
                                        0,
                                        0,
                                        0,
                                        0,
                                        0
                                )
                        );
                final int ghost_location_ID = Box.locations.add(
                        new Box.Location(
                                0,
                                0,
                                0,
                                0,
                                0
                        )
                );

                        Box.Camera camera = Box.cameras.atId(0);
                        camera.location_ID = root_location_ID;
                        Mathe.translationXYZ(camera.T, 0, 0, -8);
                        Mathe.rotationXYZ(camera.R, 0, 0, 0);

                        int shaderProgram_ID = Load.texturedShader(
                                Box.shaders,
                                Str.inputStreamToString(asset.open("Shader/shader_textured_vert.txt")),
                                Str.inputStreamToString(asset.open("Shader/shader_textured_frag.txt"))
                        );

                        ModelData obj = OBJParser.transform(
                                asset.open("Stall/stall.obj")
                        );

                        final Vec3 offset = Positions.offset(obj.getVertices());
                        final float[] translationMatrix = new float[16];
                        Matrix.setIdentityM(translationMatrix, 0);
                        Matrix.rotateM(translationMatrix, 0, 90, 1, 0, 0);
                        Matrix.rotateM(translationMatrix, 0, 90, 0, 1, 0);
                        final float[] normals = Positions.multiplyMatrix(
                                translationMatrix,
                                obj.getNormals()
                        );
                        Matrix.translateM(translationMatrix, 0, -offset.x, -offset.y, -offset.z);
                        final float[] positions = Positions.multiplyMatrix(
                                translationMatrix,
                                obj.getVertices()
                        );

                        final int mesh_ID = Load.texturedModel(
                                Box.meshes,
                                obj.getIndices(),
                                positions,
                                obj.getTextureCoords(),
                                normals
                        );

                        Bitmap bitmap = BitmapFactory.decodeStream(
                                asset.open("Stall/stall.png")
                        );

                        int texture_ID = Load.texture(
                                Box.textures,
                                bitmap
                        );

                        Box.locations.add(
                                new Box.Location(
                                        ghost_location_ID,
                                        shaderProgram_ID,
                                        Box.meshes.atId(mesh_ID).vao,
                                        Box.textures.atId(texture_ID).texNo,
                                        Box.meshes.atId(mesh_ID).indicesCount
                                )
                        );


                        add.light(
                                Box.lights,
                                0f, -0.5f, -1f,
                                1, 1, 1
                        );
                        add.backGroundColor(Box.backGround, 0.8f, 0.8f, 0.8f);
        } catch (IOException e) {
                e.printStackTrace();
        }
        }


        public static void create_gui(@NotNull AssetManager asset) {
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
                                false,
                                freeze_unFreeze_action,
                                moveToA_action,
                                reset_action,
                                freeze_action
                        );


                } catch (IOException e) {
                        e.printStackTrace();
                }
        }



}

