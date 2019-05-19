package bertrand.myopengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import bertrand.myopengl.DeviceOrientation.DeviceOrientation;
import bertrand.myopengl.DeviceOrientation.DeviceOrientationEvent;
import bertrand.myopengl.DeviceOrientation.DeviceOrientationListner;
import bertrand.myopengl.ExampleScenes.ExampleNames;
import bertrand.myopengl.Tool.TextChooser.TextChooserActivity;
import bertrand.myopengl.TouchScreen.TouchScreen;

public class MainActivity extends AppCompatActivity implements DeviceOrientationListner {
        public class UI{
                ConstraintLayout layout;
                ActionBar actionBar;
                TextView textView;
        }
        private final MainActivity.UI ui = new MainActivity.UI();
        private static final int EXAMPLE_ACTIVITY_ID = 1;
        MainSurfaceView mainGLView;
        DeviceOrientation deviceOrientation;
        TouchScreen touchScreen;

        FrameMessageHandler frameMessageHandler = new FrameMessageHandler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                        switch (msg.what) {
                        case 0:
                                DecimalFormat df_0 = new DecimalFormat("0.000");
                                final float ms_0 = msg.arg1/1000000f;
                                String s_0 = "time [ms]: " + df_0.format(ms_0 );
                                ui.textView.setText(s_0);
                                break;
                        case 1://Frame rate
                                DecimalFormat df_1 = new DecimalFormat("0.000");
                                final float ms_1 = msg.arg1/1000000f;
                                String s_1 = "frame rate [1/s]: " + df_1.format(1000/ms_1);
                                ui.textView.setText(s_1);
                                break;
                        default:
                                break;
                        }
                        return true;
                }
        });

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                ConfigurationInfo info = am.getDeviceConfigurationInfo();
                boolean supportES2 = (info.reqGlEsVersion >= 0x20000);
                if (supportES2) {
                        setContentView(R.layout.activity_main);
                        Toolbar t = findViewById(R.id.toolbar);
                        setSupportActionBar(t);
                        ui.actionBar = getSupportActionBar();
                        //ui.actionBar.hide();
                        ui.layout = this.findViewById(R.id.layout);
                        ui.textView = this.findViewById(R.id.label);
                        String s = Integer.toHexString(info.reqGlEsVersion);
                        Toast.makeText(
                                MainActivity.this,
                                "OpenGL ES " + s,
                                Toast.LENGTH_SHORT
                        ).show();
                        mainGLView = this.findViewById(R.id.mainSurfaceView);
                        mainGLView.setEGLContextClientVersion(2);
                        mainGLView.start(frameMessageHandler);
                        deviceOrientation = new DeviceOrientation(this,this);
                        touchScreen = new TouchScreen(this);
                } else {
                        Toast.makeText(
                                MainActivity.this,
                                "Time to get a new phone, OpenGL ES 2.0 not supported.",
                                 Toast.LENGTH_SHORT
                        ).show();
                }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.main_menu, menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                case R.id.action_examples:
                        Intent intent = new Intent(MainActivity.this, TextChooserActivity.class);
                        intent.putExtra("ArrayList_names", new ExampleNames().names);
                        intent.putExtra("Titel", getResources().getString(R.string.titel_examples));
                        startActivityForResult(intent, EXAMPLE_ACTIVITY_ID);
                        return true;
                case R.id.action_clearScreen:
                        mainGLView.clearScreen();
                        return true;
                default:
                        return super.onOptionsItemSelected(item);
                }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
                if (requestCode == EXAMPLE_ACTIVITY_ID) {
                        if (resultCode == RESULT_OK) {
                                Bundle b = intent.getBundleExtra("activity_textchooser");
                                mainGLView.setExample(b.getInt("position"));
                        }
                }
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
                outState.putCharSequence("textView", ui.textView.getText());
        }

        @Override
        protected void onRestoreInstanceState(Bundle savedState) {
                super.onRestoreInstanceState(savedState);
                ui.textView.setText(savedState.getCharSequence("textView"));

        }

        @Override
        public void onSensorChanged(DeviceOrientationEvent event) {
                mainGLView.setDeviceOrientation(event.rotationMatrix);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
                touchScreen.onTouchEvent(event);
                if (touchScreen.isScaling) {
                        mainGLView.setScaleFactor(touchScreen.scaleFactor);
                } else if (touchScreen.isMoving) {
                        mainGLView.setMove(touchScreen.deltaX, -touchScreen.deltaY);
                }
                return true;
        }

}
