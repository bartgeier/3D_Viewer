package bertrand.myopengl.ExampleObjects;

import android.content.Context;
import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


import bertrand.myopengl.Models.ModelOptions;
import bertrand.myopengl.Shaders.ColoredShader;
import bertrand.myopengl.Shaders.ShaderRepo;
import bertrand.myopengl.Shaders.TexturedShader;
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

        public ModelOptions createExample(int position) {
                if(shaderRepo == null) {
                        throw new AssertionError("ExampleFactory shaderRepo == null");
                }
                if(context == null) {
                        throw new AssertionError("ExampleFactory context == null");
                }

                ModelOptions modelOptions = new ModelOptions();
                switch(position) {
                case 0:
                        modelOptions.coloredModel  = new CubeGray(shaderRepo.coloredShader);
                        break;
                case 1:
                        modelOptions.coloredModel = new Triangle(shaderRepo.coloredShader);
                        break;
                case 2:
                        modelOptions.coloredModel = new Triangle1(shaderRepo.coloredShader);
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
                        modelOptions.texturedModel = new Stall(
                                shaderRepo.texturedShader,
                                bitmap,
                                obj.indices,
                                obj.positions,
                                obj.texCoords,
                                obj.normals
                        );
                        break;
                default:
                        /* do nothing */
                        break;
                }
                return modelOptions;
        }
}
