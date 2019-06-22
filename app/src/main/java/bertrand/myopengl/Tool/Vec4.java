package bertrand.myopengl.Tool;

public class Vec4 {
        public float w = 0;
        public float x = 0;
        public float y = 0;
        public float z = 0;
        public Vec4() {
                x = 0;
                y = 0;
                z = 0;
        }

        public Vec4(float iw, float ix, float iy, float iz) {
                w = iw;
                x = ix;
                y = iy;
                z = iz;
        }

        public static Vec4 normalize(float w, float x, float y, float z) {
                float length =  (float)Math.sqrt(w*w + x*x  + y*y + z*z);
                return new Vec4(w/length, x/length, y/length, z/length );
        }

        public Vec4 normalize() {
                float length =  (float)Math.sqrt(w*w + x*x  + y*y + z*z);
                return new Vec4(w/length, x/length, y/length, z/length );
        }
}
