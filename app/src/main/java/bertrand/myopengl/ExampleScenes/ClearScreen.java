package bertrand.myopengl.ExampleScenes;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.CleanUp;
import bertrand.myopengl.Tool.Mathe;

public final class ClearScreen {
        public static void cleanUp(){
                Box.Camera camera = Box.cameras.atId(0);
                Mathe.translationXYZ(camera.T,0,0,0);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);
                while (Box.cameras.size()>1) {
                        Box.cameras.delete(Box.cameras.size()-1);
                }

                Box.cameras.at(0).location_ID = 0;
                Box.locations.clear();

                Box.circleColliders.clear();
                Box.tabActions.clear();
                Box.guiLocations.clear();
                Box.dragButtons.clear();
                Box.dragStates.clear();
                Box.tabs.clear();

                CleanUp.meshes(Box.meshes);
                CleanUp.textures(Box.textures);

                Box.swings.clear();
                Box.spins.clear();
                Box.lights.clear();


                CleanUp.backGroundColor(Box.backGround);
                CleanUp.shaders(Box.shaders);
                CleanUp.shaders(Box.guiShaders);

        }

        public static void createScene() {
                Box.Location l = new Box.Location(
                        0,
                        0,
                        0,
                        0,
                        0
                );
                final int root_location_ID = Box.locations.add(l);

                Box.Camera camera = Box.cameras.atId(0);
                camera.location_ID = root_location_ID;
                Mathe.translationXYZ(camera.T,0,0,-8);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);
        }

}

