package bertrand.myopengl;

import bertrand.myopengl.OpenGL.ColoredModel;
import bertrand.myopengl.OpenGL.SimpleShader;

public final class Triangle extends ColoredModel  {
        Triangle(final SimpleShader shader) {
                final float[] vectors = {
                        0.0f, -0.5f, 0f,
                        0.5f,  0.5f, 0f,
                        -0.5f,  0.5f, 0f,
                };
                final float[] colors = {
                        1.0f, 0.06f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,

                };
                final float[] normals = {
                        0, 0, 1,
                        0, 0, 1,
                        0, 0, 1,

                };
                final int[] indices  = {
                        0,1,2
                };
                super.init(indices, vectors, colors, normals, shader);
                position.x = 0f;
                position.y = 0f;
                position.z = -5f;
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
