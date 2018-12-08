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
import bertrand.myopengl.Tool.RFile.RFile;

public class MainRenderer implements Renderer {
        private Context context;
        private ExampleFactory exampleFactory;

        private ArrayList<ColoredModel> coloredObjects = new ArrayList<>();
        private ArrayList<TexturedModel> texturedObjects = new ArrayList<>();

        private class Example {
                boolean aNew = false;
                int position = -1;
        };

        Example example = new Example();

        MainRenderer (Context c) {
                context = c;
                exampleFactory = new ExampleFactory(context);
                RFile f = new RFile(c);
                f.inputStream(":raw/stall_obj_obj.obj");
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                float dt = deltaTime();

                if (example.aNew) {
                        loadExample();
                        example.aNew = false;
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

        private void loadExample() {
                cleanUp();
                ModelOptions m = exampleFactory.createExample(example.position);
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

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                String extensions = gl.glGetString(GL10.GL_VERSION);
                example.aNew = true;
                example.position = 3;

        }

        public void setExample(final int position) {
                example.aNew = true;
                example.position = position;
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
