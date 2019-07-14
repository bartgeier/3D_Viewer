package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.Circle;
import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.Tst;

import static org.junit.Assert.assertEquals;

public class test_Tst {
        @Test
        public void test_subset_ci_point() {
                Circle ci = new Circle(0,0,10);
                Vec2 point = new Vec2(25,30);

                boolean point_inside_ci = Tst.subset(ci,point);
                assertEquals(false,point_inside_ci);

                point.x = 9f;
                point.y = 11f;
                point_inside_ci = Tst.subset(ci,point);
                assertEquals(false,point_inside_ci);

                point.x = 10.5f;
                point.y = 6f;
                point_inside_ci = Tst.subset(ci,point);
                assertEquals(false,point_inside_ci);

                point.x = 9.5f;
                point.y = 6f;
                point_inside_ci = Tst.subset(ci,point);
                assertEquals(false,point_inside_ci);

                point.x = -2f;
                point.y = -2f;
                point_inside_ci = Tst.subset(ci,point);
                assertEquals(true,point_inside_ci);

                point.x = 1f;
                point.y = 1f;
                point_inside_ci = Tst.subset(ci,point);
                assertEquals(true,point_inside_ci);
        }
}
