package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Render;
import bertrand.myopengl.Entitys.Update;
import bertrand.myopengl.ExampleScenes.Cube;
import bertrand.myopengl.ExampleScenes.ClearScreen;
import bertrand.myopengl.ExampleScenes.ExampleNames;
import bertrand.myopengl.ExampleScenes.Stall;
import bertrand.myopengl.ExampleScenes.Triangle;
import bertrand.myopengl.ExampleScenes.Triangle_1;
import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.Time.DeltaTime;


public final class MainRenderer implements Renderer {
        private Context context;
        private ExampleNames.Index lastExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        private ExampleNames.Index newExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        private DeltaTime deltaTime = new DeltaTime();

        MainRenderer (Context c) {
                context = c;
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                final float dt = deltaTime.dt();
                if(lastExampleIndex != newExampleIndex) {
                        ClearScreen.createScene();
                        switch(newExampleIndex) {
                        case CUBE:
                                Cube.createScene(new RFile(context));
                                break;
                        case TRIANGLE:
                                Triangle.createScene(new RFile(context));
                                break;
                        case TRIANGLE_1:
                                Triangle_1.createScene(new RFile(context));
                                break;
                        case STALL:
                                Stall.createScene(new RFile(context));
                                break;
                        default:
                                break;
                        }
                        Update.gpu_shader_projectionMatrix(
                                Box.shadersPrograms,
                                Camera.projectionMatrix()
                        );
                        lastExampleIndex = newExampleIndex;
                }
                Update.locations(Box.locations, Box.periods, dt);
                Update.bodys(Box.bodys, Camera.position(), Box.locations);
                Render.background(Box.backGround);
                Render.entitys(Box.lights, Box.bodys, Box.shadersPrograms);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
                Camera.aspectRatio((float) width / height);
                Update.gpu_shader_projectionMatrix(Box.shadersPrograms, Camera.projectionMatrix());
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                Camera.init();
                newExampleIndex = ExampleNames.Index.STALL;
        }

        public void onClearScreen() {
                newExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        }

        public void onNewExample(final int idx) {
                newExampleIndex = ExampleNames.Index.values()[idx];
        }

        public void onOrientationChanged(final float pitch, final float roll, final float azimut) {
                Camera.rotation(pitch, -roll, azimut);
        }

        public void onDeviceOrientationChanged(final float[] rotationMatrix) {
                Camera.rotation(rotationMatrix);
        }
}
