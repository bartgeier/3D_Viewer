package bertrand.myopengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import bertrand.myopengl.DeviceOrientation.DeviceOrientation;
import bertrand.myopengl.DeviceOrientation.DeviceOrientationEvent;
import bertrand.myopengl.DeviceOrientation.DeviceOrientationListner;
import bertrand.myopengl.ExampleScenes.ExampleNames;
import bertrand.myopengl.Tool.TextChooser.TextChooserActivity;

public class MainActivity extends AppCompatActivity implements DeviceOrientationListner {
        private static final String TAG = "MainActivity";
        private static final int EXAMPLE_ACTIVITY_ID = 1;
        MainSurfaceView mainGLView;
        DeviceOrientation deviceOrientation;





        public class UI{
                ConstraintLayout layout;
                ActionBar actionBar;
                TextView textView;
        }
        private final MainActivity.UI ui = new MainActivity.UI();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                ConfigurationInfo info = am.getDeviceConfigurationInfo();
                boolean supportES2 = (info.reqGlEsVersion >= 0x20000);
                if (supportES2) {
                        setContentView(R.layout.activity_main);
                        Toolbar t = (Toolbar)findViewById(R.id.toolbar);
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
                        mainGLView.start();

                        deviceOrientation = new DeviceOrientation(this,this);
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
                                ui.textView.setText("");
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
                                ui.textView.setText(b.getString("name"));
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
                String s =
                        "Azimut: " + event.azimut + "\n" +
                        "Pitch: " + event.pitch + "\n" +
                        "Roll: " + event.roll;
                ui.textView.setText(s);
                //mainGLView.setOrientation(event.pitch, event.roll, event.azimut);
                mainGLView.setOrientation(event.rotationMatrix);
        }

}
