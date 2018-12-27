package bertrand.myopengl.AExamples;

import android.content.Context;
import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.constructor;
import bertrand.myopengl.ExampleObjects.CubeGray;
import bertrand.myopengl.ExampleObjects.Scene;
import bertrand.myopengl.ExampleObjects.Stall;
import bertrand.myopengl.ExampleObjects.Triangle;
import bertrand.myopengl.ExampleObjects.Triangle1;
import bertrand.myopengl.Light.Light;
import bertrand.myopengl.Shaders.ShaderRepo;
import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_Data;
import bertrand.myopengl.Tool.OBJ_FILE.OBJ_File_Loader;
import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.Texture_File_Loader;

import static bertrand.myopengl.Entitys.Box.Periode.Type.ROTATE;

public class AExampleFactory {
        public ArrayList<String> names = new ArrayList<>();
        private Context context = null;
        private ShaderRepo shaderRepo = null;

        public AExampleFactory() {
                intNames();
        }

        public AExampleFactory(@NotNull Context c, @NotNull ShaderRepo repo) {
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
                case 3:
                        OBJ_Data obj = OBJ_File_Loader.loadObjModel(
                                new RFile(context),
                                ":/raw/stall_obj.obj"
                        );
                        Bitmap bitmap =  Texture_File_Loader.getBitmap(
                                new RFile(context),
                                ":/raw/stall_png.png"
                        );
                        Load.Info i = Load.texturedModel(
                                shaderRepo.texturedShader,
                                bitmap,
                                obj.indices,
                                obj.positions,
                                obj.texCoords,
                                obj.normals
                        );
                        constructor.body(0, Box.bodys, i);
                        constructor.location(
                                0,
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
                        constructor.period(
                                0,
                                Box.periods,
                                ROTATE,
                                8000,
                                0
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
