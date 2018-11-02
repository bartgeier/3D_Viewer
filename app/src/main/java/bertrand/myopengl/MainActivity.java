package bertrand.myopengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MainSurfaceView mainGLView;
    MainRenderer mainRenderer;
    ConstraintLayout layout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        layout = this.findViewById(R.id.layout);
        textView = this.findViewById(R.id.label);

        //ViewCompat.setTranslationZ(textView, 1);




        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        boolean supportES2 = (info.reqGlEsVersion >= 0x20000);
        if (supportES2) {
            mainRenderer = new MainRenderer(this);
            mainGLView = (MainSurfaceView)this.findViewById(R.id.Id);
            //mainGLView = new MainSurfaceView(this);
            mainGLView.setEGLContextClientVersion(2);
            mainGLView.setRenderer(mainRenderer);
/*
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            ViewGroup.LayoutParams layoutParams=mainGLView.getLayoutParams();
            layoutParams.width = size.inputStream;
            layoutParams.height = size.y;
            mainGLView.setLayoutParams(layoutParams);
*/
            //this.setContentView(mainGLView);
        } else {
            Toast.makeText(
                    MainActivity.this,
                    "Time to get a new phone, OpenGL ES 2.0 not supported.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mainGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mainGLView.onPause();
    }




}
