package bertrand.myopengl;

import bertrand.myopengl.OpenGL.ObjectModel;

public final class Stall extends ObjectModel {
        double angle = 0;
        double rotationAngle = 0;
        @Override
        public void updateWithDelta(float dt_ms) {
                /*
                double newPeriod_ms = 2000;
                angle += dt_ms * 360/newPeriod_ms;
                angle %= 360;
                final double rad = Math.toRadians(angle + 90);
                final double x = Math.sin(rad);
                final double y = Math.sin(rad);
                this.position.x = (float)x * 2;
                this.position.y = (float)y * 2;
                */

                double newRotationPeriod_ms = 16000;

                rotationAngle += dt_ms * 360/newRotationPeriod_ms;
                rotation.y = (float)rotationAngle;

                rotation.x %= 360;
                rotation.y %= 360;
                rotation.z %= 360;
        }
}
