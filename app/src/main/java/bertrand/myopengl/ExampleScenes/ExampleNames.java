package bertrand.myopengl.ExampleScenes;

import java.util.ArrayList;

public class ExampleNames {
        public enum Index {
                CUBE,
                CUBE_1,
                CUBE_SWARM,
                TRIANGLE,
                TRIANGLE_1,
                STALL,
                ROCKET,
                LOW_POLY_ISLANDS,
                XYZ_Arrows,
                DRAG_BUTTON,
                TEST_4,
                TEST_5,
                TEST_6,
                TEST_7,
                TEST_8,
                TEST_9,
                CLEAR_SCREEN,
        }
        public ArrayList<String> names = new ArrayList<>();

        public ExampleNames() {
                intNames();
        }

        private void intNames() {
                names.add(Index.CUBE.ordinal() , "Cube");
                names.add(Index.CUBE_1.ordinal() , "Cube_1");
                names.add(Index.CUBE_SWARM.ordinal() , "Cube swarm");
                names.add(Index.TRIANGLE.ordinal(), "Triangle");
                names.add(Index.TRIANGLE_1.ordinal(), "Triangle_1");
                names.add(Index.STALL.ordinal(),"Stall");
                names.add(Index.ROCKET.ordinal(),"Rocket");
                names.add(Index.LOW_POLY_ISLANDS.ordinal(),"LowPoly Islands");
                names.add(Index.XYZ_Arrows.ordinal(),"World XYZ");
                names.add(Index.DRAG_BUTTON.ordinal(),"Drag Button");
                names.add(Index.TEST_4.ordinal(),"Test_4");
                names.add(Index.TEST_5.ordinal(),"Test_5");
                names.add(Index.TEST_6.ordinal(),"Test_6");
                names.add(Index.TEST_7.ordinal(),"Test_7");
                names.add(Index.TEST_8.ordinal(),"Test_8");
                names.add(Index.TEST_9.ordinal(),"Test_9");
        }

}
