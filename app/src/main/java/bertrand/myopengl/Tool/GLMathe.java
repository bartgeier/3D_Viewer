package bertrand.myopengl.Tool;

public class GLMathe {
        public static void pixel_to_glCoordinateSystem(final Vec2 gl, final Vec2 a, final Vec2 viewSize) {
                gl.x =   2f * a.x / viewSize.x - 1;     //glx between -1 and +1
                gl.y = -(2f * a.y / viewSize.y - 1);    //gly between -1 and +1
        }
        public static void pixel_to_glCoordinateSystem(final Vec2 a, final Vec2 viewSize) {
                a.x =   2f * a.x / viewSize.x - 1;
                a.y = -(2f * a.y / viewSize.y - 1);
        }

}
