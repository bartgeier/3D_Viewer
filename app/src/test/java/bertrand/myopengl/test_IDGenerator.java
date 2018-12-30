package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.IDGenerator;

import static org.junit.Assert.assertEquals;

public class test_IDGenerator {
        @Test
        public void get() {
                IDGenerator generator = new IDGenerator();
                assertEquals(generator.getID(),0);
                assertEquals(generator.getID(),1);
                assertEquals(generator.getID(),2);
                assertEquals(generator.getID(),3);
                assertEquals(generator.test_nextID(),4);

                generator.deleteID(4);
                assertEquals(generator.test_nextID(),4);

                assertEquals(generator.getID(),4);
                assertEquals(generator.test_nextID(),5);

                generator.deleteID(2);
                assertEquals(generator.test_nextID(),5);
                assertEquals(generator.getID(),2);
                assertEquals(generator.test_nextID(),5);

                generator.deleteID(1);
                generator.deleteID(3);
                assertEquals(generator.test_nextID(),5);
                assertEquals(generator.getID(),3);
                assertEquals(generator.test_nextID(),5);
                assertEquals(generator.getID(),1);
                assertEquals(generator.test_nextID(),5);
                assertEquals(generator.getID(),5);
                assertEquals(generator.test_nextID(),6);

                generator.deleteID(5);
                assertEquals(generator.test_nextID(),5);
        }
}
