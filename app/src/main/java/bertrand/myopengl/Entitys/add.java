package bertrand.myopengl.Entitys;

import bertrand.myopengl.Tool.SparseArray.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Tool.Color4f;
import bertrand.myopengl.Tool.Vec3;


public class add {
        public static void backGroundColor(
                Box.BackGround backGround,
                float red, float green, float blue
        ) {
                backGround.color = new Color4f(red, green, blue);
        }

        public static int light(
                @NotNull final SparseArray<Box.Light> lights,
                float x, float y, float z,
                float red, float green, float blue
        ) {
                Box.Light l = new Box.Light(x, y, z, red, green, blue);
                return lights.add(l);
        }

        public static int location(
                @NotNull final SparseArray<Box.Location> locations,
                int shaderProgram_ID,
                int vao,
                int indicesCount,
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
                Box.Location l = new Box.Location(
                        new Vec3(pos_x, pos_y, pos_z),
                        new Vec3(rot_x, rot_y, rot_z),
                        new Vec3(scale_x, scale_y, scale_z),
                        shaderProgram_ID,
                        vao,
                        indicesCount
                );
                return locations.add(l);
        }

        public static int period(
                int location_ID,
                @NotNull final SparseArray<Box.Periode> periods,
                @NotNull final Box.Periode.Type type,
                double period_ms,
                double start_angle
        ) {
                Box.Periode p = new Box.Periode(location_ID, type, period_ms, start_angle);
                return periods.add(p);
        }
}
