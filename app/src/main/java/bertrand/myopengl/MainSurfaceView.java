package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;


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

        public void setTouchAdd(final int id, final float x_, final float y_) {
                final float y = y_ - getTop();
                final float x = x_ - getLeft();
                final int width = getWidth();
                final int height = getHeight();
                if (x <  width && y <  height) {
                        final float glx = 2f*x/width - 1;     //glx between -1 and +1
                        final float gly = -(2f*y/height - 1); //gly between -1 and +1
                        queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                        renderer.onTouchAdd(id, glx, gly);
                                }
                        });
                }
        }

        public void setTouchChanged(final int id, final float x_, final  float y_) {
                final float y = y_ - getTop();
                final float x = x_ - getLeft();
                final int width = getWidth();
                final int height = getHeight();
                if (x <  width && y <  height) {
                        final float glx = 2f*x/width - 1;     //glx between -1 and +1
                        final float gly = -(2f*y/height - 1); //gly between -1 and +1
                        queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                        renderer.onTouchChanged(id, glx, gly);
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
