package bertrand.myopengl.Shaders;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import bertrand.myopengl.OpenGL.BaseShader;
import bertrand.myopengl.OpenGL.GPU;

public class ShaderRepo {
        public TexturedShader texturedShader;
        public ColoredShader coloredShader;
        private ArrayList<BaseShader> shaders = new ArrayList<>();

        public ShaderRepo() {
                texturedShader = new TexturedShader();
                shaders.add(texturedShader);
                coloredShader = new ColoredShader();
                shaders.add(coloredShader);
        }

        public void setProjectionMatrix(@NotNull final float[] projectionMatrix){
                if(projectionMatrix.length != 16) {
                        throw new AssertionError("ShaderRepo projectionMatrix.length != 16");
                }
                for(BaseShader shader :shaders) {
                        GPU.useProgram(shader.programID);
                        GPU.loadMatrix(shader.u_ProjectionMatrix, projectionMatrix);
                }
                GPU.useProgram(0);
        }

        public void cleanUp() {
                for(BaseShader shader : shaders) {
                        shader.cleanUp();
                }
        }
}
