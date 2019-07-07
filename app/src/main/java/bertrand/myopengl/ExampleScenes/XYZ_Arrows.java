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
import bertrand.myopengl.Tool.Positions;
import bertrand.myopengl.Tool.Str;
import bertrand.myopengl.Tool.Vec3;

public final class XYZ_Arrows {
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

                int shaderProgram_ID = Load.texturedShader(
                        Box.shaders,
                        Str.inputStreamToString(asset.open( "Shader/shader_textured_vert.txt")),
                        Str.inputStreamToString(asset.open( "Shader/shader_textured_frag.txt"))
                );

                ModelData obj = OBJParser.transform(
                        asset.open("XYZ_Arrows/XYZ_Arrows.obj")
                );

                Bitmap bitmap = BitmapFactory.decodeStream(
                        asset.open("XYZ_Arrows/XYZ_Arrows.png")
                );

                int mesh_ID = Load.texturedModel(
                        Box.meshes,
                        bitmap,
                        obj.getIndices(),
                        obj.getVertices(),
                        obj.getTextureCoords(),
                        obj.getNormals()
                );

                Box.locations.add(
                        new Box.Location(
                                0,
                                shaderProgram_ID,
                                Box.meshes.atId(mesh_ID).vao,
                                Box.meshes.atId(mesh_ID).texId,
                                Box.meshes.atId(mesh_ID).indicesCount
                        )
                );

                Box.Camera camera = Box.cameras.atId(0);
                camera.location_ID = root_location_ID;
                Mathe.translationXYZ(camera.T,0,0,-25); //-25
                Mathe.rotationXYZ(camera.R, 0, 0, 0);

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

