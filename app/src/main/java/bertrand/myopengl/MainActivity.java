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

import bertrand.myopengl.ExampleScenes.ExampleNames;
import bertrand.myopengl.Tool.TextChooser.TextChooserActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
        private static final String TAG = "MainActivity";
        private static final int EXAMPLE_ACTIVITY_ID = 1;
        MainSurfaceView mainGLView;

        private SensorManager sensorManager;
        Sensor accelerometer;
        Sensor magnetometer;
        Sensor rotationVector;
        private float[] mLastAccelerometer = new float[3];
        private float[] mLastMagnetometer = new float[3];
        private boolean mLastAccelerometerSet = false;
        private boolean mLastMagnetometerSet = false;
        private float[] mR = new float[9];
        private float[] mOrientation = new float[3];



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

                        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                        rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                        sensorManager.registerListener(MainActivity.this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                        sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);

                        Log.d(TAG, "onCreate: Registered accelerometer listener");
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

        private float[] mMatrixR = new float[9];
        private float[] mMatrixValues = new float[3];
        @Override
        public void onSensorChanged(SensorEvent event) {
                switch(event.sensor.getType()) {
                case Sensor.TYPE_ROTATION_VECTOR:
                        // Get rotation matrix
                        SensorManager.getRotationMatrixFromVector(mR, event.values);

                        SensorManager.getOrientation(mR, mOrientation);

                        float azimut = (float) Math.toDegrees(mOrientation[0]); // +-Pi
                        float pitch = (float) Math.toDegrees(mOrientation[1]);  // +-Pi/2
                        float roll = (float) Math.toDegrees(mOrientation[2]);   // +-Pi/2

                        String s =
                                "Azimut: " + azimut + "   " + mOrientation[0] + "\n" +
                                "Pitch: " + pitch + "   " + mOrientation[1] + "\n" +
                                "Roll: " + roll + "   " + mOrientation[2];
                        ui.textView.setText(s);
                        mainGLView.setOrientation(pitch, roll, azimut);
                        break;
                case Sensor.TYPE_ACCELEROMETER:
                       // System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
                       // mLastAccelerometerSet = true;
                        break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                       // System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
                       // mLastMagnetometerSet = true;
                        break;
                default:
                        break;
                }
                /*
                if (event.sensor == accelerometer) {
                        System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
                        mLastAccelerometerSet = true;
                } else if (event.sensor == magnetometer) {
                        System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
                        mLastMagnetometerSet = true;
                }
                */
                /*
                if (mLastAccelerometerSet && mLastMagnetometerSet) {
                        SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
                        SensorManager.getOrientation(mR, mOrientation);

                        float azimut = (float) Math.toDegrees(mOrientation[0]); // +-Pi
                        float pitch = (float) Math.toDegrees(mOrientation[1]);  // +-Pi/2
                        float roll = (float) Math.toDegrees(mOrientation[2]);   // +-Pi/2

                        String s =
                                "Azimut: " + azimut + "   " + mOrientation[0] + "\n" +
                                "Pitch: " + pitch + "   " + mOrientation[1] + "\n" +
                                "Roll: " + roll + "   " + mOrientation[2];
                        ui.textView.setText(s);
                        mainGLView.setOrientation(pitch, roll, azimut);
                       // Log.d("OrientationTestActivity", String.format("Orientation: %f, %f, %f",
                       //         mOrientation[0], mOrientation[1], mOrientation[2]));
                }
                */
        }

/*
        @Override
        public void onSensorChanged(SensorEvent event) {

                ui.textView.setText(String.format("Orientation: %f, %f, %f",
                        event.values[0],  event.values[1],  event.values[2]));


                Log.d(
                        TAG,
                        "onSensorChanged: X: " +
                                event.values[0] +
                                " Y: " +
                                event.values[1] +
                                " Z: " +
                                event.values[2]
                );
        }
*/


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

}
