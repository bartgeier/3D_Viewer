package bertrand.myopengl.System;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Entitys.Box;

public class SCamera {
        public static void init(
                @NotNull final Box.Camera camera
        ) {
                Matrix.setIdentityM(camera.T, 0);
                Matrix.setIdentityM(camera.R, 0);
        }
}
