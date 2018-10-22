package bertrand.myopengl.Tool;


public class VectorMath {
        public static Vec3 vector3Normalize(final Vec3 v) {
                float length =  (float)Math.sqrt(v.x*v.x  + v.y*v.y + v.z*v.z);
                return new Vec3(v.x/length, v.y/length, v.z/length);
        }
}
