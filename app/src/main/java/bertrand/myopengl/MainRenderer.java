package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.Time.DeltaTime;
import bertrand.myopengl.Tool.Time.StopWatch;

import static java.lang.StrictMath.abs;

public final class MainRenderer implements Renderer {
        private Context context;
        private FrameMessageHandler frameMessageHandler;
        private ExampleNames.Index lastExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        private ExampleNames.Index newExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        private DeltaTime deltaTime = new DeltaTime();
        private StopWatch stopWatch = new StopWatch();
        private final int cameraId;

        MainRenderer (Context c, FrameMessageHandler h) {
                context = c;
                frameMessageHandler = h;
                cameraId = Box.cameras.add( new Box.Camera(
                        0, //root
                        1f,
                        85f,
                        0.1f,
                        300f
                ));
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                final float dt = deltaTime.dt();
                Box.Camera camera = Box.cameras.atId(cameraId);

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
                        Matrix.perspectiveM(Update.matrix,0, camera.fovyZoomAngle, camera.aspectRatio, camera.near, camera.far);
                        Update.gpu_shader_projectionMatrix(
                                Box.shaders,
                                Update.matrix
                        );
                        lastExampleIndex = newExampleIndex;

                }
                Update.periods(Box.locations, Box.periods, dt);

                frameMessageHandler.sendMessage_FrameRateUpdate(stopWatch.avarage_ns(10));
                stopWatch.start_ns();

                Render.background(Box.backGround);
                Matrix.multiplyMM(Render.matrix,0,camera.T,0,camera.R,0);
                Render.entitys(Render.matrix, Box.lights, Box.locations, Box.shaders);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
                Box.Camera c = Box.cameras.atId(cameraId);
                c.aspectRatio = (float)width / height;
                Matrix.perspectiveM(Update.matrix,0, c.fovyZoomAngle, c.aspectRatio, c.near, c.far);
                Update.gpu_shader_projectionMatrix(Box.shaders, Update.matrix);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                Box.Camera c = Box.cameras.atId(cameraId);
                newExampleIndex = ExampleNames.Index.STALL;
        }

        public void onClearScreen() {
                newExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        }

        public void onNewExample(final int idx) {
                newExampleIndex = ExampleNames.Index.values()[idx];
        }

        public void onDeviceOrientationChanged(final float[] rotationMatrix) {
                Box.Camera c = Box.cameras.atId(cameraId);
                c.R = rotationMatrix;
        }

        public void onTouchScreenScaling(final float scaleFactor) {
                Box.Camera c = Box.cameras.atId(cameraId);
                Mathe.Tz(c.T,-Mathe.adjustDistance(Mathe.Tz(c.T),1/scaleFactor,0.01f));
        }

        public void onTouchScreenMoving(final float dx, final float dy) {
                Box.Camera camera = Box.cameras.atId(cameraId);
                float[] vector = new float[4];
                float[] result = new float[4];
                vector[0] = dx/10 * (abs(Mathe.Tz(camera.T)) + 1);
                vector[1] = dy/10 * (abs(Mathe.Tz(camera.T)) + 1);
                vector[2] = 0;
                vector[3] = 1;
                float[] rotMatrix = new float[16];
                Matrix.transposeM(rotMatrix,0,camera.R,0);
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
