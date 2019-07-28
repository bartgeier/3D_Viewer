package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

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
import bertrand.myopengl.ExampleScenes.XYZ_Arrows;
import bertrand.myopengl.Tool.Circle;
import bertrand.myopengl.Tool.Collision.Gui;
import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.GLMathe;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.SparseArray.SparseArray;
import bertrand.myopengl.Tool.Time.DeltaTime;
import bertrand.myopengl.Tool.Time.StopWatch;
import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.Tst;

import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

public final class MainRenderer implements Renderer {
        private Context context;
        private FrameMessageHandler frameMessageHandler;
        private ExampleNames.Index lastExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        private ExampleNames.Index newExampleIndex = ExampleNames.Index.CLEAR_SCREEN;
        private DeltaTime deltaTime = new DeltaTime();
        private StopWatch stopWatch = new StopWatch();
        private final int cameraId;
        private final int displayId;

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

                displayId = Box.displays.add( new Box.Display(
                        1,
                        1
                ));

               // Circle circle = new Circle(-0.8f, -0.8f, 0.1f);
               // Box.circleColliders.add(circle);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                final float dt = deltaTime.dt();
                Box.Camera camera = Box.cameras.atId(cameraId);

                if(lastExampleIndex != newExampleIndex) {
                        ClearScreen.cleanUp();
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
                                case XYZ_Arrows:
                                        XYZ_Arrows.createScene(context.getAssets());
                                        break;
                                case TEST_3:
                                        Test_3.createScene(context.getAssets());
                                        break;
                                default:
                                        ClearScreen.createScene();
                                        break;
                        }
                        Matrix.perspectiveM(
                                Update.matrix,
                                0,
                                camera.fovyZoomAngle,
                                camera.aspectRatio,
                                camera.near,
                                camera.far
                        );
                        Update.gpu_shader_projectionMatrix(
                                Box.shaders,
                                Update.matrix
                        );
                        Matrix.setIdentityM(Update.matrix, 0);
                        Matrix.scaleM(Update.matrix, 0,  1, camera.aspectRatio, 1);
                        Update.gpu_shader_projectionMatrix(
                                Box.guiShaders,
                                Update.matrix
                        );
                        lastExampleIndex = newExampleIndex;

                }
                Update.swings(Box.locations, Box.swings, dt);
                Update.spins(Box.locations, Box.spin, dt);

                frameMessageHandler.sendMessage_FrameRateUpdate(stopWatch.avarage_ns(10));
                stopWatch.start_ns();

                Render.background(Box.backGround);
                Matrix.multiplyMM(Render.matrixA,0,camera.T,0,camera.R,0);
                Render.camera(Render.matrixB,Box.locations, camera.location_ID);
                Matrix.invertM(Render.matrixC,0,Render.matrixB,0);
                Matrix.multiplyMM(Render.matrixB,0,Render.matrixA,0,Render.matrixC,0);

                Render.entitys(Render.matrixB, Box.lights, Box.locations, Box.shaders);

                Matrix.setIdentityM(Render.matrixA,0);
                Render.guis(Render.matrixA, Box.guiLocations, Box.guiShaders);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
                Box.Camera camera = Box.cameras.atId(cameraId);
                Box.Display display = Box.displays.atId(displayId);
                display.width = width;
                display.height = height;
                camera.aspectRatio = (float)display.width / display.height;
                Matrix.perspectiveM(
                        Update.matrix,
                        0,
                        camera.fovyZoomAngle,
                        camera.aspectRatio,
                        camera.near,
                        camera.far
                );
                Update.gpu_shader_projectionMatrix(Box.shaders, Update.matrix);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
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

        //x and y between -1 and +1
        public void onTouchAdd(final int id, final Vec2 point, Vec2 viewSize) {
                if(id >= Box.touchs.length()) {
                        return;
                }
                final Box.Camera camera = Box.cameras.atId(cameraId);
                Box.Display display = Box.displays.atId(displayId);

                GLMathe.pixel_to_glCoordinateSystem(point, viewSize);
                Box.Touch touch = new Box.Touch(point);
                Box.touchs.replaceId(id,touch);

                final Vec2 p = new Vec2(touch.point.x,touch.point.y / camera.aspectRatio);
                Box.tabs.clear();
                Gui.circleCollision(Box.tabs, Box.circleColliders, Box.guiLocations, p);
                Gui.reduseTabActions(Box.tabs, 0);
                Gui.excutePressTabAction(Box.guiLocations, Box.tabActions, Box.tabs);
                Box.tabs.clear();
        }

        // x and y between -1 and +1
        public void onTouchChanged(final int id, final Vec2 pixel, Vec2 viewSize) {
                if(id >= Box.touchs.length() || Box.touchs.getIndex(id) < 0) {
                        return;
                }
                final Box.Camera camera = Box.cameras.atId(cameraId);
                final Box.Touch touch = Box.touchs.atId(id);
                final Vec2 last = touch.point.copy();
                GLMathe.pixel_to_glCoordinateSystem(touch.point, pixel, viewSize);
                Vec2.sub(touch.delta, touch.point, last);

                final Box.Touch guiTouch = new Box.Touch(touch.point, touch.delta);
                guiTouch.point.y = guiTouch.point.y / camera.aspectRatio;
                guiTouch.delta.y = guiTouch.delta.y / camera.aspectRatio;
                //guiTouch in normalized pixel coordinates
                Vec2.sub(last, guiTouch.point, guiTouch.delta);

                Box.tabs.clear();

                Gui.circleCollision(Box.tabs, Box.circleColliders, Box.guiLocations, last);
                Gui.reduseTabActions(Box.tabs, 0);
                Gui.noTabActionOnIdx(Box.tabs,0);
                Gui.circleCollision(Box.tabs, Box.circleColliders, Box.guiLocations, guiTouch.point);
                Gui.reduseTabActions(Box.tabs, 1);
                Gui.noTabActionOnIdx(Box.tabs,1);
                Gui.excuteChangeTabAction(Box.guiLocations, Box.tabActions, Box.tabs, guiTouch);

                Box.tabs.clear();

                if(Box.touchs.size() == 1) {
                        // camera moving in x,y //
                        // if camera.location_ID == 0 (world root) then moving the hole world //
                        Box.Location location = Box.locations.atId(camera.location_ID);
                        float[] vector = new float[4];
                        float[] result = new float[4];
                        vector[0] = touch.delta.x/2 * (abs(Mathe.Tz(camera.T)) + 1f);
                        vector[1] = touch.delta.y/2 * (abs(Mathe.Tz(camera.T)) + 1f);
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

                        Matrix.translateM(location.TF,0,
                                result[0], result[1], result[2]
                        );
                } else if (Box.touchs.size() == 2) {
                        // scale camera distance -z, looks like scaling the hole world //
                        Box.Touch a;
                        Box.Touch b;
                        if (Box.touchs.getIndex(id) == 1) {
                                a = Box.touchs.at(0);
                                b = Box.touchs.at(1);
                        } else {
                                a = Box.touchs.at(1);
                                b = Box.touchs.at(0);
                        }
                        // a doesn't change //
                        Vec2 lastPoint  = Vec2.sub(b.point, b.delta);
                        Vec2 lastGap = Vec2.sub(lastPoint, a.point);
                        Vec2 gap = Vec2.sub(b.point, a.point);
                        float length = gap.length();
                        if( length > 0) {
                                final float scaleFactor = lastGap.length() / length;
                                Mathe.Tz(
                                        camera.T,
                                        -Mathe.adjustDistance(
                                                Mathe.Tz(camera.T),
                                                scaleFactor, 0.01f
                                        )
                                );
                        }

                }
        }

        public void onTouchDelete(final int id) {
                if(id >= Box.touchs.length()) {
                        return;
                }
                final Box.Camera camera = Box.cameras.atId(cameraId);
                final Box.Touch touch = Box.touchs.atId(id);
                final Vec2 p = new Vec2(touch.point.x,touch.point.y / camera.aspectRatio);
                Box.tabs.clear();
                Gui.circleCollision(Box.tabs, Box.circleColliders, Box.guiLocations, p);
                Gui.reduseTabActions(Box.tabs, 0);
                Gui.excuteReleaseTabAction(Box.guiLocations, Box.tabActions, Box.tabs);
                Box.tabs.clear();
                Box.touchs.delete(id);
        }

}
