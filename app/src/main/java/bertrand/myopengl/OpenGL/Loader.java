package bertrand.myopengl.OpenGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import bertrand.myopengl.Models.ColoredModel;
import bertrand.myopengl.Shaders.ColoredShader;

public class Loader {
        public static void toVAO(
        ColoredModel model,
        final int[] indices,
        final float[] positions,
        final float[] colors,
        final float[] normals,
        final ColoredShader s) {
                int vao = GPU.createVertexArrayObject();
                int[] vbos = new int[4];
                vbos[0] = GPU.loadIndecisBuffer(allocateBuffer(indices));
                vbos[1] = GPU.loadFragmentBuffer(s.attributeID.position, 3, allocateBuffer(positions));
                vbos[2] = GPU.loadFragmentBuffer(s.attributeID.color, 4, allocateBuffer(colors));
                vbos[3] = GPU.loadFragmentBuffer(s.attributeID.normal, 3, allocateBuffer(normals));
                GLES.glBindVertexArray(0);
                model.setShader(s);
                model.init(vao, vbos, indices.length);
        }

        private static ByteBuffer allocateBuffer(float[] array) {
                ByteBuffer buffer;
                buffer = ByteBuffer.allocateDirect(array.length * 4);
                buffer.order(ByteOrder.nativeOrder());
                buffer.asFloatBuffer().put(array);
                buffer.position(0);
                return buffer;
        }
        private static ByteBuffer allocateBuffer(int[] array) {
                ByteBuffer buffer;
                buffer = ByteBuffer.allocateDirect(array.length * 4);
                buffer.order(ByteOrder.nativeOrder());
                buffer.asIntBuffer().put(array);
                buffer.position(0);
                return buffer;
        }
}
