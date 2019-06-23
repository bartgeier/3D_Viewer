package bertrand.myopengl.Entitys;

import bertrand.myopengl.Tool.SparseArray.SparseArray;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Tool.Color4f;


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
}
