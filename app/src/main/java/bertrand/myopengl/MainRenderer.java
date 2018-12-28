package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import bertrand.myopengl.Camera.Camera;
import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Entitys.Load;
import bertrand.myopengl.Entitys.Update;
import bertrand.myopengl.ExampleScenes.CubeGray;
import bertrand.myopengl.ExampleScenes.Empty;
import bertrand.myopengl.ExampleScenes.ExampleNames;
import bertrand.myopengl.ExampleScenes.Scene;
import bertrand.myopengl.ExampleScenes.Stall;
import bertrand.myopengl.ExampleScenes.Triangle;
import bertrand.myopengl.ExampleScenes.Triangle1;
import bertrand.myopengl.Light.Light;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.RFile.RFile_IF;


public class MainRenderer implements Renderer {
        private Context context;
        private Light light;
        private Color4f backGroundColor;

        ExampleNames.Index lastExampleIndex = ExampleNames.Index.EMPTY;
        ExampleNames.Index newExampleIndex = ExampleNames.Index.EMPTY;

        MainRenderer (Context c) {
                context = c;
                light = new Light(
                        0f,0.8f,-1f,
                        1,1,1
                );
        }

        @Override
        public void onDrawFrame(GL10 gl) {
                float dt = deltaTime();
                if(lastExampleIndex != newExampleIndex) {
                        Scene s = Empty.createScene();
                        switch(newExampleIndex) {
                        case CUBE:
                                s = CubeGray.createScene();
                                break;
                        case TRIANGLE:
                                s = Triangle.createScene();
                                break;
                        case TRIANGLE_1:
                                s = Triangle1.createScene();
                                break;
                        case STALL:
                                s = Stall.createScene(context);
                                break;
                        default:
                                break;
                        }
                        light = s.light;
                        backGroundColor = s.backGroundColor;
                        lastExampleIndex = newExampleIndex;
                }
                GPU.renderBackground(backGroundColor);
                Update.locations(Box.locations, Box.periods, dt);
                Update.bodys(Box.bodys, Camera.position(), Box.locations);
                Update.gpu(light, Box.bodys, Box.shadersUniforms);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
                float r = (float) width / height;
                Camera.aspectRatio(r);
                Update.gpu_shader_projectionMatrix(Box.shadersUniforms, Camera.projectionMatrix());
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                Camera.init();
                RFile_IF file = new RFile(context);
                Load.texturedShader(
                        Box.shadersUniforms,
                        file.string(":/raw/shader_textured_vert.txt"),
                        file.string(":/raw/shader_textured_frag.txt")
                );
                Load.coloredShader(
                        Box.shadersUniforms,
                        file.string(":/raw/shader_colored_vert.txt"),
                        file.string(":/raw/shader_colored_frag.txt")
                );
                newExampleIndex = ExampleNames.Index.STALL;
        }

        public void onClearScreen() {
                newExampleIndex = ExampleNames.Index.EMPTY;
        }

        public void onNewExample(final int idx) {
                newExampleIndex = ExampleNames.Index.values()[idx];
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
