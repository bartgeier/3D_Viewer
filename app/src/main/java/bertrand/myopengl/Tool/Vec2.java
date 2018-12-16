package bertrand.myopengl.Tool;

public class Vec2 {
        public float x = 0;
        public float y = 0;
        public Vec2() {
                x = 0;
                y = 0;
        }
        public Vec2(float ix, float iy) {
                x = ix;
                y = iy;
        }
        public static Vec2 normalize(float x, float y) {
                float length =  (float)Math.sqrt(x*x  + y*y);
                return new Vec2(x/length, y/length);
        }

        public Vec2 normalize() {
                float length =  (float)Math.sqrt(x*x  + y*y);
                return new Vec2(x/length, y/length);
        }
}
