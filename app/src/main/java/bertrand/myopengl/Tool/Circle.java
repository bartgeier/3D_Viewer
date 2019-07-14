package bertrand.myopengl.Tool;

public class Circle {
        public Vec2 center  = new Vec2();
        public float radius;
        public Circle(float x, float y, float radius) {
                center.x = x;
                center.y = y;
                this.radius = radius;
        }
}
