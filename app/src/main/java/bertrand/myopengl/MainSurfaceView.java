package bertrand.myopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import bertrand.myopengl.Models.ModelOptions;


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

        public void setExample(final int position) {
                queueEvent(new Runnable() {
                        @Override
                        public void run() {
                                renderer.setExample(position);
                        }
                });

        }

/*
        public  void queueEvent(new Runnable(){

                @Override
                public void run() {
                       // mRenderer.method();
                }
        });


            @Override
        public boolean onTouch(View v, MotionEvent event) {

                ...
                // Calculate normalized value and everything
                ...

                glSurfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        mainRenderer.handleOnTouch(action, normalizedX, normalizedY);
                    }
                });
            }
*/

}
