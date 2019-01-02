package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class MainSurfaceView extends GLSurfaceView {//GLTextureView {//GLSurfaceView {
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

        public void start() {
                renderer = new MainRenderer(context);
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

        public void setOrientation(final float pitch, final float roll, final float azimut) {
                queueEvent(new Runnable() {
                        @Override
                        public void run() {
                                renderer.onOrientationChanged(pitch, roll, azimut);
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
}
