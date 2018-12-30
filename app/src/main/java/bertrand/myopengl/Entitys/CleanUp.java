package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import bertrand.myopengl.OpenGL.GPU;
import bertrand.myopengl.Tool.Color4f;

public class CleanUp {
        public static void backGroundColor(@NotNull final Box.BackGround backGround) {
                backGround.color = new Color4f(0.8f,0.8f,0.8f);
        }

        public static void lights(@NotNull final SparseArray<Box.Light> lights) {
                lights.clear();
        }

        public static void shaders(
                @NotNull final SparseArray<Box.ShaderProgam> shaderProgams,
                @NotNull final SparseArray<Box.ShaderDeleteInfo> shaderDeleteInfos
        ) {
                int size = shaderDeleteInfos.size();
                for (int i = 0; i < size; i++) {
                        GPU.cleanUp(
                                shaderDeleteInfos.valueAt(i).programID,
                                shaderDeleteInfos.valueAt(i).vertexShaderID,
                                shaderDeleteInfos.valueAt(i).fragmentShaderID
                        );
                }
                shaderDeleteInfos.clear();
                shaderProgams.clear();
        }

        public static void bodys(
                @NotNull final SparseArray<Box.Body> bodys,
                @NotNull final ArrayList<Box.BodyDeleteInfo> bodyDeleteInfos
        ) {
                int size = bodyDeleteInfos.size();
                for (Box.BodyDeleteInfo info : bodyDeleteInfos) {
                        if (info.texId != 0) {
                                GPU.deleteTextureID(info.texId);
                        }
                        GPU.deleteVBOs(info.vbos);
                        GPU.deleteVertexArrayObject(info.vao);
                }
                bodyDeleteInfos.clear();
                bodys.clear();
        }

        public static void locations(@NotNull final SparseArray<Box.Location> locations) {
                locations.clear();
        }

        public static void periods(@NotNull final SparseArray<Box.Periode> periods) {
                periods.clear();
        }
}
