package bertrand.myopengl.ExampleScenes;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.CleanUp;

public final class ClearScreen {
        public static void createScene() {
                CleanUp.locations(Box.locations);
                CleanUp.meshes(Box.meshes);
                CleanUp.periods(Box.periods);
                CleanUp.lights(Box.lights);
                CleanUp.backGroundColor(Box.backGround);
                CleanUp.shaders(Box.shaders);
                Camera.translation(0,0,0);
                Camera.rotation(0,0,0);
        }

}

