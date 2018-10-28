package bertrand.myopengl.Models;

import android.opengl.Matrix;

import org.jetbrains.annotations.NotNull;

import bertrand.myopengl.OpenGL.AbstractShader;
import bertrand.myopengl.OpenGL.GLES;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Shaders.ColoredShader;
import bertrand.myopengl.Tool.Vec3;
import bertrand.myopengl.Tool.VectorMath;

public class ColoredModel extends RawModel {
        public void setShader(ColoredShader shader) {
                this.shader = shader;
        }
        private ColoredShader shader;

        public void render(float[] parentModelViewMatrix) {
                float[] modelVieMatrix = new float[16];
                Matrix.multiplyMM(
                        modelVieMatrix,
                        0,
                        parentModelViewMatrix,
                        0,
                        modelMatrix(),
                        0
                );
                prepareToDraw(shader,modelVieMatrix);
                GPU.render(vao, indicesCount);
        }

        private void prepareToDraw(@NotNull AbstractShader shader, float[] modelVieMatrix) {
                GLES.glUseProgram(shader.programID);
                GLES.glUniformMatrix4fv(
                        shader.u.modelViewMatrix,
                        1,
                        false,
                        modelVieMatrix,
                        0
                );
                GLES.glUniformMatrix4fv(
                        shader.u.projectionMatrix,
                        1,
                        false,
                        projectionMatrix,
                        0
                );

                final float red = 1;
                final float green = 1;
                final float blue = 1;
                GLES.glUniform3f(shader.u.lightAmbientColor, red, green, blue);
                GLES.glUniform1f(shader.u.lightAmbientIntens,0.1f);
                Vec3 lightDirection = VectorMath.vector3Normalize(new Vec3(0,1f,-1));
                GLES.glUniform3f(
                        shader.u.lightDirection,
                        lightDirection.x,
                        lightDirection.y,
                        lightDirection.z);
                GLES.glUniform1f(shader.u.lightDiffuseIntens,0.7f);

                GLES.glUniform1f(shader.u.matSpecularIntensity,2.0f);
                GLES.glUniform1f(shader.u.shininess,8.0f);
        }

}

