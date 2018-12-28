package bertrand.myopengl.ExampleScenes;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.Constructor;
import bertrand.myopengl.Light.Light;
import bertrand.myopengl.Tool.Color4f;

import static bertrand.myopengl.Entitys.Box.Periode.Type.ROTATE;

public final class CubeGray {
        @NotNull
        public static Scene createScene() {
                final int entity_ID = 1;
                Load.Info i = Load.coloredModel(
                        new int[] { // indices
                                // Front
                                0, 1, 2,
                                2, 3, 0,
                                // Back
                                4, 5, 6,
                                6, 7, 4,
                                // Left
                                8, 9, 10,
                                10, 11, 8,
                                // Right
                                12, 13, 14,
                                14, 15, 12,
                                // Top
                                16, 17, 18,
                                18, 19, 16,
                                // Bottom
                                20, 21, 22,
                                22, 23, 20
                        },
                        new float[] { // positions
                                // Front
                                1, -1,  1,   // 0
                                1,  1,  1,   // 1
                                -1,  1,  1,   // 2
                                -1,  -1, 1,   // 3

                                // Back
                                -1, -1, -1,    // 4
                                -1,  1, -1,    // 5
                                1,  1, -1,    // 6
                                1, -1, -1,    // 7

                                // Left
                                -1, -1,  1,   // 8
                                -1,  1,  1,   // 9
                                -1,  1, -1,   // 10
                                -1, -1, -1,   // 11

                                // Right
                                1, -1, -1,   // 12
                                1,  1, -1,   // 13
                                1,  1,  1,   // 14
                                1, -1,  1,   // 15

                                // Top
                                1,  1,  1,    // 16
                                1,  1, -1,    // 17
                                -1,  1, -1,    // 18
                                -1,  1,  1,    // 19

                                // Bottom
                                1, -1, -1,    // 20
                                1, -1,  1,    // 21
                                -1, -1,  1,    // 22
                                -1, -1, -1,    // 23
                        },
                        new float[] { // colors
                                0.5f, 0.5f, 0.5f, 1,   // 0
                                0.5f, 0.5f, 0.5f, 1,   // 1
                                0.5f, 0.5f, 0.5f, 1,   // 2
                                0.5f, 0.5f, 0.5f, 1,   // 3
                                0.5f, 0.5f, 0.5f, 1,    // 4
                                0.5f, 0.5f, 0.5f, 1,    // 5
                                0.5f, 0.5f, 0.5f, 1,    // 6
                                0.5f, 0.5f, 0.5f, 1,    // 7
                                0.5f, 0.5f, 0.5f, 1,   // 8
                                0.5f, 0.5f, 0.5f, 1,   // 9
                                0.5f, 0.5f, 0.5f, 1,   // 10
                                0.5f, 0.5f, 0.5f, 1,   // 11
                                0.5f, 0.5f, 0.5f, 1,   // 12
                                0.5f, 0.5f, 0.5f, 1,   // 13
                                0.5f, 0.5f, 0.5f, 1,   // 14
                                0.5f, 0.5f, 0.5f, 1,   // 15
                                0.5f, 0.5f, 0.5f, 1,    // 16
                                0.5f, 0.5f, 0.5f, 1,    // 17
                                0.5f, 0.5f, 0.5f, 1,    // 18
                                0.5f, 0.5f, 0.5f, 1,    // 19
                                0.5f, 0.5f, 0.5f, 1,    // 20
                                0.5f, 0.5f, 0.5f, 1,    // 21
                                0.5f, 0.5f, 0.5f, 1,    // 22
                                0.5f, 0.5f, 0.5f, 1,    // 23
                        },
                        new float[] { // normals
                                0, 0, 1,     // 0
                                0, 0, 1,     // 1
                                0, 0, 1,     // 2
                                0, 0, 1,     // 3
                                0, 0, -1,    // 4
                                0, 0, -1,    // 5
                                0, 0, -1,    // 6
                                0, 0, -1,    // 7
                                -1, 0, 0,    // 8
                                -1, 0, 0,    // 9
                                -1, 0, 0,    // 10
                                -1, 0, 0,    // 11
                                1, 0, 0,     // 12
                                1, 0, 0,     // 13
                                1, 0, 0,     // 14
                                1, 0, 0,     // 15
                                0, 1, 0,     // 16
                                0, 1, 0,     // 17
                                0, 1, 0,     // 18
                                0, 1, 0,     // 19
                                0, -1, 0,    // 20
                                0, -1, 0,    // 21
                                0, -1, 0,    // 22
                                0, -1, 0,    // 23
                        }
                );
                Constructor.body(entity_ID, Box.bodys, i);
                Constructor.location(
                        entity_ID,
                        Box.locations,
                        0f,
                        0f,
                        0f,
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
                        ROTATE,
                        8000,
                        0
                );
                Scene scene = new Scene();
                scene.light = new Light(
                        0f,0.8f,-1f,
                        1,1,1
                );
                scene.backGroundColor = new Color4f(0.8f,0.8f,0.8f);
                Camera.position(0,-0f,-4);
                Camera.rotation(20,0,0);
                return scene;
        }
}
