package bertrand.myopengl.Models;

import android.opengl.Matrix;

import bertrand.myopengl.Tool.Vec3;

public class RawModel {
        protected int vao = 0;
        protected int[] vbos;
        protected int indicesCount;

        public Vec3 position;
        public Vec3 rotation;
        public Vec3 scale;

        public static float[] projectionMatrix = new float[16];

        protected float[] modelMatrix() {
                float[] matrix = new float[16];
                Matrix.setIdentityM(matrix, 0);
                Matrix.translateM(matrix,0,position.x, position.y, position.z);
                Matrix.rotateM(matrix,0, rotation.x, 1, 0, 0);
                Matrix.rotateM(matrix,0, rotation.y, 0, 1, 0);
                Matrix.rotateM(matrix,0, rotation.z,  0, 0, 1);
                Matrix.scaleM(matrix,0, scale.x, scale.y, scale.z);
                return matrix;
        }

        public RawModel() {
                position = new Vec3(0,0,0);
                rotation = new Vec3(0,0,0);
                scale = new Vec3(1,1,1);
        }

        public void init(int vao, int[] vbos, int indicesCount) {
                this.vao = vao;
                this.vbos = vbos;
                this.indicesCount = indicesCount;
        }

        public interface UpdateListner {
                public void withDelta(RawModel self, float dt_ms);
        }
        public UpdateListner update;
        public void updateWithDelta(float dt_ms) {
                update.withDelta(this,dt_ms);

        }

}
