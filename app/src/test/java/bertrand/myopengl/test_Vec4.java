package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.Vec4;

import static org.junit.Assert.assertEquals;

public class test_Vec4 {
        @Test
        public void normalize() {
                Vec4 vector = Vec4.normalize(12f, -5f , 7f, 4f);
                assertEquals(0.784465f, vector.w, 0.00001);
                assertEquals( -0.32686f, vector.x ,0.00001);
                assertEquals( 0.457604f, vector.y ,0.00001);
                assertEquals(0.261488f, vector.z, 0.00001);
        }

        @Test
        public void normalizeMember() {
                /* https://www.wolframalpha.com/input/?i=normalize+the+vector+(12,-5,7,4) */
                Vec4 v = new Vec4(12f, -5f , 7f, 4f);
                Vec4 vector = v.normalize();
                assertEquals(0.784465f, vector.w, 0.00001);
                assertEquals( -0.32686f, vector.x ,0.00001);
                assertEquals( 0.457604f, vector.y ,0.00001);
                assertEquals(0.261488f, vector.z, 0.00001);
        }
}