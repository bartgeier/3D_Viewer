package bertrand.myopengl.DeviceOrientation;

public class DeviceOrientationEvent {
        public final float azimut;
        public final float pitch;
        public final float roll;
        public final float[] rotationMatrix;

        DeviceOrientationEvent(
                final float[] rotationMatrix,
                final float azimut,
                final float pitch,
                final float roll
        ) {
                this.rotationMatrix = rotationMatrix;
                this.azimut = azimut;
                this.pitch = pitch;
                this.roll = roll;
        }
}
