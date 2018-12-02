package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import bertrand.myopengl.Models.TexturedModel;
import bertrand.myopengl.Models.ColoredModel;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Shaders.ColoredShader;
import bertrand.myopengl.Tool.RFile.RFile;

public class MainRenderer implements Renderer {

        private Context context;


        private ArrayList<ColoredModel> coloredObjects = new ArrayList<>();
        private ArrayList<TexturedModel> texturedObjects = new ArrayList<>();


        MainRenderer (Context c) {
                context = c;
                RFile f = new RFile(c);
                f.inputStream(":raw/stall_obj_obj.obj");
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                float dt = deltaTime();

                GPU.renderBackground();
                for(ColoredModel object : coloredObjects) {
                        object.updateWithDelta(dt);
                        float[] viewMatrix = new float[16];
                        Matrix.setIdentityM(viewMatrix, 0);
                        Matrix.translateM(viewMatrix,0,0, 0f, -5);
                        Matrix.rotateM(viewMatrix,0, 20, 1, 0, 0);
                        object.render(viewMatrix);
                }

                for(TexturedModel object : texturedObjects) {
                        object.updateWithDelta(dt);
                        float[] viewMatrix = new float[16];
                        Matrix.setIdentityM(viewMatrix, 0);
                        Matrix.translateM(viewMatrix,0,0, 0f, -5);
                        Matrix.rotateM(viewMatrix,0, 20, 1, 0, 0);
                        object.render(viewMatrix);
                }
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
                float ratio = (float) width / height;
                Matrix.perspectiveM(
                        ColoredModel.projectionMatrix,
                        0,
                        85,
                        ratio,
                        0.1f,  //zNear should be never zero
                        150f
                );
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                String extensions = gl.glGetString(GL10.GL_VERSION);
                ColoredShader shader = new ColoredShader();
                //coloredObjects.add(new Triangle(shader));
                //coloredObjects.add(new Triangle1(shader));
                //coloredObjects.add(new CubeGray(shader));
                //coloredObjects.add(OBJLoader.loadObjModel(new RFile(context), ":/raw/stall_obj.obj"));
                texturedObjects.add(OBJ_PNG_Loader.loadObjModel(new RFile(context), ":/raw/stall_obj.obj", ":/raw/stall_png.png"));
        }

        private double lasttime = 0;
        private float deltaTime() {
                final double time = SystemClock.elapsedRealtime();
                float delta;
                if (lasttime == 0) {
                        delta = 0;
                } else {
                        delta = (float)(time - lasttime);
                }
                lasttime = time;
                return delta;
        }

}
