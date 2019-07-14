package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;

import bertrand.myopengl.Tool.Vec2;


public class MainSurfaceView extends GLSurfaceView {
        private MainRenderer renderer;
        private Context context;

        public MainSurfaceView(Context context) {
                super(context);
                this.context = context;
                //WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                //Display display = wm.getDefaultDisplay();




        }

        public MainSurfaceView(Context context, AttributeSet attrs) {
                super(context, attrs);
                this.context = context;
        }



        public void start(FrameMessageHandler handler) {
                renderer = new MainRenderer(context, handler);
                setRenderer(renderer);
        }

        public void setExample(final int idx) {
                queueEvent(new Runnable() {
                        @Override
                        public void run() {
                                renderer.onNewExample(idx);
                        }
                });
        }

        public void clearScreen(){
                queueEvent(new Runnable() {
                        @Override
                        public void run() {
                                renderer.onClearScreen();
                        }
                });
        }

        public void setDeviceOrientation(final float[] rotationMatrix) {
                queueEvent(new Runnable() {
                        @Override
                        public void run() {
                                renderer.onDeviceOrientationChanged(rotationMatrix);
                        }
                });
        }

        // screen 720 x 1280,  surfaceView 720 x 1168 //
        public void setTouchAdd(final int id, final float x, final float y) {
                final Vec2 point = new Vec2(x - getLeft(),y - getTop());
                final Vec2 size = new Vec2(getWidth(), getHeight());
                if (point.x <= getRight() && point.y <= getBottom()) {
                        queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                        renderer.onTouchAdd(
                                                id,
                                                point,
                                                size
                                        );
                                }
                        });
                }
        }

        public void setTouchChanged(final int id, final float x, final  float y) {
                final Vec2 point = new Vec2(x - getLeft(),y - getTop());
                final Vec2 size = new Vec2(getWidth(), getHeight());
                if (point.x <= getRight() && point.y <= getBottom()) {
                        queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                        renderer.onTouchChanged(
                                                id,
                                                point,
                                                size
                                        );
                                }
                        });
                }
        }

        public void setTouchDelete(final int id) {
                queueEvent(new Runnable() {
                        @Override
                        public void run() {
                                renderer.onTouchDelete(id);
                        }
                });
        }

}
