package bertrand.myopengl.ExampleScenes;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.CleanUp;
import bertrand.myopengl.Tool.Mathe;

public final class ClearScreen {
        public static void createScene() {
                Box.Camera camera = Box.cameras.atId(0);
                Mathe.translationXYZ(camera.T,0,0,0);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);

                CleanUp.locations(Box.locations);
                CleanUp.meshes(Box.meshes);
                CleanUp.periods(Box.periods);
                CleanUp.lights(Box.lights);
                CleanUp.backGroundColor(Box.backGround);
                CleanUp.shaders(Box.shaders);
        }
}

