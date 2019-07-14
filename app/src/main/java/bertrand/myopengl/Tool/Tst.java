package bertrand.myopengl.Tool;

public class Tst {

        public static boolean subset(Circle ci, Vec2 point) {
                // if point a subset from ci return true //
                return (Vec2.sub(point, ci.center).length() <= ci.radius);
        }
}
