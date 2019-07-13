package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.GLMathe;

import static org.junit.Assert.assertEquals;

public class test_GLMathe {
        @Test
        public void  pixel_to_glCoordinateSystem_0() {
                Vec2 size = new Vec2(300,1200);
                Vec2 a = new Vec2(300,1200);
                Vec2 gl = new Vec2();
                GLMathe.pixel_to_glCoordinateSystem(gl, a, size);
                assertEquals(1f, gl.x, 0.001);
                assertEquals(-1f, gl.y, 0.001);
                a.x = 0;
                a.y = 0;
                GLMathe.pixel_to_glCoordinateSystem(gl, a, size);
                assertEquals(-1f, gl.x, 0.001);
                assertEquals(1f, gl.y, 0.001);
                a.x = 150;
                a.y = 600;
                GLMathe.pixel_to_glCoordinateSystem(gl, a, size);
                assertEquals(0f, gl.x, 0.001);
                assertEquals(0f, gl.y, 0.001);
        }

        @Test
        public void  pixel_to_glCoordinateSystem_1() {
                Vec2 size = new Vec2(300,1200);
                Vec2 a = new Vec2(300,1200);
                GLMathe.pixel_to_glCoordinateSystem(a, size);
                assertEquals(1f, a.x, 0.001);
                assertEquals(-1f, a.y, 0.001);
                a.x = 0;
                a.y = 0;
                GLMathe.pixel_to_glCoordinateSystem(a, size);
                assertEquals(-1f, a.x, 0.001);
                assertEquals(1f, a.y, 0.001);
                a.x = 150;
                a.y = 600;
                GLMathe.pixel_to_glCoordinateSystem(a, size);
                assertEquals(0f, a.x, 0.001);
                assertEquals(0f, a.y, 0.001);
        }
}
