package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Random;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.Str;

import static bertrand.myopengl.Entitys.Box.Periode.Type.ROTATE_X;
import static bertrand.myopengl.Entitys.Box.Periode.Type.ROTATE_Y;
import static bertrand.myopengl.Entitys.Box.Periode.Type.ROTATE_Z;

public final class Cube_swarm {
        public static void createScene(@NotNull AssetManager asset) {
        try {
                Camera.translation(0,0f,-4);
                Camera.rotation(0,0,0);
                final int root_location_ID = add.location(
                        Box.locations,
                        0,
                        0, //dummy
                        0, //dummy
                        0, //dummy
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

                int shaderProgram_ID = Load.coloredShader(
                        Box.shaders,
                        Str.inputStreamToString(asset.open( "Shader/shader_colored_vert.txt")),
                        Str.inputStreamToString(asset.open( "Shader/shader_colored_frag.txt"))
                );

                final int mesh_ID = Load.coloredModel(
                        Box.meshes,
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
                                  //Front blue
                                  //r,   g,    b, a
                                  0f,   0f,   1f, 1,   // 0
                                  0f,   0f,   1f, 1,   // 1
                                  0f,   0f,   1f, 1,   // 2
                                  0f,   0f,   1f, 1,   // 3
                                  //Back yellow
                                  1f,   1f,   0f, 1,    // 4
                                  1f,   1f,   0f, 1,    // 5
                                  1f,   1f,   0f, 1,    // 6
                                  1f,   1f,   0f, 1,    // 7
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
                                  //Top cyan
                                  1f,   0f,   1f, 1,    // 16
                                  1f,   0f,   1f, 1,    // 17
                                  1f,   0f,   1f, 1,    // 18
                                  1f,   0f,   1f, 1,    // 19
                                  //Bottom magenta
                                  0f,   1f,   1f, 1,    // 20
                                  0f,   1f,   1f, 1,    // 21
                                  0f,   1f,   1f, 1,    // 22
                                  0f,   1f,   1f, 1,    // 23
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
                Random random = new Random();
                for (int i = 0; i < 200; i++) {
                        float x = random.nextFloat() * 100 - 50;
                        float y = random.nextFloat() * 100 - 50;
                        float z = random.nextFloat() * 100 - 50;
                        final int location_ID = add.location(
                                Box.locations,
                                0,
                                shaderProgram_ID,
                                Box.meshes.atId(mesh_ID).vao,
                                Box.meshes.atId(mesh_ID).indicesCount,
                                x,
                                y,
                                z,
                                0f,
                                0f,
                                0f,
                                1f,
                                1f,
                                1f
                        );

                        float startAngel = random.nextFloat() * 360;
                        startAngel %= 360;
                        float period_ms  = random.nextFloat() * 8000;
                        if(i%2 == 0) {
                                period_ms = -period_ms;
                        }
                        Box.Periode.Type periodeType;
                        switch(i%3) {
                        case 0:
                                periodeType = ROTATE_X;
                                break;
                        case 1:
                                periodeType = ROTATE_Y;
                                break;
                        case 2:
                                periodeType = ROTATE_Z;
                                break;
                        default:
                                periodeType = ROTATE_X;
                                break;
                        }
                        add.period(
                                location_ID,
                                Box.periods,
                                periodeType,
                                period_ms,
                                startAngel
                        );

                }
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
