package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

public class Locations {
        public static SparseArray<Box.Location> array = new SparseArray<>();
        public static void addEntity(
                @NotNull final SparseArray<Box.Location> locations,
                int entity_ID
        ) {
                Box.Location l = new Box.Location(entity_ID);
                locations.append(entity_ID, l);
        }

        public void update(
                @NotNull final SparseArray<Box.Location> locations,
                @NotNull final SparseArray<Box.Periode> periods,
                final float dt_ms
        ) {
                int size = locations.size();
                for (int i = 0; i < size; i++) {
                        Box.Location loc = locations.valueAt(i);
                        Box.Periode period = periods.get(loc.entity_ID);
                        switch(period.type) {
                                case ROTATE:
                                        rotation(loc, dt_ms, period);
                                        break;
                                case SWING:
                                        swing(loc, dt_ms, period);
                                        break;
                                case UNDEF:
                                        break;
                                default:
                                        break;
                        }
                }
        }

        private static void rotation(
                @NotNull final Box.Location location,
                final float dt_ms,
                @NotNull final Box.Periode rotation
        ) {
                double a = rotation.angle;
                double p = rotation.period_ms;
                a += dt_ms * 360/p;
                rotation.angle = a;
                location.rotation.y = (float)a;
        }

        private static void swing(
                @NotNull final Box.Location location,
                final float dt_ms,
                @NotNull final Box.Periode swing
        ) {
                double a = swing.angle;
                double p = swing.period_ms;
                a += dt_ms * 360/p;
                a %= 360;
                final double rad = Math.toRadians(a);
                final double x = 4 * Math.sin(rad);
                swing.angle = a;
                location.position.x = (float)x;
        }

}
