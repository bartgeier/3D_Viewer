package bertrand.myopengl.ExampleObjects;

import android.content.Context;

import java.util.ArrayList;


import bertrand.myopengl.Models.ModelOptions;
import bertrand.myopengl.OBJ_PNG_Loader;
import bertrand.myopengl.Shaders.ColoredShader;
import bertrand.myopengl.Shaders.TexturedShader;
import bertrand.myopengl.Tool.RFile.RFile;

public class ExampleFactory {
        public ArrayList<String> names = new ArrayList<>();
        private Context context;

        public ExampleFactory(Context c) {
                context = c;
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
                ColoredShader coloredShader;
                TexturedShader texturedShader;
                ModelOptions modelOptions = new ModelOptions();
                switch(position) {
                case 0:
                        coloredShader = new ColoredShader();
                        modelOptions.coloredModel  = new CubeGray(coloredShader);
                        break;
                case 1:
                        coloredShader = new ColoredShader();
                        modelOptions.coloredModel = new Triangle(coloredShader);
                        break;
                case 2:
                        coloredShader = new ColoredShader();
                        modelOptions.coloredModel = new Triangle1(coloredShader);
                        break;
                case 3:
                        //texturedShader = new TexturedShader();
                        modelOptions.texturedModel = OBJ_PNG_Loader.loadObjModel(
                                new RFile(context),
                                ":/raw/stall_obj.obj",
                                ":/raw/stall_png.png");
                        break;
                default:
                        /* do nothing */
                        break;
                }
                return modelOptions;
        }
}