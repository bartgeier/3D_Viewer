package bertrand.myopengl.OpenGL;

import java.nio.ByteBuffer;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;

public class Presenter {


        public void renderBackground() {
                GLES.glClearColor(0.8f,0.5f,0.0f,1.0f);
                GLES.glClear(GLES.GL_COLOR_BUFFER_BIT |GLES.GL_DEPTH_BUFFER_BIT);
                //GLES.glEnable(GLES.GL_DEPTH_TEST);
                GLES.glEnable(GLES.GL_CULL_FACE);
        }
/*
        public void render(ObjectModel object) {
                        GLES.glUseProgram(object.shader.program);
                        GLES.glUniformMatrix4fv(
                                object.shader.modelViewMatrix_Uniform_Location,
                                1,
                                false,
                                object.modelMatrix(),
                                0
                        );

                        GLES.glBindVertexArray(object.gpuVaoName);
                        GLES.glDrawElements(
                                GLES.GL_TRIANGLES,
                                object.indices.capacity(),
                                GLES.GL_UNSIGNED_BYTE,
                                0
                        );
                        GLES.glBindVertexArray(0);
        }
*/

        public ObjectModel bindVectors(ObjectModel object) {
                object.gpuVaoName = createVertexArrayObject();
                object.gpuVectorName = gpuName();
                object.gpuIndexName = gpuName();

                bindArrayBuffer(object.fragments, object.gpuVectorName);
                bindElementArrayBuffer(object.indices, object.gpuIndexName);
                enableVertexAttribArray(object.shader);
                GLES.glBindVertexArray(0);
                GLES.glBindBuffer(GL_ARRAY_BUFFER,0);
                GLES.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
                return object;
        }

        private static int createVertexArrayObject() {
                final int oeNames[] = new int[1];
                GLES.glGenVertexArrays(1, oeNames,0);
                GLES.glBindVertexArray(oeNames[0]);
                return oeNames[0];
        }

        private static int gpuName() {
                final int gpuNames[] = new int[1];
                GLES.glGenBuffers(1, gpuNames,0);
                return gpuNames[0];
        }

        private static void bindArrayBuffer(final ByteBuffer b, final int  gpuNames) {
                GLES.glBindBuffer(GL_ARRAY_BUFFER, gpuNames);
                GLES.glBufferData(
                        GL_ARRAY_BUFFER,
                        b.capacity(),
                        b.asIntBuffer(),
                        GLES.GL_STATIC_DRAW
                );
        }

        private static void bindElementArrayBuffer(final ByteBuffer b, final int gpuIndexNames) {
                GLES.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, gpuIndexNames);
                GLES.glBufferData(
                        GL_ELEMENT_ARRAY_BUFFER,
                        b.capacity(),
                        b,
                        GLES.GL_STATIC_DRAW
                );
        }

        private static void
        enableVertexAttribArray(final Shader shader){
                GLES.glEnableVertexAttribArray(shader.position_Attrib_Location);
                GLES.glVertexAttribPointer(
                        shader.position_Attrib_Location,
                        ObjectModel.VERTEX_SIZE,
                        GLES.GL_FLOAT,
                        false,
                        ObjectModel.STRIDE,
                        ObjectModel.VERTEX_OFFSET
                );
                GLES.glEnableVertexAttribArray(shader.color_Attrib_Location);
                GLES.glVertexAttribPointer(
                        shader.color_Attrib_Location,
                        ObjectModel.COLOR_SIZE,
                        GLES.GL_FLOAT,
                        false,
                        ObjectModel.STRIDE,
                        ObjectModel.COLOR_OFFSET
                );
                GLES.glEnableVertexAttribArray(shader.normal_Attrib_Location);
                GLES.glVertexAttribPointer(
                        shader.normal_Attrib_Location,
                        ObjectModel.NORMAL_SIZE,
                        GLES.GL_FLOAT,
                        false,
                        ObjectModel.STRIDE,
                        ObjectModel.NORMAL_OFFSET
                );
        }

}
