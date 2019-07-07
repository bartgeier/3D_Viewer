package bertrand.myopengl;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import bertrand.myopengl.Tool.Scene.Hierarchy;
import bertrand.myopengl.Tool.Scene.SceneParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class test_Hierarchy {
        @Test
        public void attach() {
                String sA =
                "H;idx;model idx;model;parent idx;parent;X;Y;Z;quat W;quat X;quat Y;quat Z;scaleFactor X;scaleFactor Y;scaleFactor Z\n" +
                "h;0;0;cube_planet;0;cube_planet;0.0;0.0;0.0;1.0;0.0;0.0;0.0;16.628190994262695;16.628190994262695;0.17471875250339508\n";
                InputStream isA = new ByteArrayInputStream(sA.getBytes());
                Hierarchy hierarchy_A = SceneParser.hierarchy(isA);
                String sB =
                "H;idx;model idx;model;parent idx;parent;X;Y;Z;quat W;quat X;quat Y;quat Z;scaleFactor X;scaleFactor Y;scaleFactor Z\n" +
                "h;0;1;deciduous;0;deciduous;-14.82341194152832;15.559011459350586;12.197236061096191;1.0;0.0;0.0;0.0;0.5104865431785583;0.5104865431785583;0.5104865431785583\n" +
                "h;1;4;factory_smoke;0;deciduous;-7.750556945800781;-7.175302505493164;19.00285530090332;1.0;0.0;-0.0;-0.4134733974933624;1.1755114793777466;0.5885948538780212;0.23291943967342377\n";
                InputStream isB = new ByteArrayInputStream(sB.getBytes());
                Hierarchy hierarchy_B = SceneParser.hierarchy(isB);

                hierarchy_A.attach(hierarchy_B);
                assertNotNull(hierarchy_A);
                assertEquals(3, hierarchy_A.datas.size());
                assertEquals(0, hierarchy_A.datas.get(0).idx);
                assertEquals(0, hierarchy_A.datas.get(0).parent_idx);
                assertEquals(1, hierarchy_A.datas.get(1).idx);
                assertEquals(0, hierarchy_A.datas.get(1).parent_idx);
                assertEquals(2, hierarchy_A.datas.get(2).idx);
                assertEquals(1, hierarchy_A.datas.get(2).parent_idx);
                assertEquals(1.0, hierarchy_A.datas.get(2).quatW, 0.001);
                assertEquals(0.2329, hierarchy_A.datas.get(2).scaleZ, 0.0001);
        }
        @Test
        public void createRoot() {
                String sA =
                "H;idx;model idx;model;parent idx;parent;X;Y;Z;quat W;quat X;quat Y;quat Z;scaleFactor X;scaleFactor Y;scaleFactor Z\n" +
                "h;0;0;cube_planet;0;cube_planet;0.0;0.0;0.0;1.0;0.0;0.0;0.0;16.628190994262695;16.628190994262695;0.17471875250339508\n" +
                "h;1;1;deciduous;0;cube_planet;-14.82341194152832;15.559011459350586;12.197236061096191;1.0;0.0;0.0;0.0;0.5104865431785583;0.5104865431785583;0.5104865431785583\n";
                InputStream isA = new ByteArrayInputStream(sA.getBytes());
                Hierarchy hierarchy_A = SceneParser.hierarchy(isA);
                assertEquals(2, hierarchy_A.datas.size());

                hierarchy_A.createRoot();
                assertNotNull(hierarchy_A);
                assertEquals(1, hierarchy_A.datas.size());
                assertEquals(0, hierarchy_A.datas.get(0).idx);
                assertEquals(0, hierarchy_A.datas.get(0).parent_idx);
                assertEquals(0, hierarchy_A.datas.get(0).model_idx);
                assertEquals(0.0, hierarchy_A.datas.get(0).x, 0.001);
                assertEquals(0.0, hierarchy_A.datas.get(0).y, 0.001);
                assertEquals(0.0, hierarchy_A.datas.get(0).z, 0.001);
                assertEquals(1.0, hierarchy_A.datas.get(0).quatW, 0.001);
                assertEquals(0.0, hierarchy_A.datas.get(0).quatX, 0.001);
                assertEquals(0.0, hierarchy_A.datas.get(0).quatY, 0.001);
                assertEquals(0.0, hierarchy_A.datas.get(0).quatZ, 0.001);
                assertEquals(1.0, hierarchy_A.datas.get(0).scaleX, 0.0001);
                assertEquals(1.0, hierarchy_A.datas.get(0).scaleY, 0.001);
                assertEquals(1.0, hierarchy_A.datas.get(0).scaleZ, 0.0001);
        }

}
