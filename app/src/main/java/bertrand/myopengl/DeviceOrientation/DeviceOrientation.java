package bertrand.myopengl.DeviceOrientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import bertrand.myopengl.MainActivity;

public class DeviceOrientation implements SensorEventListener {
        private Context context;
        private DeviceOrientationListner listener;
        private SensorManager sensorManager;
        Sensor accelerometer;
        Sensor magnetometer;
        Sensor rotationVector;
        private float[] mLastAccelerometer = new float[3];
        private float[] mLastMagnetometer = new float[3];
        private boolean mLastAccelerometerSet = false;
        private boolean mLastMagnetometerSet = false;
        private float[] mR = new float[16];
        private float[] mI = new float[16];
        private float[] mOrientation = new float[3];

        public DeviceOrientation(Context context, DeviceOrientationListner listener) {
                this.context = context;
                this.listener = listener;
                sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
                //accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                //magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                //sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                //sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_FASTEST);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
                switch(event.sensor.getType()) {
                        case Sensor.TYPE_ROTATION_VECTOR: {
                                // Get rotation matrix
                                SensorManager.getRotationMatrixFromVector(mR, event.values);


                                SensorManager.getOrientation(mR, mOrientation);

                                float azimut = (float) Math.toDegrees(mOrientation[0]); // +-Pi
                                float pitch = (float) Math.toDegrees(mOrientation[1]);  // +-Pi/2
                                float roll = (float) Math.toDegrees(mOrientation[2]);   // +-Pi/2

                                DeviceOrientationEvent e =
                                        new DeviceOrientationEvent(mR, azimut, pitch, roll);
                                        //new DeviceOrientationEvent(azimut, pitch, roll);
                                listener.onSensorChanged(e);

                                } break;
                        case Sensor.TYPE_ACCELEROMETER: {
                                mLastAccelerometerSet = true;
                                System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
                                } break;
                        case Sensor.TYPE_MAGNETIC_FIELD:{
                                mLastMagnetometerSet = true;
                                System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);

                                } break;
                        default:
                                break;
                }
                if (event.sensor == accelerometer) {
                        System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
                        mLastAccelerometerSet = true;
                } else if (event.sensor == magnetometer) {
                        System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
                        mLastMagnetometerSet = true;
                }
                if (mLastAccelerometerSet && mLastMagnetometerSet) {
                        SensorManager.getRotationMatrix(mR, mI, mLastAccelerometer, mLastMagnetometer);
                        SensorManager.getOrientation(mR, mOrientation);

                        float azimut = (float) Math.toDegrees(mOrientation[0]); // +-Pi
                        float pitch = (float) Math.toDegrees(mOrientation[1]);  // +-Pi/2
                        float roll = (float) Math.toDegrees(mOrientation[2]);   // +-Pi/2

                        //DeviceOrientationEvent e = new DeviceOrientationEvent(azimut, pitch, roll);
                        //listener.onSensorChanged(e);
                }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
}
