package bertrand.myopengl.Shaders;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import bertrand.myopengl.OpenGL.BaseShader;
import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.RFile.RFile_IF;

public class ShaderRepo {
        private final Context context;
        private final RFile_IF file;
        public TexturedShader texturedShader;
        public ColoredShader coloredShader;
        private ArrayList<BaseShader> shaders = new ArrayList<>();

        public ShaderRepo(@NotNull final Context c) {
                context = c;
                file = new RFile(context);
                texturedShader = new TexturedShader(
                        file.string(":/raw/shader_textured_vert.txt"),
                        file.string(":/raw/shader_textured_frag.txt")
                );
                shaders.add(texturedShader);
                coloredShader = new ColoredShader(
                        file.string(":/raw/shader_colored_vert.txt"),
                        file.string(":/raw/shader_colored_frag.txt")
                );
                shaders.add(coloredShader);
        }

        public void setProjectionMatrix(@NotNull final float[] projectionMatrix){
                if(projectionMatrix.length != 16) {
                        throw new AssertionError("ShaderRepo projectionMatrix.length != 16");
                }
                for(BaseShader shader :shaders) {
                        GPU.useProgram(shader.programID);
                        GPU.loadMatrix(shader.u_PojectionMatrix(), projectionMatrix);
                }
                GPU.useProgram(0);
        }

        public void cleanUp() {
                for(BaseShader shader : shaders) {
                        shader.cleanUp();
                }
        }
}
