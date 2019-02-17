package bertrand.myopengl.ExampleScenes;

import android.graphics.Bitmap;
import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.OBJ_FILE.ModelData;
import bertrand.myopengl.Tool.OBJ_FILE.OBJParser;
import bertrand.myopengl.Tool.Positions;
import bertrand.myopengl.Tool.RFile.RFile_IF;
import bertrand.myopengl.Tool.Texture_File;
import bertrand.myopengl.Tool.Vec3;

public final class Stall {
        public static void createScene(@NotNull RFile_IF file) {
                int shaderProgram_ID = Load.texturedShader(
                        Box.shaders,
                        file.string(":/raw/shader_textured_vert.txt"),
                        file.string(":/raw/shader_textured_frag.txt")
                );

                ModelData obj = OBJParser.transform(
                        file,
                        ":/raw/stall_obj.obj"
                );
                Bitmap bitmap = Texture_File.readBitmap(
                        file,
                        ":/raw/stall_png.png"
                );

                final Vec3 offset = Positions.offset(obj.getVertices());
                final float[] translationMatrix = new float[16];
                Matrix.setIdentityM(translationMatrix, 0);
                Matrix.rotateM(translationMatrix,0, 90, 1, 0, 0);
                Matrix.rotateM(translationMatrix,0, 90, 0, 1, 0);
                Matrix.rotateM(translationMatrix,0, 0,  0, 0, 1);
                final float[] normals = Positions.multiplyMatrix(
                        translationMatrix,
                        obj.getNormals()
                );
                Matrix.translateM(translationMatrix,0, -offset.x, -offset.y, -offset.z);
                final float[] positions = Positions.multiplyMatrix(
                        translationMatrix,
                        obj.getVertices()
                );

                final int mesh_ID = Load.texturedModel(
                        Box.meshes,
                        bitmap,
                        obj.getIndices(),
                        positions,
                        obj.getTextureCoords(),
                        normals
                );
                add.location(
                        Box.locations,
                        shaderProgram_ID,
                        Box.meshes.atId(mesh_ID).vao,
                        Box.meshes.atId(mesh_ID).indicesCount,
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
                add.light(
                        Box.lights,
                        0f,-0.5f,-1f,
                        1,1,1
                );
                add.backGroundColor(Box.backGround,0.8f,0.8f,0.8f);
                Camera.translation(0,0,-8);
                Camera.rotation(0,0,0);
        }
}

