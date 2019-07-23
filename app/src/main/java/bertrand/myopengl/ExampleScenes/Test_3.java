package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.Circle;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.OBJ_FILE.ModelData;
import bertrand.myopengl.Tool.OBJ_FILE.OBJParser;
import bertrand.myopengl.Tool.Positions;
import bertrand.myopengl.Tool.Str;
import bertrand.myopengl.Tool.Vec3;

public final class Test_3 {
        public static void createScene(@NotNull AssetManager asset) {
        try {
                final int guiRoot_location_ID = Box.guiLocations.add(
                        new Box.Location(
                                0,
                                0,
                                0,
                                0,
                                0
                        )
                );

                int shaderProgram_ID = Load.quadShader(
                        Box.guiShaders,
                        Str.inputStreamToString(asset.open( "Shader/shader_gui_vert.txt")),
                        Str.inputStreamToString(asset.open( "Shader/shader_gui_frag.txt"))

                );

                Bitmap bitmap = BitmapFactory.decodeStream(
                        asset.open("PlusButton.png")
                );

                int mesh_ID = Load.texturedQuad(
                        Box.meshes,
                        bitmap
                );

                Box.Location  guiLocation = new Box.Location(
                        0,
                        shaderProgram_ID,
                        Box.meshes.atId(mesh_ID).vao,
                        Box.meshes.atId(mesh_ID).texId,
                        Box.meshes.atId(mesh_ID).indicesCount
                );
                //Matrix.translateM(guiLocation.TF, 0, 0.2f, 0, 0);

                Box.Camera camera = Box.cameras.atId(0);
                Box.Display display = Box.displays.atId(0);

                float factor = 2.0f/display.width;
                Matrix.translateM(guiLocation.TF,0, -0.7f, -0.7f/camera.aspectRatio,0f);
                Matrix.scaleM(guiLocation.TF,0, factor*128, factor*128f,1.0f);
                int location_id = Box.guiLocations.add(guiLocation);
                Box.circleColliders.add(new Box.CircleCollider(location_id, factor*72.0f));

                Box.Location root3D = new Box.Location(
                        0,
                        0,
                        0,
                        0,
                        0
                );
                final int root_location_ID = Box.locations.add(root3D);


                camera.location_ID = root_location_ID;
                Mathe.translationXYZ(camera.T,0,0,-8);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);


                  // Matrix.translateM(l.TF,0,0f,0f,5f);
               // Mathe.rotateM_withQuaternion(l.TF,0.5f,0f,0.5f,0.5f);

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

