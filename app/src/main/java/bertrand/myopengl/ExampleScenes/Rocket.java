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
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.OBJ_FILE.ModelData;
import bertrand.myopengl.Tool.OBJ_FILE.OBJParser;
import bertrand.myopengl.Tool.Str;

public final class Rocket {
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
                Mathe.translationXYZ(camera.T,0,0,-5);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);

                int shaderProgram_ID = Load.texturedShader(
                        Box.shaders,
                        Str.inputStreamToString(asset.open( "Shader/shader_textured_vert.txt")),
                        Str.inputStreamToString(asset.open( "Shader/shader_textured_frag.txt"))
                );

                ModelData obj = OBJParser.transform(
                        asset.open("Rocket/rocket_obj.obj")
                );
                final int mesh_ID = Load.texturedModel(
                        Box.meshes,
                        obj.getIndices(),
                        obj.getVertices(),
                        obj.getTextureCoords(),
                        obj.getNormals()
                );

                Bitmap bitmap = BitmapFactory.decodeStream(
                        asset.open("Rocket/rocket_png.png")
                );
                int texture_ID = Load.texture(
                        Box.textures,
                        bitmap
                );

                Box.Location l = new Box.Location(
                        0,
                        shaderProgram_ID,
                        Box.meshes.atId(mesh_ID).vao,
                        Box.textures.atId(texture_ID).texNo,
                        Box.meshes.atId(mesh_ID).indicesCount
                );
                Box.locations.add(l);
                Matrix.rotateM(l.TF,0,90,1,0,0);

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

