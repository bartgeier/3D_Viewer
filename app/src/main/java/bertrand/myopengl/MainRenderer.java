package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.SystemClock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.ExampleObjects.ExampleFactory;
import bertrand.myopengl.Models.ModelOptions;
import bertrand.myopengl.Models.TexturedModel;
import bertrand.myopengl.Models.ColoredModel;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Shaders.ShaderRepo;


public class MainRenderer implements Renderer {
        private Context context;
        private ExampleFactory exampleFactory;
        private ShaderRepo shaderRepo;

        private ArrayList<ColoredModel> coloredObjects = new ArrayList<>();
        private ArrayList<TexturedModel> texturedObjects = new ArrayList<>();


        enum WorkTodo {
                DONE,
                NEW_EXAMPLE,
                CLEAR_SCREEN_UP
        }
        class Admin {
                WorkTodo workTodo = WorkTodo.DONE;
                int exampleIdx = -1;
        };

        private Admin admin = new Admin();

        MainRenderer (Context c) {
                context = c;

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
                        object.render(Camera.position());
                }
                for(TexturedModel object : texturedObjects) {
                        object.updateWithDelta(dt);
                        object.render(Camera.position());
                }
        }


        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
                float r = (float) width / height;
                Camera.aspectRatio(r);
                shaderRepo.setProjectionMatrix(Camera.projectionMatrix());
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                String extensions = gl.glGetString(GL10.GL_VERSION);
                        Camera.init();
                        shaderRepo = new ShaderRepo();
                        exampleFactory = new ExampleFactory(context, shaderRepo);
                        loadExample(3);
        }

        public void onClearScreen() {
                admin.workTodo = WorkTodo.CLEAR_SCREEN_UP;
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

        private WorkTodo admin(@NotNull final Admin admin) {
                switch(admin.workTodo) {
                        case NEW_EXAMPLE:
                                clearScreen();
                                loadExample(admin.exampleIdx);
                                break;
                        case CLEAR_SCREEN_UP:
                                clearScreen();
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

        private void clearScreen() {
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
