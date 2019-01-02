package bertrand.myopengl.DeviceOrientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.jetbrains.annotations.NotNull;

public class DeviceOrientation implements SensorEventListener {
        private DeviceOrientationListner listener;

        public DeviceOrientation(
                @NotNull Context context,
                @NotNull DeviceOrientationListner listener)
        {
                this.listener = listener;
                SensorManager sensorManager;
                Sensor rotationVector;
                sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
                rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                sensorManager.registerListener(
                        this,
                        rotationVector,
                        SensorManager.SENSOR_DELAY_FASTEST
                );
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                        final float[] matrix = new float[16];
                        SensorManager.getRotationMatrixFromVector(matrix, event.values);
                        float[] o = new float[3];
                        SensorManager.getOrientation(matrix, o);
                        float azimut = (float) Math.toDegrees(o[0]);
                        float pitch = (float) Math.toDegrees(o[1]);
                        float roll = (float) Math.toDegrees(o[2]);
                        DeviceOrientationEvent e = new DeviceOrientationEvent(
                                matrix,
                                azimut,
                                pitch,
                                roll
                        );
                        listener.onSensorChanged(e);
                }
         }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
}
