package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Render;
import bertrand.myopengl.Entitys.Update;
import bertrand.myopengl.ExampleScenes.Cube;
import bertrand.myopengl.ExampleScenes.ClearScreen;
import bertrand.myopengl.ExampleScenes.Cube_1;
import bertrand.myopengl.ExampleScenes.Cube_swarm;
import bertrand.myopengl.ExampleScenes.ExampleNames;
import bertrand.myopengl.ExampleScenes.LowPoly_Islands;
import bertrand.myopengl.ExampleScenes.Stall;
import bertrand.myopengl.ExampleScenes.Rocket;
import bertrand.myopengl.ExampleScenes.Test_3;
import bertrand.myopengl.ExampleScenes.Triangle;
import bertrand.myopengl.ExampleScenes.Triangle_1;
import bertrand.myopengl.Tool.Time.DeltaTime;
import bertrand.myopengl.Tool.Time.StopWatch;

public final class MainRenderer implements Renderer {
        private Context context;
        private FrameMessageHandler frameMessageHandler;
        private ExampleNames.Index lastExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        private ExampleNames.Index newExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        private DeltaTime deltaTime = new DeltaTime();
        private StopWatch stopWatch = new StopWatch();

        MainRenderer (Context c, FrameMessageHandler h) {
                context = c;
                frameMessageHandler = h;
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                final float dt = deltaTime.dt();

                if(lastExampleIndex != newExampleIndex) {
                        ClearScreen.createScene();
                        switch(newExampleIndex) {
                        case CUBE:
                                Cube.createScene(context.getAssets());
                                break;
                        case CUBE_1:
                                Cube_1.createScene(context.getAssets());
                                break;
                        case CUBE_SWARM:
                                Cube_swarm.createScene(context.getAssets());
                                break;
                        case TRIANGLE:
                                Triangle.createScene(context.getAssets());
                                break;
                        case TRIANGLE_1:
                                Triangle_1.createScene(context.getAssets());
                                break;
                        case STALL:
                                Stall.createScene(context.getAssets());
                                break;
                        case ROCKET:
                                Rocket.createScene(context.getAssets());
                                break;
                        case LOW_POLY_ISLANDS:
                                LowPoly_Islands.createScene(context.getAssets());
                                break;
                        case TEST_3:
                                Test_3.createScene(context.getAssets());
                                        break;

                        default:
                                break;
                        }
                        Update.gpu_shader_projectionMatrix(
                                Box.shaders,
                                Camera.projectionMatrix()
                        );
                        lastExampleIndex = newExampleIndex;

                }
                Update.periods(Box.locations, Box.periods, dt);

                frameMessageHandler.sendMessage_FrameRateUpdate(stopWatch.avarage_ns(10));
                stopWatch.start_ns();

                Render.background(Box.backGround);
                Render.entitys(Camera.modelMatrix(), Box.lights, Box.locations, Box.shaders);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
                Camera.aspectRatio((float) width / height);
                Update.gpu_shader_projectionMatrix(Box.shaders, Camera.projectionMatrix());
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

        public void onDeviceOrientationChanged(final float[] rotationMatrix) {
                Camera.rotation(rotationMatrix);
        }

        public void onScaleFactorChanged(final float scaleFactor) {
                Camera.moveZ(scaleFactor);
        }
        public void onMove(final float x, final float y) {
                float[] vector = new float[4];
                float[] result = new float[4];
                vector[0] = x;
                vector[1] = y;
                vector[2] = 0;
                vector[3] = 1;
                float[] rotMatrix = new float[16];
                Matrix.transposeM(rotMatrix,0,Camera.rotationMatrix(),0);
                Matrix.multiplyMV(
                        result,
                        0,
                        rotMatrix,
                        0,
                        vector,
                        0
                );
                Box.Location l = Box.locations.at(0);
                l.position.x += result[0]/100;
                l.position.y += result[1]/100;
                l.position.z += result[2]/100;
                Matrix.setIdentityM(l.transformationMatrix, 0);
                Matrix.translateM(l.transformationMatrix,0,l.position.x, l.position.y, l.position.z);
                Matrix.rotateM(l.transformationMatrix,0, l.rotation.x, 1, 0, 0);
                Matrix.rotateM(l.transformationMatrix,0, l.rotation.y, 0, 1, 0);
                Matrix.rotateM(l.transformationMatrix,0, l.rotation.z,  0, 0, 1);
                Matrix.scaleM(l.transformationMatrix,0, l.scale.x, l.scale.y, l.scale.z);
        }
}
