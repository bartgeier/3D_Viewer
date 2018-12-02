package bertrand.myopengl.ExampleObjects;

import bertrand.myopengl.Models.ColoredModel;
import bertrand.myopengl.Shaders.ColoredShader;

public final class Triangle1 extends ColoredModel {

        public Triangle1(final ColoredShader shader) {
                super(
                        shader,
                        new int[] { //indices
                                0,1,2
                        },
                        new float[] { //positions
                                0.0f, -0.5f, 0f,
                                0.5f,  0.5f, 0f,
                                -0.5f,  0.5f, 0f,
                        },
                        new float[] { //colors
                                1.0f, 0.06f, 0.0f, 1.0f,
                                0.0f, 1.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f, 1.0f,
                        },
                        new float[] { //normals
                                0, 0, 1,
                                0, 0, 1,
                                0, 0, 1,
                        }
                );
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
                final double y = 5* Math.sin(rad);
                this.position.y = (float)y;
        }
}
