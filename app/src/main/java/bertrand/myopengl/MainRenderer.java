package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import bertrand.myopengl.ExampleObjects.ExampleFactory;
import bertrand.myopengl.Models.ModelOptions;
import bertrand.myopengl.Models.TexturedModel;
import bertrand.myopengl.Models.ColoredModel;
import bertrand.myopengl.OpenGL.GPU;



public class MainRenderer implements Renderer {
        private Context context;
        private ExampleFactory exampleFactory;

        private ArrayList<ColoredModel> coloredObjects = new ArrayList<>();
        private ArrayList<TexturedModel> texturedObjects = new ArrayList<>();


        enum WorkTodo {
                DONE,
                NEW_EXAMPLE,
                CLEAN_UP
        }
        class Admin {
                WorkTodo workTodo = WorkTodo.DONE;
                int exampleIdx = -1;
        };

        Admin admin = new Admin();

        MainRenderer (Context c) {
                context = c;
                exampleFactory = new ExampleFactory(context);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                float dt = deltaTime();

                if (admin.workTodo != WorkTodo.DONE) {
                        admin.workTodo = admin(admin);
                }

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
                loadExample(3);
        }

        public void onCleanUp() {
                admin.workTodo = WorkTodo.CLEAN_UP;
        }

        public void onNewExample(final int idx) {
                admin.workTodo = WorkTodo.NEW_EXAMPLE;
                admin.exampleIdx = idx;
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

        private WorkTodo admin(Admin admin) {
                switch(admin.workTodo) {
                        case NEW_EXAMPLE:
                                cleanUp();
                                loadExample(admin.exampleIdx);
                                break;
                        case CLEAN_UP:
                                cleanUp();
                                break;
                        default:
                                break;
                }
                return WorkTodo.DONE;
        }

        private void loadExample(int idx) {
                ModelOptions m = exampleFactory.createExample(idx);
                if( m.coloredModel != null) {
                        coloredObjects.add(m.coloredModel);
                }
                if(m.texturedModel != null) {
                        texturedObjects.add(m.texturedModel);
                }
        }

        private void cleanUp() {
                for(ColoredModel object : coloredObjects) {
                        object.cleanUp();
                }
                coloredObjects.clear();
                for(TexturedModel object : texturedObjects) {
                        object.cleanUp();
                }
                texturedObjects.clear();
        }


}
