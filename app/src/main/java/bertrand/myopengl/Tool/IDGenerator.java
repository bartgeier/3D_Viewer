package bertrand.myopengl.Tool;

import java.util.ArrayList;

public class IDGenerator {
        private int nextID = 0;
        private ArrayList<Integer> holes = new ArrayList<>();

        public int getID() {
                if (holes.size() == 0) {
                        return nextID++;
                } else {
                        return holes.remove(holes.size()-1);
                }
        }

        public void deleteID(int id) {
                if (id >= nextID) {
                        return;
                } else if (id == (nextID - 1)) {
                        nextID--;
                } else {
                        holes.add(id);
                }
        }

        /* only for unit test */
        public int test_nextID() {
                return nextID;
        }
}
