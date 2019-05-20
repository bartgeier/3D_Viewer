package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class MainSurfaceView extends GLSurfaceView {
        private MainRenderer renderer;
        private Context context;

        public MainSurfaceView(Context context) {
                super(context);
                this.context = context;
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

        public void setScaleFactor(final float scaleFactor) {
                queueEvent(new Runnable() {
                        @Override
                        public void run() {
                                renderer.onTouchScreenScaling(scaleFactor);
                        }
                });
        }

        public void setMove(final float dx, final float dy) {
                queueEvent(new Runnable() {
                        @Override
                        public void run() {
                                renderer.onTouchScreenMoving(dx,dy);
                        }
                });
        }

}
