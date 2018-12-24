package bertrand.myopengl.Models;

import android.opengl.Matrix;

import bertrand.myopengl.Tool.Vec3;

public class Location {
        public Vec3 position = new Vec3(0,0,0);
        public Vec3 rotation = new Vec3(0,0,0);
        public Vec3 scale = new Vec3(1,1,1);

        public float[] modelMatrix() {
                float[] matrix = new float[16];
                Matrix.setIdentityM(matrix, 0);
                Matrix.translateM(matrix,0,position.x, position.y, position.z);
                Matrix.rotateM(matrix,0, rotation.x, 1, 0, 0);
                Matrix.rotateM(matrix,0, rotation.y, 0, 1, 0);
                Matrix.rotateM(matrix,0, rotation.z,  0, 0, 1);
                Matrix.scaleM(matrix,0, scale.x, scale.y, scale.z);
                return matrix;
        }

}
