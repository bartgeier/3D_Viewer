package bertrand.myopengl;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import bertrand.myopengl.Tool.Scene.Hierarchy;
import bertrand.myopengl.Tool.Scene.HierarchyData;
import bertrand.myopengl.Tool.Scene.SceneParser;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class test_SceneParser {
        @Test
        public void names() {
                String s =
                "M;model names\n" +
                "m;cube_planet;deciduous;conifer;waterTower;factory_smoke;haus;lake;factory\n" +
                "\n" +
                "H;idx;model idx;model;parent idx;parent;X;Y;Z;rotation X;rotation Y;rotation Z;scaleFactor X;scaleFactor Y;scaleFactor Z\n" +
                "h;0;0;cube_planet;0;cube_planet;0.0;0.0;0.0;0.0;0.0;0.0;16.628190994262695;16.628190994262695;0.17471875250339508\n" +
                "h;1;1;deciduous;0;cube_planet;-14.82341194152832;15.559011459350586;12.197236061096191;0.0;0.0;0.0;0.5104865431785583;0.5104865431785583;0.5104865431785583\n" +
                "h;2;2;conifer;0;cube_planet;6.806492805480957;5.371898174285889;12.197236061096191;0.0;0.0;0.0;0.75;0.75;0.75\n" +
                "h;3;2;conifer;0;cube_planet;9.663742065429688;8.150565147399902;12.197236061096191;0.0;0.0;0.0;1.0;1.0;1.0\n" +
                "h;4;3;waterTower;0;cube_planet;11.31657600402832;-1.3446128368377686;12.205534934997559;0.0;0.0;0.0;-0.03612328693270683;-0.03612328693270683;0.21220965683460236\n" +
                "h;5;4;factory_smoke;0;cube_planet;-7.750556945800781;-7.175302505493164;19.00285530090332;0.0;-0.0;-0.4134733974933624;1.1755114793777466;0.5885948538780212;0.23291943967342377\n" +
                "h;6;5;haus;0;cube_planet;0.0;13.383928298950195;12.200411796569824;0.0;0.0;0.0;1.5486903190612793;1.5486903190612793;1.5486903190612793\n" +
                "h;7;6;lake;0;cube_planet;-11.743013381958008;11.305191993713379;12.207236289978027;0.0;0.0;0.0;0.38419556617736816;1.0;1.0\n" +
                "h;8;5;haus;0;cube_planet;0.0;8.7894287109375;12.200411796569824;0.0;0.0;0.0;1.5486903190612793;1.5486903190612793;1.5486903190612793\n" +
                "h;9;7;factory;0;cube_planet;0.0;-7.1825714111328125;12.201512336730957;0.0;0.0;0.0;3.084872007369995;1.4366670846939087;1.4366670846939087";
                InputStream is = new ByteArrayInputStream(s.getBytes());
                String[] names = SceneParser.names(is);
                int idx = 0;
                assertNotNull(names);
                assertEquals(8, names.length);
                assertEquals("cube_planet",names[idx++]);
                assertEquals("deciduous",names[idx++]);
                assertEquals("conifer", names[idx++]);
                assertEquals("waterTower", names[idx++]);
                assertEquals("factory_smoke", names[idx++]);
                assertEquals("haus", names[idx++]);
                assertEquals("lake", names[idx++]);
                assertEquals("factory", names[idx]);
        }

        @Test
        public void scene() {
                String s =
                "M;model names\n" +
                "m;cube_planet;deciduous;conifer;waterTower;factory_smoke;haus;lake;factory\n" +
                "\n" +
                "H;idx;model idx;model;parent idx;parent;X;Y;Z;rotation X;rotation Y;rotation Z;scaleFactor X;scaleFactor Y;scaleFactor Z\n" +
                "h;0;0;cube_planet;0;cube_planet;0.0;0.0;0.0;0.0;0.0;0.0;16.628190994262695;16.628190994262695;0.17471875250339508\n" +
                "h;1;1;deciduous;0;cube_planet;-14.82341194152832;15.559011459350586;12.197236061096191;0.0;0.0;0.0;0.5104865431785583;0.5104865431785583;0.5104865431785583\n" +
                "h;2;2;conifer;0;cube_planet;6.806492805480957;5.371898174285889;12.197236061096191;0.0;0.0;0.0;0.75;0.75;0.75\n" +
                "h;3;2;conifer;0;cube_planet;9.663742065429688;8.150565147399902;12.197236061096191;0.0;0.0;0.0;1.0;1.0;1.0\n" +
                "h;4;3;waterTower;0;cube_planet;11.31657600402832;-1.3446128368377686;12.205534934997559;0.0;0.0;0.0;-0.03612328693270683;-0.03612328693270683;0.21220965683460236\n" +
                "h;5;4;factory_smoke;0;cube_planet;-7.750556945800781;-7.175302505493164;19.00285530090332;0.0;-0.0;-0.4134733974933624;1.1755114793777466;0.5885948538780212;0.23291943967342377\n" +
                "h;6;5;haus;0;cube_planet;0.0;13.383928298950195;12.200411796569824;0.0;0.0;0.0;1.5486903190612793;1.5486903190612793;1.5486903190612793\n" +
                "h;7;6;lake;0;cube_planet;-11.743013381958008;11.305191993713379;12.207236289978027;0.0;0.0;0.0;0.38419556617736816;1.0;1.0\n" +
                "h;8;5;haus;0;cube_planet;0.0;8.7894287109375;12.200411796569824;0.0;0.0;0.0;1.5486903190612793;1.5486903190612793;1.5486903190612793\n" +
                "h;9;7;factory;0;cube_planet;0.0;-7.1825714111328125;12.201512336730957;0.0;0.0;0.0;3.084872007369995;1.4366670846939087;1.4366670846939087\n";
                InputStream is = new ByteArrayInputStream(s.getBytes());
                Hierarchy hierarchy = SceneParser.hierarchy(is);
                assertNotNull(hierarchy);
                assertEquals(10, hierarchy.datas.size());
                assertEquals(0, hierarchy.datas.get(0).idx);
                assertEquals(9, hierarchy.datas.get(9).idx);
                assertEquals(0.0, hierarchy.datas.get(9).rotX, 0.001);
                assertEquals(1.4366, hierarchy.datas.get(9).scaleZ, 0.0001);
        }

}