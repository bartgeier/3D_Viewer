package bertrand.myopengl.ExampleScenes;

import java.util.ArrayList;

public class ExampleNames {
        public enum Index {
                CUBE,
                TRIANGLE,
                TRIANGLE_1,
                STALL,
                TEST_1,
                TEST_2,
                TEST_3,
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
                names.add(Index.TRIANGLE.ordinal(), "Triangle");
                names.add(Index.TRIANGLE_1.ordinal(), "Triangle_1");
                names.add(Index.STALL.ordinal(),"Stall");
                names.add(Index.TEST_1.ordinal(),"Test_1");
                names.add(Index.TEST_2.ordinal(),"Test_2");
                names.add(Index.TEST_3.ordinal(),"Test_3");
                names.add(Index.TEST_4.ordinal(),"Test_4");
                names.add(Index.TEST_5.ordinal(),"Test_5");
                names.add(Index.TEST_6.ordinal(),"Test_6");
                names.add(Index.TEST_7.ordinal(),"Test_7");
                names.add(Index.TEST_8.ordinal(),"Test_8");
                names.add(Index.TEST_9.ordinal(),"Test_9");
        }

}
