package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.OBJ_FILE.ModelData;
import bertrand.myopengl.Tool.OBJ_FILE.OBJParser;
import bertrand.myopengl.Tool.Positions;
import bertrand.myopengl.Tool.Str;
import bertrand.myopengl.Tool.Vec3;

public final class Test_3 {
        public static void createScene(@NotNull AssetManager asset) {
        try {
                Camera.translation(0,0,-20);
                Camera.rotation(0,0,0);
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
                        90f,
                        0f,
                        0f,
                        1f,
                        1f,
                        1f
                );
                int shaderProgram_ID = Load.texturedShader(
                        Box.shaders,
                        Str.inputStreamToString(asset.open( "Shader/shader_textured_vert.txt")),
                        Str.inputStreamToString(asset.open( "Shader/shader_textured_frag.txt"))
                );

                ModelData obj = OBJParser.transform(
                        asset.open("LowPoly_Islands/waterTower.obj")
                );

                Bitmap bitmap = BitmapFactory.decodeStream(
                        asset.open("LowPoly_Islands/waterTower.png")
                );

                final Vec3 offset = Positions.offset(obj.getVertices());
                /*
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
                */
                int mesh_ID = Load.texturedModel(
                        Box.meshes,
                        bitmap,
                        obj.getIndices(),
                        obj.getVertices(),
                        obj.getTextureCoords(),
                        obj.getNormals()
                );

                add.location(
                        Box.locations,
                        0,
                        shaderProgram_ID,
                        Box.meshes.atId(mesh_ID).vao,
                        Box.meshes.atId(mesh_ID).texId,
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

                obj = OBJParser.transform(
                        asset.open("LowPoly_Islands/conifer.obj")
                );
                bitmap = BitmapFactory.decodeStream(
                        asset.open("LowPoly_Islands/conifer.png")
                );
                mesh_ID = Load.texturedModel(
                        Box.meshes,
                        bitmap,
                        obj.getIndices(),
                        obj.getVertices(),
                        obj.getTextureCoords(),
                        obj.getNormals()
                );
                add.location(
                        Box.locations,
                        0,
                        shaderProgram_ID,
                        Box.meshes.atId(mesh_ID).vao,
                        Box.meshes.atId(mesh_ID).texId,
                        Box.meshes.atId(mesh_ID).indicesCount,
                        0f,
                        0f,
                        5f,
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
        } catch (IOException e) {
                e.printStackTrace();
        }
        }
}

