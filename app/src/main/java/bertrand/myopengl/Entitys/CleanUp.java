package bertrand.myopengl.Entitys;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

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
                @NotNull final SparseArray<Box.Shader> shaders
        ) {
                int size = shaders.size();
                for (int i = 0; i < size; i++) {
                        GPU.cleanUp(
                                shaders.valueAt(i).programID,
                                shaders.valueAt(i).vertexShaderID,
                                shaders.valueAt(i).fragmentShaderID
                        );
                }
                shaders.clear();
        }

        public static void meshes(
                @NotNull final SparseArray<Box.Mesh> meshes
        ) {
                int size = meshes.size();
                for (int i = 0; i < size; i++) {
                        if (meshes.valueAt(i).texId != 0) {
                                GPU.deleteTextureID(meshes.valueAt(i).texId);
                        }
                        GPU.deleteVBOs(meshes.valueAt(i).vbos);
                        GPU.deleteVertexArrayObject(meshes.valueAt(i).vao);
                }
                meshes.clear();
        }

        public static void locations(@NotNull final SparseArray<Box.Location> locations) {
                locations.clear();
        }

        public static void periods(@NotNull final SparseArray<Box.Periode> periods) {
                periods.clear();
        }
}
