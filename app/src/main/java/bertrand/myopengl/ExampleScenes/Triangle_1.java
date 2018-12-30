package bertrand.myopengl.ExampleScenes;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.Constructor;
import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.RFile.RFile;

import static bertrand.myopengl.Entitys.Box.Periode.Type.UNDEF;

public final class Triangle_1 {
        @NotNull
        public static void createScene(@NotNull RFile file) {
                final int entity_ID = Box.entity_ID_Generator.getID();
                final int light_ID = Box.light_ID_Generator.getID();

                Load.coloredShader(
                        Box.shadersPrograms,
                        Box.shadersDeleteInfos,
                        file.string(":/raw/shader_colored_vert.txt"),
                        file.string(":/raw/shader_colored_frag.txt")
                );

                Load.coloredModel(
                        entity_ID,
                        Box.bodys,
                        Box.bodyDeleteInfos,
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
                Constructor.location(
                        entity_ID,
                        Box.locations,
                        0f,
                        0f,
                        -1f,
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
                        UNDEF,
                        8000,
                        0
                );
                Constructor.light(
                        light_ID,
                        Box.lights,
                        0f,-0.5f,-1f,
                        1,1,1
                );
                Constructor.backGroundColor(Box.backGround,0.8f,0.8f,0.8f);
                Camera.position(0,0,0);
                Camera.rotation(0,0,0);
        }
}
