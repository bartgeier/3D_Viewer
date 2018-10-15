package bertrand.myopengl.Tool;

import android.renderscript.Float3;

import static java.lang.Math.sqrt;


public class VectorMath {
        public static Float3 vector3Normalize(final Float3 v) {
                float length =  (float)Math.sqrt(v.x*v.x  + v.y*v.y + v.z*v.z);
                return new Float3(v.x/length, v.y/length, v.z/length);
        }
}
