package bertrand.myopengl.Entitys;

import android.util.SparseArray;

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

        public static void light(
                int light_ID,
                @NotNull final SparseArray<Box.Light> lights,
                float x, float y, float z,
                float red, float green, float blue
        ) {
                Box.Light l = new Box.Light(x, y, z, red, green, blue);
                lights.append(light_ID, l);
        }

        public static void location(
                int location_ID,
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
                locations.append(location_ID, l);
        }

        public static void period(
                int period_ID,
                int location_ID,
                @NotNull final SparseArray<Box.Periode> periods,
                @NotNull final Box.Periode.Type type,
                double period_ms,
                double start_angle
        ) {
                Box.Periode p = new Box.Periode(location_ID, type, period_ms, start_angle);
                periods.append(period_ID, p);
        }
}