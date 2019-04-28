package bertrand.myopengl.Tool.Scene;

import java.util.ArrayList;
import java.util.List;

public class Hierarchy {
        public List<HierarchyData> datas = new ArrayList<>();

        public void createRoot() {
                HierarchyData x = new HierarchyData();
                datas.clear();
                datas.add(x);
        }

        public void attach(Hierarchy sub) {
                final int offset = datas.size();
                HierarchyData x = sub.datas.get(0);
                x.idx += offset;
                datas.add(x);
                for (int i = 1; i < sub.datas.size(); i++) {
                        x = sub.datas.get(i);
                        x.parent_idx += offset;
                        x.idx += offset;
                        datas.add(x);
                }
        }
}
