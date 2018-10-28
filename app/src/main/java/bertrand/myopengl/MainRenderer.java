package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import bertrand.myopengl.OpenGL.ColoredModel;
import bertrand.myopengl.OpenGL.SimpleShader;

public class MainRenderer implements Renderer {

        private Context context;

        private ArrayList<ColoredModel> objects = new ArrayList<>();


        MainRenderer (Context c) {
                context = c;
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                float dt = deltaTime();

                ColoredModel.renderBackground();
                for(ColoredModel object : objects) {
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
                SimpleShader shader = new SimpleShader();
                objects.add(new Triangle(shader));
                objects.add(new Triangle1(shader));
                objects.add(new CubeGray(shader));
                objects.add(OBJLoader.loadObjModel(stall()));
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

        private InputStreamReader stall() {
                //http://www.learnopengles.com/android-lesson-four-introducing-basic-texturing/

                //Resources res = getContext().getResources();
                //int id = R.drawable.image;
                //Bitmap b = BitmapFactory.decodeResource(res, id);

                //final BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inScaled = false;   // No pre-scaling


                //final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.raw.stall);
                //final Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

                InputStream s = context.getResources().openRawResource(R.raw.stall);
                InputStreamReader streamReader = new InputStreamReader(s);
                return streamReader;
        }
}
