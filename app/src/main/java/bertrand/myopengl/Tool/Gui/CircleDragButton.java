package bertrand.myopengl.Tool.Gui;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.SparseArray.SparseArray;
import bertrand.myopengl.Tool.Vec2;


public class CircleDragButton {
        // Collider callback functions //
        public static class Press implements Box.TabAction.Function_IF {
                @Override
                public void f(int dragButton_ID) {
                        Box.DragButton x = Box.dragButtons.atId(dragButton_ID);
                        Box.DragState state = Box.dragStates.atId(x.state_ID);
                        Box.Location guiLocation = Box.guiLocations.atId(x.guiLocation_ID);
                        guiLocation.texId = Box.textures.atId(x.texturePress_ID).texNo;
                        state.pressed = true;
                }
        }
        public static class Release implements Box.TabAction.Function_IF {
                @Override
                public void f(int dragButton_ID) {
                        Box.DragButton x = Box.dragButtons.atId(dragButton_ID);
                        Box.DragState state = Box.dragStates.atId(x.state_ID);
                        Box.UserAction userAction = Box.userActions.atId(x.userAction_ID);
                        Box.Location guiLocation = Box.guiLocations.atId(x.guiLocation_ID);
                        guiLocation.texId = Box.textures.atId(x.textureRelease_ID).texNo;
                        final boolean pressed = state.pressed;
                        state.pressed = false;
                        state.drag.sub(state.drag);//set zero
                        if (pressed) {
                                Vec2 pos = new Vec2(
                                        Mathe.Tx(guiLocation.TF),Mathe.Ty(guiLocation.TF)
                                );
                                Vec2 a = Vec2.sub(state.posA, pos);
                                if (a.length() == 0) {
                                        userAction.actionA.f();
                                } else {
                                        userAction.actionB.f();
                                }
                        } else {
                                /* animate */
                                Vec2 pos = new Vec2(Mathe.Tx(guiLocation.TF),Mathe.Ty(guiLocation.TF));
                                Vec2 a = Vec2.sub(state.posA, pos);
                                Vec2 b = Vec2.sub(state.posB, pos);
                                if (a.length() <= b.length() && !state.A) {
                                        /* moving to posA */
                                        state.A = true;
                                        userAction.actionBA.f();

                                } else if (a.length() > b.length() && state.A){
                                        /* moving to posB */
                                        state.A = false;
                                        userAction.actionAB.f();
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
                        Box.DragState state = Box.dragStates.atId(x.state_ID);
                        Box.Location guiLocation = Box.guiLocations.atId(x.guiLocation_ID);
                        if(Vec2.length(state.drag) > 0.05) {
                                state.pressed = false;
                                Matrix.setIdentityM(matrixA,0);
                                Matrix.translateM(matrixA, 0, delta.x, delta.y, 0f);
                                Matrix.multiplyMM(
                                        matrixB,0,
                                        matrixA,0,
                                        guiLocation.TF,0
                                );
                                /* because guiLocation.TF is scaled */
                                System.arraycopy(
                                        matrixB, 0,
                                        guiLocation.TF, 0,
                                        16
                                );
                        } else if (state.pressed) {
                                state.drag.add(delta);
                        }
                }
        }
        public static class Exit implements Box.TabAction.Change_IF {
                @Override
                public void f(int dragButton_ID, Vec2 delta) {
                        Box.DragButton x = Box.dragButtons.atId(dragButton_ID);
                        Box.DragState state = Box.dragStates.atId(x.state_ID);
                        if(Vec2.length(state.drag) > 0.05) {
                                change.f(dragButton_ID, delta);
                        } else if (state.pressed) {
                                Box.Location guiLocation = Box.guiLocations.atId(x.guiLocation_ID);
                                guiLocation.texId = Box.textures.atId(x.textureRelease_ID).texNo;
                                state.pressed = false;
                                state.drag.sub(state.drag);//set zero
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
                final float colliderRadius, //72.0fPixel
                final Vec2 posA,
                final Vec2 posB,
                final boolean A,
                final Box.UserAction.Function_IF actionA,
                final Box.UserAction.Function_IF actionB,
                final Box.UserAction.Function_IF actionAB,
                final Box.UserAction.Function_IF actionBA
        ) {
                Box.Location  guiLocation = new Box.Location(
                        parent_guiLocation_ID,
                        shaderProgram_ID,
                        Box.meshes.atId(unitQuad_mesh_ID).vao,
                        Box.textures.atId(release_texture_ID).texNo,
                        Box.meshes.atId(unitQuad_mesh_ID).indicesCount
                );
                //Matrix.translateM(guiLocation.TF,0, -0.7f, -0.7f/aspectRatio,0f);
                Matrix.translateM(guiLocation.TF,0, posA.x, posA.y,0f);
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


                Box.DragState dragState = new Box.DragState(
                        false,
                        new Vec2(0,0), //drag
                        posA,
                        posB,
                        A,
                        guiLocation_id
                );
                int state_id = Box.dragStates.add(dragState);

                Box.UserAction userAction = new Box.UserAction(actionA, actionB, actionAB, actionBA);
                int userAction_id = Box.userActions.add(userAction);

                final int dragButton_ID = Box.dragButtons.add(
                        new Box.DragButton(
                                state_id,
                                tabAction_ID,
                                collider_id,
                                guiLocation_id,
                                userAction_id,
                                unitQuad_mesh_ID,
                                press_texture_ID,
                                release_texture_ID,
                                hover_texture_ID
                        )
                );
                circleCollider.entity_ID = dragButton_ID;
                return dragButton_ID;
        }

        public static void animation(
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull SparseArray<Box.DragState> dragStates
        ) {
                for (int idx = 0; idx < dragStates.size(); idx++) {
                        final Box.DragState state = dragStates.at(idx);
                        Box.Location l = locations.atId(state.guiLocation_ID);
                        Vec2 pos = new Vec2(Mathe.Tx(l.TF),Mathe.Ty(l.TF));

                        float[] matrixA = new float[16];
                        float[] matrixB = new float[16];
                        Matrix.setIdentityM(matrixA,0);

                        if(Vec2.length(state.drag) == 0 && !state.pressed) {
                                /* animate */
                                Vec2 a = Vec2.sub(state.posA, pos);
                                Vec2 b = Vec2.sub(state.posB, pos);
                                if (state.A) {
                                        /* moving to posA */
                                        Matrix.translateM(matrixA, 0, a.x, a.y, 0f);
                                } else {
                                        /* moving to posB */
                                        Matrix.translateM(matrixA, 0, b.x, b.y, 0f);
                                }
                                Matrix.multiplyMM(
                                        matrixB,0,
                                        matrixA,0,
                                        l.TF,0
                                );
                                System.arraycopy(
                                        matrixB, 0,
                                        l.TF, 0,
                                        16
                                );
                        }
                }
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
