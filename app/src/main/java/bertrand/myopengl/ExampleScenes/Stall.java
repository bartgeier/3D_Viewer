package bertrand.myopengl.ExampleScenes;

import android.graphics.Bitmap;
import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.Constructor;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_Data;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_File_Loader;
import bertrand.myopengl.Tool.Positions;
import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.Texture_File_Loader;
import bertrand.myopengl.Tool.Vec3;

import static bertrand.myopengl.Entitys.Box.Periode.Type.UNDEF;

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

                // translate vertecis around world origin
                // rotate vertecis and normas orientation to North-South direction
                final Vec3 offset = Positions.offset(obj.positions);
                final float[] translationMatrix = new float[16];
                Matrix.setIdentityM(translationMatrix, 0);
                Matrix.rotateM(translationMatrix,0, 90, 1, 0, 0);
                Matrix.rotateM(translationMatrix,0, 90, 0, 1, 0);
                Matrix.rotateM(translationMatrix,0, 0,  0, 0, 1);
                final float[] normals = Positions.multiplyMatrix(
                        obj.normals,
                        translationMatrix
                );
                Matrix.translateM(translationMatrix,0, -offset.x, -offset.y, -offset.z);
                final float[] positions = Positions.multiplyMatrix(
                        obj.positions,
                        translationMatrix
                );

                Load.texturedModel(
                        entity_ID,
                        Box.bodys,
                        Box.bodyDeleteInfos,
                        bitmap,
                        obj.indices,
                        positions,
                        obj.texCoords,
                        normals
                );
                Constructor.location(
                        entity_ID,
                        Box.locations,
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
                Constructor.period(
                        entity_ID,
                        Box.periods,
                        UNDEF,
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
                Camera.translation(0,0,-8);
                Camera.rotation(0,0,0);
        }
}

