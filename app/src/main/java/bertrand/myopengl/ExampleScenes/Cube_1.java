package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.Str;

public final class Cube_1 {
        public static void createScene(@NotNull AssetManager asset) {
        try {

                final int root_location_ID = Box.locations.add(
                        new Box.Location(
                                0,
                                0,
                                0,
                                0,
                                0
                        )
                );
                Box.Camera camera = Box.cameras.atId(0);
                camera.location_ID = root_location_ID;
                Mathe.translationXYZ(camera.T,0,0,-8);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);

                int shaderProgram_ID = Load.coloredShader(
                        Box.shaders,
                        Str.inputStreamToString(asset.open( "Shader/shader_colored_vert.txt")),
                        Str.inputStreamToString(asset.open( "Shader/shader_colored_frag.txt"))
                );
                final int mesh_ID = Load.coloredModel(
                        Box.meshes,
                        new int[] { // indices
                                // Top
                                0, 1, 2,
                                2, 3, 0,
                                // Bottom
                                4, 5, 6,
                                6, 7, 4,
                                // Left
                                8, 9, 10,
                                10, 11, 8,
                                // Right
                                12, 13, 14,
                                14, 15, 12,
                                // Back
                                16, 17, 18,
                                18, 19, 16,
                                // Front
                                20, 21, 22,
                                22, 23, 20
                        },
                        new float[] { // positions
                                // Top
                                1, -1,  1,    // 0
                                1,  1,  1,    // 1
                                -1,  1,  1,   // 2
                                -1,  -1, 1,   // 3
                                // Bottom
                                -1, -1, -1,   // 4
                                -1,  1, -1,   // 5
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
                                // Back
                                1,  1,  1,    // 16
                                1,  1, -1,    // 17
                                -1,  1, -1,   // 18
                                -1,  1,  1,   // 19
                                // Front
                                1, -1, -1,    // 20
                                1, -1,  1,    // 21
                                -1, -1,  1,   // 22
                                -1, -1, -1,   // 23
                        },
                        new float[] { // colors
                                  //Top cyan
                                  //r,   g,    b, a
                                  0f,   1f,   1f, 1,    // 0
                                  0f,   1f,   1f, 1,    // 1
                                  0f,   1f,   1f, 1,    // 2
                                  0f,   1f,   1f, 1,    // 3
                                  //Bottom magenta
                                  1f,   0f,   1f, 1,    // 4
                                  1f,   0f,   1f, 1,    // 5
                                  1f,   0f,   1f, 1,    // 6
                                  1f,   0f,   1f, 1,    // 7
                                  //Left red
                                  1f,   0f,   0f, 1,   // 8
                                  1f,   0f,   0f, 1,   // 9
                                  1f,   0f,   0f, 1,   // 10
                                  1f,   0f,   0f, 1,   // 11
                                  //Right green
                                  0f,   1f,   0f, 1,   // 12
                                  0f,   1f,   0f, 1,   // 13
                                  0f,   1f,   0f, 1,   // 14
                                  0f,   1f,   0f, 1,   // 15
                                  //Back yellow
                                  1f,   1f,   0f, 1,   // 16
                                  1f,   1f,   0f, 1,   // 17
                                  1f,   1f,   0f, 1,   // 18
                                  1f,   1f,   0f, 1,   // 19
                                  //Front blue
                                  0f,   0f,   1f, 1,   // 20
                                  0f,   0f,   1f, 1,   // 21
                                  0f,   0f,   1f, 1,   // 22
                                  0f,   0f,   1f, 1,   // 23



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
                Box.locations.add(
                        new Box.Location(
                                0,
                                shaderProgram_ID,
                                Box.meshes.atId(mesh_ID).vao,
                                0, //dummy
                                Box.meshes.atId(mesh_ID).indicesCount
                        )
                );
                add.light(
                        Box.lights,
                        0f,0.8f,-1f,
                        1,1,1
                );
                add.backGroundColor(Box.backGround,0.8f,0.8f,0.8f);
        } catch (IOException e) {
                e.printStackTrace();
        }
        }
}
