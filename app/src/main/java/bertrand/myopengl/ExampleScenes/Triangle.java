package bertrand.myopengl.ExampleScenes;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.Constructor;
import bertrand.myopengl.Light.Light;
import bertrand.myopengl.Tool.Color4f;

import static bertrand.myopengl.Entitys.Box.Periode.Type.SWING;

public final class Triangle  {
        @NotNull
        public static Scene createScene() {
                final int entity_ID = 2;
                Load.Info i = Load.coloredModel(
                        new int[] { //indices
                                0,1,2
                        },
                        new float[] { //positions
                                0.0f, -0.5f, 0f,
                                0.5f,  0.5f, 0f,
                                -0.5f,  0.5f, 0f,
                        },
                        new float[] { //colors
                                1.0f, 0.06f, 0.0f, 1.0f,
                                0.0f, 1.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f, 1.0f,
                        },
                        new float[] { //normals
                                0, 0, 1,
                                0, 0, 1,
                                0, 0, 1,
                        }
                );
                Constructor.body(entity_ID, Box.bodys, i);
                Constructor.location(
                        entity_ID,
                        Box.locations,
                        0f,
                        0f,
                        -5f,
                        0f,
                        0f,
                        0f,
                        1f,
                        1f,
                        1f
                );
                Constructor.period(
                        entity_ID,
                        Box.periods,
                        SWING,
                        8000,
                        0
                );
                Scene scene = new Scene();
                scene.light = new Light(
                        0f,-0.5f,-1f,
                        1,1,1
                );
                scene.backGroundColor = new Color4f(0.8f,0.8f,0.8f);
                Camera.position(0,0,0);
                Camera.rotation(0,0,0);
                return scene;
        }

}
