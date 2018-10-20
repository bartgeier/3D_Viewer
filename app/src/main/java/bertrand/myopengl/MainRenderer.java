package bertrand.myopengl;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.SystemClock;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import bertrand.myopengl.OpenGL.ObjectModel;
import bertrand.myopengl.OpenGL.SimpleShader;

public class MainRenderer implements Renderer {
        private ArrayList<ObjectModel> objects = new ArrayList<>();

        @Override
        public void onDrawFrame(GL10 gl) {
                float dt = deltaTime();

                ObjectModel.renderBackground();
                for(ObjectModel object : objects) {
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
                        ObjectModel.projectionMatrix,
                        0,
                        85,
                        ratio,
                        0.1f,  //zNear should be never zero
                        150f
                );
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                SimpleShader shader = new SimpleShader();
                objects.add(new Triangle(shader));
                objects.add(new Triangle1(shader));
                objects.add(new CubeGray(shader));
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
