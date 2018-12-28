package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

public class Constructor {
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

        public static void location(
                int entity_ID,
                @NotNull final SparseArray<Box.Location> locations,
                final float pos_x,
                final float pos_y,
                final float pos_z,
                final float rot_x,
                final float rot_y,
                final float rot_z,
                final float scale_x,
                final float scale_y,
                final float scale_z
        ) {
                Box.Location l = new Box.Location(entity_ID);
                l.position.x = pos_x;
                l.position.y = pos_y;
                l.position.z = pos_z;
                l.rotation.x = rot_x;
                l.rotation.y = rot_y;
                l.rotation.z = rot_z;
                l.scale.x = scale_x;
                l.scale.y = scale_y;
                l.scale.z = scale_z;
                locations.append(entity_ID, l);
        }

        public static void period(
                int entity_ID,
                @NotNull final SparseArray<Box.Periode> periods,
                @NotNull final Box.Periode.Type type,
                double period_ms,
                double start_angle
        ) {
                Box.Periode p = new Box.Periode(entity_ID, type, period_ms, start_angle);
                periods.append(entity_ID, p);
        }
}
