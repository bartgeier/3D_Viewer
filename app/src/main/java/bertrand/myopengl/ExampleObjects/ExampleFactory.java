package bertrand.myopengl.ExampleObjects;

import android.content.Context;
import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Light.Light;
import bertrand.myopengl.Shaders.ShaderRepo;
import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_Data;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_File_Loader;
import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.Texture_File_Loader;

public class ExampleFactory {
        public ArrayList<String> names = new ArrayList<>();
        private Context context = null;
        private ShaderRepo shaderRepo = null;

        public ExampleFactory() {
                intNames();
        }

        public ExampleFactory(@NotNull Context c, @NotNull ShaderRepo repo) {
                context = c;
                shaderRepo = repo;
                intNames();

        }
        private void intNames() {
                names.add("Cube");
                names.add("Triangle");
                names.add("Triangle1");
                names.add("Stall");
                names.add("Test_2");
                names.add("Test_3");
                names.add("Test_4");
                names.add("Test_5");
                names.add("Test_6");
                names.add("Test_7");
                names.add("Test_8");
                names.add("Test_9");
                names.add("Test_10");
        }

        @Nullable
        public Scene createExample(int position) {
                if(shaderRepo == null) {
                        throw new AssertionError("ExampleFactory shaderRepo == null");
                }
                if(context == null) {
                        throw new AssertionError("ExampleFactory context == null");
                }

                Scene scene = new Scene();
                //RawModel rawModel = null;
                switch(position) {
                case 0:
                        scene.rawModel  = new CubeGray(shaderRepo.coloredShader);
                        scene.light = new Light(
                                0f,0.8f,-1f,
                                1,1,1
                        );
                        scene.backGroundColor = new Color4f(0.8f,0.8f,0.8f);
                        Camera.position(0,-0f,-4);
                        Camera.rotation(20,0,0);
                        break;
                case 1:
                        scene.rawModel = new Triangle(shaderRepo.coloredShader);
                        scene.light = new Light(
                                0f,0.8f,-1f,
                                1,1,1
                        );
                        scene.backGroundColor = new Color4f(0.8f,0.8f,0f);
                        Camera.position(0,0,0);
                        Camera.rotation(0,0,0);
                        break;
                case 2:
                        scene.rawModel = new Triangle1(shaderRepo.coloredShader);
                        scene.light = new Light(
                                0f,0f,-1f,
                                1,1,1
                        );
                        scene.backGroundColor = new Color4f(0.8f,0.8f,0.8f);
                        Camera.position(0,0,2);
                        Camera.rotation(0,0,0);
                        break;
                case 3:
                        OBJ_Data obj = OBJ_File_Loader.loadObjModel(
                                new RFile(context),
                                ":/raw/stall_obj.obj"
                        );
                        Bitmap bitmap =  Texture_File_Loader.getBitmap(
                                new RFile(context),
                                ":/raw/stall_png.png"
                        );
                        scene.rawModel = new Stall(
                                shaderRepo.texturedShader,
                                bitmap,
                                obj.indices,
                                obj.positions,
                                obj.texCoords,
                                obj.normals
                        );
                        scene.light = new Light(
                                0f,-0.5f,-1f,
                                1,1,1
                        );
                        scene.backGroundColor = new Color4f(0.8f,0.8f,0.8f);
                        Camera.position(0,0,-5);
                        Camera.rotation(20,0,0);
                        break;
                default:
                        /* do nothing */
                        scene = null;
                        break;
                }
                return scene;
        }
}
