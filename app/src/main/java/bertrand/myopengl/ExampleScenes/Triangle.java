package bertrand.myopengl.ExampleScenes;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.RFile.RFile;

import static bertrand.myopengl.Entitys.Box.Periode.Type.SWING;

public final class Triangle  {
        public static void createScene(@NotNull RFile file) {
                int shaderProgram_ID = Load.coloredShader(
                        Box.shaders,
                        file.string(":/raw/shader_colored_vert.txt"),
                        file.string(":/raw/shader_colored_frag.txt")
                );
                final int mesh_ID = Box.mesh_ID_Generator.getID();
                Load.coloredModel(
                        mesh_ID,
                        Box.meshes,
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
                final int location_ID = Box.location_ID_Generator.getID();
                add.location(
                        location_ID,
                        Box.locations,
                        shaderProgram_ID,
                        Box.meshes.get(mesh_ID).vao,
                        Box.meshes.get(mesh_ID).indicesCount,
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
                final int period_ID = Box.periode_ID_Generator.getID();
                add.period(
                        period_ID,
                        location_ID,
                        Box.periods,
                        SWING,
                        8000,
                        0
                );
                final int light_ID = Box.light_ID_Generator.getID();
                add.light(
                        light_ID,
                        Box.lights,
                        0f,-0.5f,-1f,
                        1,1,1
                );
                add.backGroundColor(Box.backGround,0.8f,0.8f,0.8f);
                Camera.translation(0,0,0);
                Camera.rotation(0,0,0);
        }
}
