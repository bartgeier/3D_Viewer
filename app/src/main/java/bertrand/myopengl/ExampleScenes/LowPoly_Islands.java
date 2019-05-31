package bertrand.myopengl.ExampleScenes;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.add;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.OBJ_FILE.ModelData;
import bertrand.myopengl.Tool.OBJ_FILE.OBJParser;
import bertrand.myopengl.Tool.Scene.Hierarchy;
import bertrand.myopengl.Tool.Scene.HierarchyData;
import bertrand.myopengl.Tool.Scene.SceneParser;
import bertrand.myopengl.Tool.Str;

public final class LowPoly_Islands {
        public static void createScene(@NotNull AssetManager asset) {
        try {
                Box.Camera camera = Box.cameras.atId(0);
                Mathe.translationXYZ(camera.T,0,0,-50);
                Mathe.rotationXYZ(camera.R, 0, 0, 0);

                int shaderProgram_ID = Load.texturedShader(
                        Box.shaders,
                        Str.inputStreamToString(asset.open( "Shader/shader_textured_vert.txt")),
                        Str.inputStreamToString(asset.open( "Shader/shader_textured_frag.txt"))
                );

                String[] names = SceneParser.names(
                        asset.open("LowPoly_Islands/Scene_Simple.csv")
                );
                for (String name: names) {
                        String obj_path = "LowPoly_Islands/" + name + ".obj";
                        String png_path = "LowPoly_Islands/" + name + ".png";
                        ModelData obj = OBJParser.transform(
                                asset.open(obj_path)
                        );
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                asset.open(png_path)
                        );

                        final int mesh_ID = Load.texturedModel(
                                Box.meshes,
                                bitmap,
                                obj.getIndices(),
                                obj.getVertices(),
                                obj.getTextureCoords(),
                                obj.getNormals()
                        );
                }
                Hierarchy hierarchy = new Hierarchy();
                hierarchy.createRoot();
                hierarchy.datas.get(0).rotX = 90f;//turn root into Device orientaion
                hierarchy.attach(
                        SceneParser.hierarchy(
                                asset.open("LowPoly_Islands/Scene_Simple.csv")
                        )
                );
                for (HierarchyData d: hierarchy.datas) {
                        add.location(
                                Box.locations,
                                d.parent_idx,
                                shaderProgram_ID,
                                Box.meshes.at(d.model_idx).vao,
                                Box.meshes.at(d.model_idx).texId,
                                Box.meshes.at(d.model_idx).indicesCount,

                                d.x,
                                d.y,
                                d.z,
                                d.rotX,
                                d.rotY,
                                d.rotZ,
                                d.scaleX,
                                d.scaleY,
                                d.scaleZ
                        );
                }
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

