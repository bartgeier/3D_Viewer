package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.GPU;

public class CleanUp {
        public static void bodys(@NotNull final SparseArray<Box.Body> bodys) {
                int size = bodys.size();
                for (int i = 0; i < size; i++) {
                        GPU.deleteVertexArrayObject( bodys.valueAt(i).vao);
                }
                bodys.clear();
        }

        public static void locations(@NotNull final SparseArray<Box.Location> locations) {
                locations.clear();
        }

        public static void periods(@NotNull final SparseArray<Box.Periode> periods) {
                periods.clear();
        }
}
