package bertrand.myopengl.Tool;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Arr {
        public static ByteBuffer allocateBuffer(float[] array) {
                ByteBuffer buffer;
                buffer = ByteBuffer.allocateDirect(array.length * 4);
                buffer.order(ByteOrder.nativeOrder());
                buffer.asFloatBuffer().put(array);
                buffer.position(0);
                return buffer;
        }

        public static ByteBuffer allocateBuffer(int[] array) {
                ByteBuffer buffer;
                buffer = ByteBuffer.allocateDirect(array.length * 4);
                buffer.order(ByteOrder.nativeOrder());
                buffer.asIntBuffer().put(array);
                buffer.position(0);
                return buffer;
        }
}
