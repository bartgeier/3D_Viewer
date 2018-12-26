package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

public class constructor {
        public static void shader(int shader_ID) {

        }
        public static void body(
                int entity_ID,
                @NotNull final SparseArray<Box.Body> bodys,
                @NotNull final Load.Info i
        ) {
                Box.Body b = new Box.Body(entity_ID, i.shader_type_ID, i.vao, i.indicesCount);
                bodys.append(entity_ID,b);
        }

        public static void location(
                int entity_ID,
                @NotNull final SparseArray<Box.Location> locations
        ) {
                Box.Location l = new Box.Location(entity_ID);
                locations.append(entity_ID, l);
        }

        public static void period(
                int entity_ID,
                @NotNull final SparseArray<Box.Periode> periods,
                @NotNull final Box.Periode.Type type,
                double ms,
                double angle
        ) {
                Box.Periode p = new Box.Periode(entity_ID, type, ms, angle);
                periods.append(entity_ID, p);
        }
}
