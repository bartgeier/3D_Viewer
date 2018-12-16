package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.Vec3;

import static org.junit.Assert.*;

public class test_Vec3 {
        @Test
        public void normalize() {
                Vec3 vector = Vec3.normalize(3f , -4f, - 1f);
                assertEquals(0.58834, vector.x, 0.00001);
                assertEquals( -0.78446f, vector.y ,0.00001);
                assertEquals(-0.19611, vector.z, 0.00001);
        }

        @Test
        public void normalizeMember() {
                Vec3 v = new Vec3(3f , -4f, - 1f);
                Vec3 vector = v.normalize();
                assertEquals(0.58834, vector.x, 0.00001);
                assertEquals( -0.78446f, vector.y ,0.00001);
                assertEquals(-0.19611, vector.z, 0.00001);
        }
}