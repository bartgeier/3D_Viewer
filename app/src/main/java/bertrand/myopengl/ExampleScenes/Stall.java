package bertrand.myopengl.ExampleScenes;

import android.content.Context;
import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.Constructor;
import bertrand.myopengl.Light.Light;
import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_Data;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_File_Loader;
import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.Texture_File_Loader;

import static bertrand.myopengl.Entitys.Box.Periode.Type.ROTATE;

public final class Stall {
        @NotNull
        public static Scene createScene(@NotNull Context context) {
                final int entity_ID = 0;
                OBJ_Data obj = OBJ_File_Loader.loadObjModel(
                        new RFile(context),
                        ":/raw/stall_obj.obj"
                );
                Bitmap bitmap = Texture_File_Loader.getBitmap(
                        new RFile(context),
                        ":/raw/stall_png.png"
                );
                Load.Info i = Load.texturedModel(
                        bitmap,
                        obj.indices,
                        obj.positions,
                        obj.texCoords,
                        obj.normals
                );
                Constructor.body(
                        entity_ID,
                        Box.bodys,
                        i
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
                Scene scene = new Scene();
                scene.light = new Light(
                        0f,-0.5f,-1f,
                        1,1,1
                );
                scene.backGroundColor = new Color4f(0.8f,0.8f,0.8f);
                Camera.position(0,0,-5);
                Camera.rotation(20,0,0);
                return scene;
        }

}

