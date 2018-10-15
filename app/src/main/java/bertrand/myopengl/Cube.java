package bertrand.myopengl;

import bertrand.myopengl.OpenGL.ObjectModel;
import bertrand.myopengl.OpenGL.Shader;

public final class Cube extends ObjectModel {
        Cube(final Shader shader) {
                /*
                final float[] fragments = {
                        //front
                        1, -1, 1,     1, 0, 0, 1, //0
                        1,  1, 1,     1, 0, 0, 1, //1
                       -1,  1, 1,     0, 1, 0, 1, //2
                       -1, -1, 1,     0, 1, 0, 1, //3
                        //back
                       -1, -1, -1,     1, 0, 0, 1, //4
                       -1,  1, -1,     1, 0, 0, 1, //5
                        1,  1, -1,     0, 1, 0, 1, //6
                        1, -1, -1,     0, 1, 0, 1, //7
                };
                final byte[] indices  = {
                        //front
                        0,1,2,
                        2,3,0,
                        //back
                        4,5,6,
                        6,7,4,
                        //left
                        3,2,5,
                        5,4,3,
                        //right
                        7,6,1,
                        1,0,7,
                        //top
                        1,6,5,
                        5,2,1,
                        //bottom
                        3,4,7,
                        7,0,3
                };
                */

                final float[] vectors = {
                        // Front
                        1, -1,  1,    1, 0, 0, 1,     0, 0, 1, // 0
                        1,  1,  1,    0, 1, 0, 1,     0, 0, 1, // 1
                       -1,  1,  1,    0, 0, 1, 1,     0, 0, 1, // 2
                       -1,  -1, 1,    0, 0, 0, 1,     0, 0, 1, // 3

                       // Back
                       -1, -1, -1,    0, 0, 1, 1,     0, 0, -1, // 4
                       -1,  1, -1,    0, 1, 0, 1,     0, 0, -1, // 5
                        1,  1, -1,    1, 0, 0, 1,     0, 0, -1, // 6
                        1, -1, -1,    0, 0, 0, 1,     0, 0, -1, // 7

                       // Left
                       -1, -1,  1,    1, 0, 0, 1,    -1, 0, 0, // 8
                       -1,  1,  1,    0, 1, 0, 1,    -1, 0, 0, // 9
                       -1,  1, -1,    0, 0, 1, 1,    -1, 0, 0, // 10
                       -1, -1, -1,    0, 0, 0, 1,    -1, 0, 0, // 11

                        // Right
                        1, -1, -1,    1, 0, 0, 1,     1, 0, 0, // 12
                        1,  1, -1,    0, 1, 0, 1,     1, 0, 0, // 13
                        1,  1,  1,    0, 0, 1, 1,     1, 0, 0, // 14
                        1, -1,  1,    0, 0, 0, 1,     1, 0, 0, // 15

                        // Top
                        1,  1,  1,    1, 0, 0, 1,      0, 1, 0, // 16
                        1,  1, -1,    0, 1, 0, 1,      0, 1, 0, // 17
                       -1,  1, -1,    0, 0, 1, 1,      0, 1, 0, // 18
                       -1,  1,  1,    0, 0, 0, 1,      0, 1, 0, // 19

                        // Bottom
                        1, -1, -1,     1, 0, 0, 1,     0, -1, 0, // 20
                        1, -1,  1,     0, 1, 0, 1,     0, -1, 0, // 21
                       -1, -1,  1,     0, 0, 1, 1,     0, -1, 0, // 22
                       -1, -1, -1,     0, 0, 0, 1,     0, -1, 0, // 23
                };

                /*
                final float[] fragments = {
                        // Front
                        1, -1,  1,    1, 0, 0, 1,    // 0
                        1,  1,  1,    0, 1, 0, 1,    // 1
                       -1,  1,  1,    0, 0, 1, 1,    // 2
                       -1,  -1, 1,    0, 0, 0, 1,    // 3

                        // Back
                       -1, -1, -1,    0, 0, 1, 1,    /// 4
                       -1,  1, -1,    0, 1, 0, 1,    /// 5
                        1,  1, -1,    1, 0, 0, 1,    /// 6
                        1, -1, -1,    0, 0, 0, 1,    /// 7

                        // Left
                       -1, -1,  1,    1, 0, 0, 1,    // 8
                       -1,  1,  1,    0, 1, 0, 1,    // 9
                       -1,  1, -1,    0, 0, 1, 1,    // 10
                       -1, -1, -1,    0, 0, 0, 1,    // 11

                        // Right
                        1, -1, -1,    1, 0, 0, 1,    // 12
                        1,  1, -1,    0, 1, 0, 1,    // 13
                        1,  1,  1,    0, 0, 1, 1,    // 14
                        1, -1,  1,    0, 0, 0, 1,    // 15

                        // Top
                        1,  1,  1,    1, 0, 0, 1,    // 16
                        1,  1, -1,    0, 1, 0, 1,    // 17
                       -1,  1, -1,    0, 0, 1, 1,    // 18
                       -1,  1,  1,    0, 0, 0, 1,    // 19

                        // Bottom
                        1, -1, -1,     1, 0, 0, 1,   // 20
                        1, -1,  1,     0, 1, 0, 1,   // 21
                       -1, -1,  1,     0, 0, 1, 1,   // 22
                       -1, -1, -1,     0, 0, 0, 1,   // 23
                };
                */

                final byte[] indices  = {
                        // Front
                        0, 1, 2,
                        2, 3, 0,
                        // Back
                        4, 5, 6,
                        6, 7, 4,
                        // Left
                        8, 9, 10,
                        10, 11, 8,
                        // Right
                        12, 13, 14,
                        14, 15, 12,
                        // Top
                        16, 17, 18,
                        18, 19, 16,
                        // Bottom
                        20, 21, 22,
                        22, 23, 20
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
        double rotationAngle = 0;
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
