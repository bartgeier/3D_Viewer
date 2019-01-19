package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Tool.SparseArray.SparseArray;

import static org.junit.Assert.assertEquals;

public class test_SparseArray {
        @Test
        public void createSparseArray() {
                SparseArray x = new SparseArray(5);
                assertEquals(5, x.datas.length);
                assertEquals(0, x.writeIdx);

                assertEquals(0, x.getNextFreeId());

                assertEquals(true, x.isIdFree(0));
                assertEquals(1, x.getIndex(0));

                assertEquals(true, x.isIdFree(1));
                assertEquals(2, x.getIndex(1));

                assertEquals(true, x.isIdFree(2));
                assertEquals(3, x.getIndex(2));

                assertEquals(true, x.isIdFree(3));
                assertEquals(4, x.getIndex(3));

                assertEquals(true, x.isIdFree(4));
                assertEquals(-1, x.getIndex(4));
        }

        @Test
        public void work() {
                SparseArray x = new SparseArray(5);
                assertEquals(0, x.writeIdx);
                assertEquals(0, x.getNextFreeId());

                int id_0 = x.add(1.1f);
                assertEquals(0, id_0);
                assertEquals(false, x.isIdFree(id_0));
                assertEquals(0, x.getIndex(id_0));
                assertEquals(1, x.writeIdx);
                assertEquals(1, x.getNextFreeId());

                int id_1 = x.add(2.2f);
                assertEquals(1, id_1);
                assertEquals(false, x.isIdFree(id_1));
                assertEquals(1, x.getIndex(id_1));
                assertEquals(2, x.writeIdx);
                assertEquals(2, x.getNextFreeId());

                int id_2 = x.add(3.3f);
                assertEquals(2, id_2);
                assertEquals(false, x.isIdFree(id_2));
                assertEquals(2, x.getIndex(id_2));
                assertEquals(3, x.writeIdx);
                assertEquals(3, x.getNextFreeId());

                int id_3 = x.add(4.4f);
                assertEquals(3, id_3);
                assertEquals(false, x.isIdFree(id_3));
                assertEquals(3, x.getIndex(id_3));
                assertEquals(4, x.writeIdx);
                assertEquals(4, x.getNextFreeId());

                int id_4 = x.add(5.5f);
                assertEquals(4, id_4);
                assertEquals(false, x.isIdFree(id_4));
                assertEquals(4, x.getIndex(id_4));
                assertEquals(5, x.writeIdx);
                assertEquals(-1, x.getNextFreeId());

                assertEquals(false, x.hasId(-2));
                assertEquals(true, x.hasId(4));
                assertEquals(false, x.hasId(5));

                assertEquals(1.1f, x.datas[0], 0.0001);
                assertEquals(2.2f, x.datas[1],0.0001);
                assertEquals(3.3f, x.datas[2], 0.0001);
                assertEquals(4.4f, x.datas[3], 0.0001);
                assertEquals(5.5f, x.datas[4], 0.0001);

                assertEquals(false, x.isIdFree(id_0));
                assertEquals(1.1f, x.at(id_0), 0.0001);

                assertEquals(0, x.getId(0));
                assertEquals(1, x.getId(1));
                assertEquals(2, x.getId(2));
                assertEquals(3, x.getId(3));
                assertEquals(4, x.getId(4));

                /* delete */
                /* (nextFreeId == -1) */
                assertEquals(-1, x.getNextFreeId());
                assertEquals(false, x.isIdFree(1));
                x.delete(1);
                assertEquals(true, x.isIdFree(1));
                assertEquals(1, x.getNextFreeId());
                assertEquals(-1, x.getIndex(1));
                assertEquals(0, x.getId(0));
                assertEquals(4, x.getId(1));
                assertEquals(2, x.getId(2));
                assertEquals(3, x.getId(3));
                assertEquals(-1, x.getId(4));
                assertEquals(1.1f, x.at(0), 0.0001);
                assertEquals(3.3f, x.at(2), 0.0001);
                assertEquals(4.4f, x.at(3), 0.0001);
                assertEquals(5.5f, x.at(4), 0.0001);


                /* (id < nextFreeId) */
                assertEquals(1, x.getNextFreeId());
                assertEquals(false, x.isIdFree(0));
                x.delete(0);
                assertEquals(true, x.isIdFree(0));
                assertEquals(0, x.getNextFreeId());
                assertEquals(1, x.getIndex(0));
                assertEquals(-1, x.getIndex(1));
                assertEquals(3, x.getId(0));
                assertEquals(4, x.getId(1));
                assertEquals(2, x.getId(2));
                assertEquals(-1, x.getId(3));
                assertEquals(-1, x.getId(4));
                assertEquals(3.3f, x.at(2), 0.0001);
                assertEquals(4.4f, x.at(3), 0.0001);
                assertEquals(5.5f, x.at(4), 0.0001);

                /* in while loop (freeId1 == -1) */
                assertEquals(false, x.isIdFree(4));
                x.delete(4);
                assertEquals(true, x.isIdFree(4));
                assertEquals(0, x.getNextFreeId());
                assertEquals(1, x.getIndex(0));
                assertEquals(4, x.getIndex(1));
                assertEquals(-1, x.getIndex(4));
                assertEquals(3, x.getId(0));
                assertEquals(2, x.getId(1));
                assertEquals(-1, x.getId(2));
                assertEquals(-1, x.getId(3));
                assertEquals(-1, x.getId(4));
                assertEquals(3.3f, x.at(2), 0.0001);
                assertEquals(4.4f, x.at(3), 0.0001);

                /* in while loop (id < freeId1 && id > freeId0) */
                assertEquals(false, x.isIdFree(3));
                x.delete(3);
                assertEquals(true, x.isIdFree(3));
                assertEquals(0, x.getNextFreeId());
                assertEquals(1, x.getIndex(0));
                assertEquals(3, x.getIndex(1));
                assertEquals(4, x.getIndex(3));
                assertEquals(-1, x.getIndex(4));
                assertEquals(2, x.getId(0));
                assertEquals(-1, x.getId(1));
                assertEquals(-1, x.getId(2));
                assertEquals(-1, x.getId(3));
                assertEquals(-1, x.getId(4));
                assertEquals(3.3f, x.at(2), 0.0001);

                /* in while loop (id < freeId1 && id > freeId0) */
                assertEquals(false, x.isIdFree(2));
                x.delete(2);
                assertEquals(true, x.isIdFree(3));
                assertEquals(0, x.getNextFreeId());
                assertEquals(1, x.getIndex(0));
                assertEquals(2, x.getIndex(1));
                assertEquals(3, x.getIndex(2));
                assertEquals(4, x.getIndex(3));
                assertEquals(-1, x.getIndex(4));
                assertEquals(-1, x.getId(0));
                assertEquals(-1, x.getId(1));
                assertEquals(-1, x.getId(2));
                assertEquals(-1, x.getId(3));
                assertEquals(-1, x.getId(4));
        }
}
