package bertrand.myopengl.Entitys;

import bertrand.myopengl.Tool.SparseArray.SparseArray;

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

        public static void cameras(@NotNull final SparseArray<Box.Camera> cameras) {
                cameras.clear();
        }

        public static void shaders(
                @NotNull final SparseArray<Box.Shader> shaders
        ) {
                for (int i = 0; i < shaders.size(); i++) {
                        GPU.cleanUp(
                                shaders.at(i).programID,
                                shaders.at(i).vertexShaderID,
                                shaders.at(i).fragmentShaderID
                        );
                }
                shaders.clear();
        }
/*
        public static void meshes(
                @NotNull final SparseArray<Box.Mesh> meshes
        ) {
                for (int i = 0; i < meshes.size(); i++) {
                        if (meshes.at(i).texId != 0) {
                                GPU.deleteTextureID(meshes.at(i).texId);
                        }
                        GPU.deleteVBOs(meshes.at(i).vbos);
                        GPU.deleteVertexArrayObject(meshes.at(i).vao);
                }
                meshes.clear();
        }
*/

        public static void meshes(
                @NotNull final SparseArray<Box.Mesh> meshes
        ) {
                for (int i = 0; i < meshes.size(); i++) {
                        GPU.deleteVBOs(meshes.at(i).vbos);
                        GPU.deleteVertexArrayObject(meshes.at(i).vao);
                }
                meshes.clear();
        }


        public static void textures(
                @NotNull final SparseArray<Box.Texture> textures
        ) {
                for (int i = 0; i < textures.size(); i++) {
                                GPU.deleteTextureID(textures.at(i).texNo);
                }
                textures.clear();
        }

        public static void locations(@NotNull final SparseArray<Box.Location> locations) {
                locations.clear();
        }

        public static void swings(@NotNull final SparseArray<Box.Swing> swings) {
                swings.clear();
        }

        public static void spins(@NotNull final SparseArray<Box.Spin> spins) {
                spins.clear();
        }
}
