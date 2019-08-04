package bertrand.myopengl.Tool.Gui;

import android.opengl.Matrix;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Tool.Vec2;

public class CircleDragButton {
        // Collider callback functions //
        public static class Press implements Box.TabAction.Function_IF {
                @Override
                public void f(int button_ID) {
                        Box.DragButton x = Box.dragButtons.atId(button_ID);
                        Box.Location guiLocation = Box.guiLocations.atId(x.guiLocation_ID);
                        guiLocation.texId = Box.textures.atId(x.texturePress_ID).texNo;
                        x.pressed = true;
                }
        }
        public static class Release implements Box.TabAction.Function_IF {
                @Override
                public void f(int dragButton_ID) {
                        Box.DragButton x = Box.dragButtons.atId(dragButton_ID);
                        Box.Location guiLocation = Box.guiLocations.atId(x.guiLocation_ID);
                        guiLocation.texId = Box.textures.atId(x.textureRelease_ID).texNo;
                        final boolean pressed = x.pressed;
                        x.pressed = false;
                        x.drag.sub(x.drag);//set zero
                        if (pressed) {
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
        }
        public static class Entry implements Box.TabAction.Change_IF {
                @Override
                public void f(int dragButton_ID, Vec2 delta) {
                }
        }
        public static class Change implements Box.TabAction.Change_IF {
                private float[] matrixA = new float[16];
                private float[] matrixB = new float[16];
                @Override
                public void f(int dragButton_ID, Vec2 delta) {
                        Box.DragButton x = Box.dragButtons.atId(dragButton_ID);
                        Box.Location guiLocation = Box.guiLocations.atId(x.guiLocation_ID);
                        if(Vec2.length(x.drag) > 0.05) {
                                x.pressed = false;
                                Matrix.setIdentityM(matrixA,0);
                                Matrix.translateM(matrixA, 0, delta.x, delta.y, 0f);
                                Matrix.multiplyMM(
                                        matrixB,0,
                                        matrixA,0,
                                        guiLocation.TF,0
                                );
                                System.arraycopy(
                                        matrixB, 0,
                                        guiLocation.TF, 0,
                                        16
                                );
                                Matrix.multiplyMM(
                                        matrixB,0,
                                        matrixA,0,
                                        guiLocation.MV,0
                                );
                                System.arraycopy(
                                        matrixB, 0,
                                        guiLocation.MV, 0,
                                        16
                                );
                        } else if (x.pressed) {
                                x.drag.add(delta);
                        }
                }
        }
        public static class Exit implements Box.TabAction.Change_IF {
                @Override
                public void f(int dragButton_ID, Vec2 delta) {
                        Box.DragButton x = Box.dragButtons.atId(dragButton_ID);
                        if(Vec2.length(x.drag) > 0.05) {
                                change.f(dragButton_ID, delta);
                        } else if (x.pressed) {
                                Box.Location guiLocation = Box.guiLocations.atId(x.guiLocation_ID);
                                guiLocation.texId = Box.textures.atId(x.textureRelease_ID).texNo;
                                x.pressed = false;
                                x.drag.sub(x.drag);//set zero
                        }
                }
        }

        private static Press press = new Press();
        private static Release release = new Release();
        private static Change change = new Change();
        private static Entry entry = new Entry();
        private static Exit exit = new Exit();

        public static int factory(
                Integer layer,           //use it so factory(++layer,...
                final float aspectRatio, //camera.aspectRatio = display.width / display.height;
                final int press_texture_ID,
                final int release_texture_ID,
                final int hover_texture_ID,
                final int parent_guiLocation_ID,
                final int shaderProgram_ID,
                final int unitQuad_mesh_ID,
                final float quadSize,      //128fPixel
                final float colliderRadius //72.0fPixel
        ) {
                Box.Location  guiLocation = new Box.Location(
                        parent_guiLocation_ID,
                        shaderProgram_ID,
                        Box.meshes.atId(unitQuad_mesh_ID).vao,
                        Box.textures.atId(release_texture_ID).texNo,
                        Box.meshes.atId(unitQuad_mesh_ID).indicesCount
                );
                Matrix.translateM(guiLocation.TF,0, -0.7f, -0.7f/aspectRatio,0f);
                Matrix.scaleM(guiLocation.TF,0, quadSize, quadSize,1.0f);
                int guiLocation_id = Box.guiLocations.add(guiLocation);

                Box.TabAction tabAction = new Box.TabAction(
                        press, release, entry, change, exit
                );
                int tabAction_ID = Box.tabActions.add(tabAction);

                Box.CircleCollider circleCollider = new Box.CircleCollider(
                        0,
                        layer,
                        guiLocation_id,
                        colliderRadius,
                        tabAction_ID
                );
                int collider_id = Box.circleColliders.add(circleCollider);

                final int dragButton_ID = Box.dragButtons.add(
                        new Box.DragButton(
                                false,
                                new Vec2(0,0),
                                  collider_id,
                                guiLocation_id,
                                unitQuad_mesh_ID,
                                press_texture_ID,
                                release_texture_ID,
                                hover_texture_ID
                        )
                );
                circleCollider.entity_ID = dragButton_ID;
                return dragButton_ID;
        }

        // todo Esperimentel not use yet //
        public static void delete(int dragButton_ID) {
                Box.DragButton x = Box.dragButtons.atId(dragButton_ID);
                Box.CircleCollider collider = Box.circleColliders.atId(x.collider_ID);

                Box.tabActions.delete(collider.tabAction_ID);
                Box.circleColliders.delete(x.collider_ID);
                Box.guiLocations.delete(x.guiLocation_ID);   // todo delete hierarchy container  //
                Box.meshes.delete(x.mesh_ID);            // todo reference counted container //
                Box.textures.delete(x.textureHover_ID);  // todo reference counted container //
                Box.textures.delete(x.texturePress_ID);  // todo reference counted container //
                Box.textures.delete(x.textureRelease_ID);// todo reference counted container //
                Box.dragButtons.delete(dragButton_ID);
        }
}
