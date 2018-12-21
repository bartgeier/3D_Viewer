package bertrand.myopengl.Models;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.Light.Light;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Tool.Vec3;

public abstract class RawModel implements Cloneable {
        protected int vao = 0;
        protected int[] vbos;
        protected int indicesCount;

        public Vec3 position;
        public Vec3 rotation;
        public Vec3 scale;

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
/*
        public Object clone() {
                Object x = null;
                try {x = super.clone();} catch (CloneNotSupportedException e) {}
                return x;
        }*/

        public void cleanUp() {
                GPU.deleteVBOs(vbos);
                GPU.deleteVertexArrayObject(vao);
                indicesCount = 0;
        }

        public interface UpdateListner {
                public void withDelta(RawModel self, float dt_ms);
        }
        public UpdateListner update;
        public void updateWithDelta(final float dt_ms) {
                if(update != null) {
                        update.withDelta(this, dt_ms);
                }

        }

        public abstract void render(
                @NotNull final float[] parentModelViewMatrix,
                @NotNull Light light
        );

}
