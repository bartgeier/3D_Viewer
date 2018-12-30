package bertrand.myopengl.ExampleScenes;

import android.content.Context;
import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.Constructor;
import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_Data;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_File_Loader;
import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.Texture_File_Loader;

import static bertrand.myopengl.Entitys.Box.Periode.Type.ROTATE;

public final class Stall {
        @NotNull
        public static void createScene(@NotNull RFile file) {
                final int entity_ID = Box.entity_ID_Generator.getID();
                final int light_ID = Box.light_ID_Generator.getID();

                Load.texturedShader(
                        Box.shadersPrograms,
                        Box.shadersDeleteInfos,
                        file.string(":/raw/shader_textured_vert.txt"),
                        file.string(":/raw/shader_textured_frag.txt")
                );

                OBJ_Data obj = OBJ_File_Loader.loadObjModel(
                        file,
                        ":/raw/stall_obj.obj"
                );
                Bitmap bitmap = Texture_File_Loader.getBitmap(
                        file,
                        ":/raw/stall_png.png"
                );
                Load.texturedModel(
                        entity_ID,
                        Box.bodys,
                        Box.bodyDeleteInfos,
                        bitmap,
                        obj.indices,
                        obj.positions,
                        obj.texCoords,
                        obj.normals
                );
                Constructor.location(
                        entity_ID,
                        Box.locations,
                        -0.5f,
                        -3f,
                        -3f,
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
                        16000,
                        0
                );
                Constructor.light(
                        light_ID,
                        Box.lights,
                        0f,-0.5f,-1f,
                        1,1,1
                );
                Constructor.backGroundColor(Box.backGround,0.8f,0.8f,0.8f);
                Camera.position(0,0,-5);
                Camera.rotation(20,0,0);
        }
}

