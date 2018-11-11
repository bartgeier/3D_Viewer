package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.Vec2;


import static org.junit.Assert.*;

public class test_Vec2 {
        @Test
        public void normalize() {
                Vec2 vector = Vec2.normalize(3f , -4f);
                assertEquals(0.6f, vector.x, 0);
                assertEquals( -0.8f, vector.y ,0);
        }
}