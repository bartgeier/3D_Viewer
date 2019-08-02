package bertrand.myopengl.Tool;

import static java.lang.StrictMath.sqrt;

public class Vec2 {
        public float x = 0;
        public float y = 0;
        public Vec2() {
                x = 0;
                y = 0;
        }
        public Vec2(float x, float y) {
                this.x = x;
                this.y = y;
        }

        public void copy(final Vec2 v) {
                x = v.x;
                y = v.y;
        }

        public Vec2 copy() {
                Vec2 v = new Vec2(x,y);
                return v;
        }

        public void sub(Vec2 b) {
                x -= b.x;
                y -= b.y;
        }

        // c = a - b;
        public static void sub(Vec2 c, Vec2 a, Vec2 b) {
                c.x = a.x - b.x;
                c.y = a.y - b.y;
        }


        public static Vec2 sub(Vec2 a, Vec2 b) {
                Vec2 c = new Vec2();
                c.x = a.x - b.x;
                c.y = a.y - b.y;
                return c;
        }

        public void add(Vec2 b) {
                x += b.x;
                y += b.y;
        }

        // c = a - b;
        public static void add(Vec2 c, Vec2 a, Vec2 b) {
                c.x = a.x + b.x;
                c.y = a.y + b.y;
        }

        public void sMult(final float scalar) {
                x *= scalar;
                y *= scalar;
        }

        public float length() {
                return (float)sqrt(x * x + y * y);
        }

        public static float length(final Vec2 v) {
                Vec2 c = new Vec2();
                return (float)sqrt(v.x * v.x + v.y * v.y);
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
