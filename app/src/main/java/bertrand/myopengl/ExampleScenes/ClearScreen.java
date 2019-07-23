package bertrand.myopengl.ExampleScenes;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.CleanUp;
import bertrand.myopengl.Entitys.add;
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
                CleanUp.locations(Box.locations);
                CleanUp.locations(Box.guiLocations);
                Box.circleColliders.clear();
                CleanUp.meshes(Box.meshes);
                CleanUp.swings(Box.swings);
                CleanUp.spins(Box.spin);
                CleanUp.lights(Box.lights);
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
//        public static void createScene() {
//                final int root_location_ID = add.location(
//                        Box.locations,
//                        0,
//                        0, //dummy
//                        0, //dummy
//                        0, // dummy
//                        0, //dummy
//                        0f,
//                        0f,
//                        0f,
//                        0f,
//                        0f,
//                        0f,
//                        1f,
//                        1f,
//                        1f
//                );
//
//                Box.Camera camera = Box.cameras.atId(0);
//                camera.location_ID = root_location_ID;
//                Mathe.translationXYZ(camera.T,0,0,-8);
//                Mathe.rotationXYZ(camera.R, 0, 0, 0);
//        }
}

