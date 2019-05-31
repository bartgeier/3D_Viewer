package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.Str;

import static bertrand.myopengl.Entitys.Box.Periode.Type.SWING;

public final class Triangle  {
        public static void createScene(@NotNull AssetManager asset) {
        try {
                Box.Camera camera = Box.cameras.atId(0);
                Mathe.translationXYZ(camera.T,0,0,-1);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);
                final int root_location_ID = add.location(
                        Box.locations,
                        0,
                        0, //dummy
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
                final int location_ID = add.location(
                        Box.locations,
                        0,
                        shaderProgram_ID,
                        Box.meshes.atId(mesh_ID).vao,
                        0, //dummy
                        Box.meshes.atId(mesh_ID).indicesCount,
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
                add.period(
                        location_ID,
                        Box.periods,
                        SWING,
                        8000,
                        0
                );
                add.light(
                        Box.lights,
                        0f,-0.5f,-1f,
                        1,1,1
                );
                add.backGroundColor(Box.backGround,0.8f,0.8f,0.8f);

        } catch (IOException e) {
                e.printStackTrace();
        }
        }
}
