package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.Vec2;

import static org.junit.Assert.*;

public class test_Vec2 {
        @Test
        public void copy_0() {
                Vec2 a = new Vec2(11,23);
                Vec2 b = new Vec2(0,0);
                b.copy(a);
                a.x = 99;
                a.y = 333;
                assertEquals(11, b.x, 0.0001);
                assertEquals(23, b.y, 0.0001);
        }

        @Test
        public void copy_1() {
                Vec2 a = new Vec2(11,23);
                Vec2 b = a.copy();
                a.copy(b);
                a.x = 99;
                a.y = 333;
                assertEquals(11, b.x, 0.0001);
                assertEquals(23, b.y, 0.0001);
        }

        @Test
        public void sub_0() {
                Vec2 a = new Vec2(11,23);
                Vec2 b = new Vec2(3,6);
                a.sub(b);
                assertEquals(8, a.x, 0.0001);
                assertEquals(17, a.y, 0.0001);
        }

        @Test
        public void sub_1() {
                Vec2 a = new Vec2(11,23);
                Vec2 b = new Vec2(3,6);
                Vec2 c = new Vec2(0,0);
                Vec2.sub(c, a, b);
                assertEquals(8, c.x, 0.0001);
                assertEquals(17, c.y, 0.0001);
        }

        @Test
        public void sub_2() {
                Vec2 a = new Vec2(11,23);
                Vec2 b = new Vec2(3,6);
                Vec2 c = Vec2.sub(a, b);
                assertEquals(8, c.x, 0.0001);
                assertEquals(17, c.y, 0.0001);
        }

        @Test
        public void add_0() {
                Vec2 a = new Vec2(11,23);
                Vec2 b = new Vec2(3,6);
                a.add(b);
                assertEquals(14, a.x, 0.0001);
                assertEquals(29, a.y, 0.0001);
        }

        @Test
        public void sMult_0() {
                Vec2 a = new Vec2(11,23);
                a.sMult(5);
                assertEquals(55, a.x, 0.0001);
                assertEquals(115, a.y, 0.0001);
        }

        @Test
        public void length_0() {
                Vec2 a = new Vec2(2,2);
                float leng = a.length();
                assertEquals(2.82842712, leng, 0.0001);
        }

        @Test
        public void length_1() {
                Vec2 a = new Vec2(2,2);
                float l = Vec2.length(a);
                a.x = 100;
                assertEquals(2.82842712, l, 0.0001);
        }

        @Test
        public void normalize() {
                Vec2 vector = Vec2.normalize(3f , -4f);
                assertEquals(0.6f, vector.x, 0);
                assertEquals( -0.8f, vector.y ,0);
        }
        @Test
        public void normalizeMember() {
                Vec2 v = new Vec2(3f , -4f);
                Vec2 vector = v.normalize();
                assertEquals(0.6f, vector.x, 0);
                assertEquals( -0.8f, vector.y ,0);
        }


}