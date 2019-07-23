package bertrand.myopengl.Tool;

import static java.lang.StrictMath.abs;

public class Tst {

        public static boolean subset(Circle ci, Vec2 point) {
                // if point a subset from ci return true //
                return (Vec2.sub(point, ci.center).length() < ci.radius);
                //return (abs(Vec2.sub(point, ci.center).x) < ci.radius && abs(Vec2.sub(point, ci.center).y) < ci.radius);
        }
}
