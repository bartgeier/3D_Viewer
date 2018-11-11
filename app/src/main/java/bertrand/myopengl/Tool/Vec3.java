package bertrand.myopengl.Tool;

public class Vec3 {
        public float x = 0;
        public float y = 0;
        public float z = 0;
        public Vec3() {
                x = 0;
                y = 0;
                z = 0;
        }

        public Vec3(float ix, float iy, float iz) {
                x = ix;
                y = iy;
                z = iz;
        }

        public static Vec3 normalize(float x, float y, float z) {
                float length =  (float)Math.sqrt(x*x  + y*y + z*z);
                return new Vec3(x/length, y/length, z/length );
        }
}
