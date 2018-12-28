package bertrand.myopengl.ExampleScenes;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.CleanUp;
import bertrand.myopengl.Light.Light;
import bertrand.myopengl.Tool.Color4f;

public final class Empty {
        @NotNull
        public static Scene createScene() {
                final int entity_ID = 0;
                CleanUp.bodys(Box.bodys);
                CleanUp.locations(Box.locations);
                CleanUp.periods(Box.periods);
                //cleanUp.shadersUniforms();
                Scene scene = new Scene();
                scene.light = new Light(
                        0f,-0.5f,-1f,
                        1,1,1
                );
                scene.backGroundColor = new Color4f(0.8f,0.8f,0.8f);
                Camera.position(0,0,-5);
                Camera.rotation(20,0,0);
                return scene;
        }

}

