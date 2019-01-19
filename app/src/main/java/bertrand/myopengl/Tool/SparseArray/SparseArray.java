package bertrand.myopengl.Tool.SparseArray;

class Index {
        int idx;
        boolean free;
}

public class SparseArray {
        public float datas[];
        public int writeIdx = 0;
        private Index indices[];
        private int dataIds[];
        private int nextFreeId = 0;

        public SparseArray (int size){
                indices = new Index[size];
                datas = new float[size];
                dataIds = new int[size];
                for ( int i = 0; i<size; i++) {
                        Index index = new Index();
                        indices[i] = index;
                        indices[i].free = true;
                        if (i < size - 1) {
                                indices[i].idx = i + 1;
                        } else {
                                indices[i].idx = -1;
                        }
                        dataIds[i] = -1; // -1 used for debug and unit tests
                }
        }

        /* return -1 no ID's array full */
        public int add(float data) {
                if (nextFreeId == -1) {
                        return -1;
                }
                final int id = nextFreeId;
                nextFreeId = indices[nextFreeId].idx;
                indices[id].idx = writeIdx;
                indices[id].free = false;
                datas[writeIdx] = data;
                dataIds[writeIdx] = id;
                writeIdx++;
                return id;
        }

        public boolean hasId(int id) {
                return !(id >= indices.length || id < 0);
        }

        public boolean isIdFree(int id) {
                if (id >= indices.length || id < 0) {
                        throw new AssertionError(
                                "SparseArray.isIdFree, id >= indices.length || id < 0"
                        );
                }
                return indices[id].free;
        }

        public float at(int id) {
                if (id >= indices.length || indices[id].free) {
                        throw new AssertionError(
                                "SparseArray.at, " +
                                        "id >= indices.length || indices[id].free");
                }
                return datas[indices[id].idx];
        }

        public void delete(int id) {
                if (id >= indices.length || indices[id].free){
                        return;
                }
                final int last = writeIdx - 1;
                datas[indices[id].idx] = datas[last];
                dataIds[indices[id].idx] = dataIds[last];
                indices[dataIds[last]].idx = indices[id].idx;
                dataIds[last] = -1; // -1 used for debug and unit tests
                writeIdx = last;
                if (nextFreeId == -1) {
                        indices[id].idx = -1;
                        indices[id].free = true;
                        nextFreeId = id;
                        return;
                } else if (id < nextFreeId) {
                        indices[id].idx = nextFreeId;
                        indices[id].free = true;
                        nextFreeId = id;
                        return;
                }
                int freeId_0;
                int freeId_1 = nextFreeId;
                do {
                        freeId_0 = freeId_1;
                        freeId_1 = indices[freeId_0].idx;
                        if (id < freeId_1 && id > freeId_0) {
                                indices[id].idx = freeId_1;
                                indices[id].free = true;
                                indices[freeId_0].idx = id;
                                return;
                        } else if (freeId_1 == -1) {
                                indices[id].idx = -1;
                                indices[id].free = true;
                                indices[freeId_0].idx = id;
                                return;
                        }
                } while(freeId_1 > -1);
        }


        /* only for unit test */
        public int getIndex(int id) {
                return indices[id].idx;
        }

        /* only for unit test */
        public int getId(int idx) {
                return dataIds[idx];
        }

        /* only for unit test */
        public int getNextFreeId() {
                return nextFreeId;
        }
}
