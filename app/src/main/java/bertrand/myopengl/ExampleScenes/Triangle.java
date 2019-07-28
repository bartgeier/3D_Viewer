package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.Str;

public final class Triangle  {
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
                Mathe.translationXYZ(camera.T,0,0,-3);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);

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
                                0.0f,  0.5f, 0f,
                               -0.5f, -0.5f, 0f,
                                0.5f, -0.5f, 0f,
                        },
                        new float[] { //colors
                                1.0f, 0.06f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f, 1.0f,
                                0.0f, 1.0f, 0.0f, 1.0f,
                        },
                        new float[] { //normals
                                0, 0, 1,
                                0, 0, 1,
                                0, 0, 1,
                        }
                );
                final int location_ID = Box.locations.add(
                        new Box.Location(
                                0,
                                shaderProgram_ID,
                                Box.meshes.atId(mesh_ID).vao,
                                0,
                                Box.meshes.atId(mesh_ID).indicesCount
                        )
                );

                Box.Swing swing = new Box.Swing(
                        location_ID,
                        8000, 0, 3,0
                );
                Box.swings.add(swing);

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
