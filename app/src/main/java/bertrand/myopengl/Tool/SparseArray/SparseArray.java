package bertrand.myopengl.Tool.SparseArray;

public class SparseArray<T> {
        private final int length;
        private final T dummy;
        private T datas[];
        private int writeIdx;
        private int indices[];
        private int dataIds[];
        private int nextFreeId;

        public SparseArray (final T dummy, final int length){
                this.dummy = dummy;
                this.length = length;
                if (length <= 0) {
                        throw new AssertionError("SparseArray, length <= 0");
                }
                clear();
        }

        @SuppressWarnings("unchecked")
        public void clear() {
                indices = new int[this.length];
                datas = (T[]) new Object[this.length];
                dataIds = new int[this.length];
                for ( int i = 0; i<this.length; i++) {
                        if (i < this.length - 1) {
                                indices[i] = (i + 1) | 0x80000000;
                        } else {
                                indices[i] = i | 0x80000000;
                        }
                        dataIds[i] = -1; // -1 used for debug and unit tests
                        datas[i] = this.dummy;
                }
                nextFreeId = 0x80000000;
                writeIdx = 0;
        }

        /* return -1 no ID's -> array full */
        public int add(T data) {
                if (nextFreeId >= 0) {
                        return -1;
                }
                final int id = nextFreeId & 0x7FFFFFFF;
                if (indices[id] == nextFreeId) {
                        nextFreeId = 0;
                } else {
                        nextFreeId = indices[id];
                }
                indices[id] = writeIdx;
                datas[writeIdx] = data;
                dataIds[writeIdx] = id;
                writeIdx++;
                return id;
        }

        public void delete(final int id) {
                if (id >= indices.length || indices[id] < 0){
                        /* not valid id, or id is already free */
                        return;
                }
                final int last = writeIdx - 1;
                datas[indices[id]] = datas[last];
                dataIds[indices[id]] = dataIds[last];
                indices[dataIds[last]] = indices[id];
                dataIds[last] = -1; // -1 used for debug and unit tests
                writeIdx = last;
                if (nextFreeId >= 0) {
                        /* no free ids in indices */
                        nextFreeId = id | 0x80000000;
                        indices[id] = nextFreeId;
                        return;
                } else if (id < (nextFreeId & 0x7FFFFFFF)) {
                        /* id before the first indices */
                        indices[id] = nextFreeId;
                        nextFreeId = id | 0x80000000;
                        return;
                }
                int freeId_0;
                int freeId_1 = nextFreeId & 0x7FFFFFFF;
                do {
                        freeId_0 = freeId_1;
                        freeId_1 = indices[freeId_0] & 0x7FFFFFFF;
                        if (id < freeId_1 && id > freeId_0) {
                                /* id between two free indices */
                                indices[id] = freeId_1 | 0x80000000;
                                indices[freeId_0] = id | 0x80000000;
                                return;
                        } else if (freeId_1 == freeId_0) {
                                /* id behind the last free indices */
                                indices[id] = id | 0x80000000;
                                indices[freeId_0] = indices[id];
                                return;
                        }
                } while(true);
        }

        public int length() {
                return datas.length;
        }

        public int size() {
                return writeIdx;
        }

        public T at(final int idx) {
                return datas[idx];
        }

        public T atId(final int id) {
                if (id >= indices.length || indices[id] < 0) {
                        throw new AssertionError(
                                "SparseArray.at, " +
                                        "id >= indices.length || indices[id] < 0");
                }
                return datas[indices[id]];
        }


        ////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////

        /* only for unit test */
        public int getIndex(final int id) {
                return indices[id];
        }

        /* only for unit test */
        public int getId(final int idx) {
                return dataIds[idx];
        }

        /* only for unit test */
        public int getNextFreeId() {
                return nextFreeId;
        }

        /* only for unit test */
        public boolean hasId(final int id) {
                return !(id >= indices.length || id < 0);
        }

        /* only for unit test */
        public boolean isIdFree(final int id) {
                if (id >= indices.length || id < 0) {
                        throw new AssertionError(
                                "SparseArray.isIdFree, id >= indices.length || id < 0"
                        );
                }
                return (indices[id] < 0);
        }
}
