package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.SparseArray.SparseArray;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class test_SparseArray {
        @Test
        public void createSparseArray() {
                @SuppressWarnings("unchecked")
                SparseArray<Float> x = new SparseArray(0,5);
                assertEquals(5, x.length());
                assertEquals(0, x.size());

                assertEquals(0x80000000, x.getNextFreeId());

                assertTrue(x.isIdFree(0));
                assertEquals(1 | 0x80000000, x.getIndex(0));

                assertTrue(x.isIdFree(1));
                assertEquals(2 | 0x80000000, x.getIndex(1));

                assertTrue( x.isIdFree(2));
                assertEquals(3 | 0x80000000, x.getIndex(2));

                assertTrue(x.isIdFree(3));
                assertEquals(4 | 0x80000000, x.getIndex(3));

                assertTrue(x.isIdFree(4));
                assertEquals(4 | 0x80000000, x.getIndex(4));
        }

        @Test
        public void work() {
                @SuppressWarnings("unchecked")
                SparseArray<Float> x = new SparseArray(0,5);
                assertEquals(0, x.size());
                assertEquals(0x80000000, x.getNextFreeId());

                int id_0 = x.add(1.1f);
                assertEquals(0, id_0);
                assertFalse(x.isIdFree(id_0));
                assertEquals(0, x.getIndex(id_0));
                assertEquals(1, x.size());
                assertEquals(1 | 0x80000000 , x.getNextFreeId());

                int id_1 = x.add(2.2f);
                assertEquals(1, id_1);
                assertFalse(x.isIdFree(id_1));
                assertEquals(1, x.getIndex(id_1));
                assertEquals(2, x.size());
                assertEquals(2 | 0x80000000, x.getNextFreeId());

                int id_2 = x.add(3.3f);
                assertEquals(2, id_2);
                assertFalse(x.isIdFree(id_2));
                assertEquals(2, x.getIndex(id_2));
                assertEquals(3, x.size());
                assertEquals(3 | 0x80000000, x.getNextFreeId());

                int id_3 = x.add(4.4f);
                assertEquals(3, id_3);
                assertFalse(x.isIdFree(id_3));
                assertEquals(3, x.getIndex(id_3));
                assertEquals(4, x.size());
                assertEquals(4 | 0x80000000, x.getNextFreeId());

                int id_4 = x.add(5.5f);
                assertEquals(4, id_4);
                assertFalse(x.isIdFree(id_4));
                assertEquals(4, x.getIndex(id_4));
                assertEquals(5, x.size());
                assertEquals(0, x.getNextFreeId());

                assertFalse(x.hasId(-2));
                assertTrue(x.hasId(4));
                assertFalse(x.hasId(5));

                assertEquals(1.1f, x.at(0), 0.0001);
                assertEquals(2.2f, x.at(1),0.0001);
                assertEquals(3.3f, x.at(2), 0.0001);
                assertEquals(4.4f, x.at(3), 0.0001);
                assertEquals(5.5f, x.at(4), 0.0001);


                assertFalse(x.isIdFree(id_0));
                assertEquals(1.1f, x.atId(id_0), 0.0001);

                assertEquals(0, x.getId(0));
                assertEquals(1, x.getId(1));
                assertEquals(2, x.getId(2));
                assertEquals(3, x.getId(3));
                assertEquals(4, x.getId(4));

                /* delete */
                /* (nextFreeId >= 0) */
                /* no free ids in indices */
                assertEquals(0, x.getNextFreeId());
                assertFalse(x.isIdFree(1));
                x.delete(1);
                assertTrue(x.isIdFree(1));
                assertEquals(4, x.size());
                assertEquals(1 | 0x80000000, x.getNextFreeId());
                assertEquals(1 | 0x80000000, x.getIndex(1));
                assertEquals(0, x.getId(0));
                assertEquals(4, x.getId(1));
                assertEquals(2, x.getId(2));
                assertEquals(3, x.getId(3));
                assertEquals(-1, x.getId(4));
                assertEquals(1.1f, x.atId(0), 0.0001);
                assertEquals(3.3f, x.atId(2), 0.0001);
                assertEquals(4.4f, x.atId(3), 0.0001);
                assertEquals(5.5f, x.atId(4), 0.0001);


                /* (id < (nextFreeId & 0x7FFFFFFF)) */
                /* id before the first indices */
                assertEquals(1 | 0x80000000, x.getNextFreeId());
                assertFalse(x.isIdFree(0));
                x.delete(0);
                assertTrue(x.isIdFree(0));
                assertEquals(3, x.size());
                assertEquals(0x80000000, x.getNextFreeId());
                assertEquals(1 | 0x80000000, x.getIndex(0));
                assertEquals(1 | 0x80000000, x.getIndex(1));
                assertEquals(3, x.getId(0));
                assertEquals(4, x.getId(1));
                assertEquals(2, x.getId(2));
                assertEquals(-1, x.getId(3));
                assertEquals(-1, x.getId(4));
                assertEquals(3.3f, x.atId(2), 0.0001);
                assertEquals(4.4f, x.atId(3), 0.0001);
                assertEquals(5.5f, x.atId(4), 0.0001);

                /* in while loop (freeId_1 == freeId_0) */
                /* id behind the last free indices */
                assertFalse(x.isIdFree(4));
                x.delete(4);
                assertTrue(x.isIdFree(4));
                assertEquals(2, x.size());
                assertEquals(0x80000000, x.getNextFreeId());
                assertEquals(1 | 0x80000000, x.getIndex(0));
                assertEquals(4 | 0x80000000, x.getIndex(1));
                assertEquals(4 | 0x80000000, x.getIndex(4));
                assertEquals(3, x.getId(0));
                assertEquals(2, x.getId(1));
                assertEquals(-1, x.getId(2));
                assertEquals(-1, x.getId(3));
                assertEquals(-1, x.getId(4));
                assertEquals(3.3f, x.atId(2), 0.0001);
                assertEquals(4.4f, x.atId(3), 0.0001);

                /* in while loop (id < freeId_1 && id > freeId_0) */
                /* id between two free indices */
                assertFalse(x.isIdFree(3));
                x.delete(3);
                assertTrue(x.isIdFree(3));
                assertEquals(1, x.size());
                assertEquals(0x80000000, x.getNextFreeId());
                assertEquals(1 | 0x80000000, x.getIndex(0));
                assertEquals(3 | 0x80000000, x.getIndex(1));
                assertEquals(4 | 0x80000000, x.getIndex(3));
                assertEquals(4 | 0x80000000, x.getIndex(4));
                assertEquals(2, x.getId(0));
                assertEquals(-1, x.getId(1));
                assertEquals(-1, x.getId(2));
                assertEquals(-1, x.getId(3));
                assertEquals(-1, x.getId(4));
                assertEquals(3.3f, x.atId(2), 0.0001);

                /* in while loop (id < freeId_1 && id > freeId_0) */
                /* id between two free indices */
                assertFalse(x.isIdFree(2));
                x.delete(2);
                assertTrue(x.isIdFree(3));
                assertEquals(0, x.size());
                assertEquals(0x80000000, x.getNextFreeId());
                assertEquals(1 | 0x80000000, x.getIndex(0));
                assertEquals(2 | 0x80000000, x.getIndex(1));
                assertEquals(3 | 0x80000000, x.getIndex(2));
                assertEquals(4 | 0x80000000, x.getIndex(3));
                assertEquals(4 | 0x80000000, x.getIndex(4));
                assertEquals(-1, x.getId(0));
                assertEquals(-1, x.getId(1));
                assertEquals(-1, x.getId(2));
                assertEquals(-1, x.getId(3));
                assertEquals(-1, x.getId(4));
        }

        @Test
        public void clear() {
                @SuppressWarnings("unchecked")
                SparseArray<Float> x = new SparseArray(0,5);
                assertEquals(0, x.size());
                x.add(1.1f);
                x.add(2.2f);
                x.add(3.3f);
                x.add(4.4f);
                x.add(5.5f);
                assertEquals(5, x.size());
                assertEquals(1.1f, x.at(0), 0.0001);
                assertEquals(2.2f, x.at(1),0.0001);
                assertEquals(3.3f, x.at(2), 0.0001);
                assertEquals(4.4f, x.at(3), 0.0001);
                assertEquals(5.5f, x.at(4), 0.0001);

                x.clear();
                assertEquals(0, x.size());
                assertTrue(x.isIdFree(0));
                assertEquals(1 | 0x80000000, x.getIndex(0));

                assertTrue(x.isIdFree(1));
                assertEquals(2 | 0x80000000, x.getIndex(1));

                assertTrue( x.isIdFree(2));
                assertEquals(3 | 0x80000000, x.getIndex(2));

                assertTrue(x.isIdFree(3));
                assertEquals(4 | 0x80000000, x.getIndex(3));

                assertTrue(x.isIdFree(4));
                assertEquals(4 | 0x80000000, x.getIndex(4));

        }
}
