package bertrand.myopengl;

import bertrand.myopengl.OpenGL.ObjectModel;
import bertrand.myopengl.OpenGL.SimpleShader;

public final class Triangle extends ObjectModel {
        Triangle(final SimpleShader shader) {
                final float[] vectors = {
                        //vertecis            color                     Normal
                        0.0f, -0.5f, 0f,     1.0f, 0.06f, 0.0f, 1.0f,  0, 0, 1,
                        0.5f,  0.5f, 0f,      0.0f, 1.0f, 0.0f, 1.0f,  0, 0, 1,
                        -0.5f,  0.5f, 0f,     0.0f, 0.0f, 1.0f, 1.0f,  0, 0, 1,
                };
                final byte[] indices  = {
                        0,1,2
                };
                super.init(vectors, indices, shader);
                position.x = 0f;
                position.y = 0f;
                position.z = 0f;
                scale.x = 1f;
                scale.y = 1f;
                scale.z = 1f;
        }

        double angle = 0;
        @Override
        public void updateWithDelta(float dt_ms) {
                double newPeriod_ms = 8000;
                angle += dt_ms * 360/newPeriod_ms;
                angle %= 360;
                final double rad = Math.toRadians(angle);
                final double x = 4 * Math.sin(rad);
                this.position.x = (float)x;

        }
}
