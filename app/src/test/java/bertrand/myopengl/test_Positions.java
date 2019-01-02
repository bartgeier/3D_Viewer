package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.Positions;
import bertrand.myopengl.Tool.Vec3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class test_Positions {
        @Test
        public void boundingBox() {
                float[] x = new float[]{ //positions
                        0.0f, -0.5f,  -5f,
                        0.5f,    2f,  -4f,
                          3f,  0.5f,  -1f,
                };
                float[] b = Positions.boundingBox(x);
                assertNotNull(b);
                //minimums
                assertEquals(0f,    b[0], 0.00001); //minX
                assertEquals(-0.5f, b[1], 0.00001); //minY
                assertEquals(-5f,   b[2], 0.00001); //minZ
                //maximums
                assertEquals(3f,  b[3], 0.00001); //maxX
                assertEquals(2f,  b[4], 0.00001); //maxY
                assertEquals(-1f, b[5], 0.00001); //maxZ
        }

        @Test
        public void offset(){
                float[] x = new float[]{ //positions
                        0.0f, -0.5f,  -5f,
                        0.5f,    2f,  -4f,
                          3f,  0.5f,  -1f,
                };
                Vec3 o = Positions.offset(x);
                assertEquals(    (0f + 3f)/2f,    o.x, 0.00001);
                assertEquals( (-0.5f + 2f)/2f,    o.y, 0.00001);
                assertEquals((-5f + (-1f))/2f,    o.z, 0.00001);
        }

        @Test
        public void transformation() {
                float[] positions = new float[]{ //positions
                        0.0f, -0.5f,  -5f,
                        0.5f,    2f,  -4f,
                        3f,  0.5f,  -1f,
                };
                final Vec3 o = Positions.offset(positions);
                final float[] newPositions = Positions.transformation(positions, -o.x, -o.y, -o.z);
                final Vec3 newOffset = Positions.offset(newPositions);
                assertEquals(0f,    newOffset.x, 0.00001);
                assertEquals(0f,    newOffset.y, 0.00001);
                assertEquals(0f,    newOffset.z, 0.00001);
        }
}
