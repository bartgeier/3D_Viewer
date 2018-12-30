package bertrand.myopengl.ExampleScenes;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.CleanUp;

public final class ClearScreen {
        @NotNull
        public static void createScene() {
                CleanUp.bodys(Box.bodys, Box.bodyDeleteInfos);
                CleanUp.locations(Box.locations);
                CleanUp.periods(Box.periods);
                CleanUp.lights(Box.lights);
                CleanUp.backGroundColor(Box.backGround);
                CleanUp.shaders(Box.shadersPrograms, Box.shadersDeleteInfos);
                Camera.position(0,0,0);
                Camera.rotation(0,0,0);
        }

}

