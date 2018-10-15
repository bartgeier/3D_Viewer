package bertrand.myopengl;

import android.os.SystemClock;

import bertrand.myopengl.OpenGL.ObjectModel;
import bertrand.myopengl.OpenGL.Shader;

public final class Triangle extends ObjectModel {
        Triangle(final Shader shader) {
                final float[] vectors = {
                        0.0f, -0.5f, 0f,     1.0f, 0.06f, 0.0f, 1.0f,
                        0.5f,  0.5f, 0f,      0.0f, 1.0f, 0.0f, 1.0f,
                        -0.5f,  0.5f, 0f,     0.0f, 0.0f, 1.0f, 1.0f
                };
                final byte[] indices  = {
                        0,1,2
                };
                super.init(vectors, indices, shader);
                position.x = 0f;
                position.y = -0.16665f;
                position.z = -5f;
        }

        double angle = 0;
        public void updateWithDelta(float dt_ms) {
                double newPeriod_ms = 2000;
                angle += dt_ms * 360/newPeriod_ms;
                angle %= 360;
                final double rad = Math.toRadians(angle);
                final double x = 4 * Math.sin(rad);
                this.position.x = (float)x;
        }
}
