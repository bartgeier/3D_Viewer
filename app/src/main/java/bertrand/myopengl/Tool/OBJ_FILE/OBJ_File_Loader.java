package bertrand.myopengl.Tool.OBJ_FILE;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bertrand.myopengl.Tool.RFile.RFile_IF;
import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.Vec3;

public class OBJ_File_Loader {
        public static OBJ_Data loadObjModel(@NotNull RFile_IF file, final String objFilePath) {
                InputStream is = file.inputStream(objFilePath);
                InputStreamReader s = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(s);

                String line;
                ArrayList<Vec3> vertices = new ArrayList<>();
                List<Vec2> textures = new ArrayList<>();
                List<Vec3> normals = new ArrayList<>();
                List<Integer> indices = new ArrayList<>();

                float[] positionsArray = null;
                float[] normalsArray = null;
                float[] textureArray = null;
                int[] indicesArray = null;

                try {
                while (true) {
                        line = reader.readLine();
                        String[] currentLine = line.split(" ");
                        if (line.startsWith("v ")) {
                                Vec3 vertex = new Vec3(
                                      Float.parseFloat(currentLine[1]),
                                      Float.parseFloat(currentLine[2]),
                                      Float.parseFloat(currentLine[3]));
                                vertices.add(vertex);
                        } else if (line.startsWith("vt ")) {
                                Vec2 texture = new Vec2(
                                      Float.parseFloat(currentLine[1]),
                                      Float.parseFloat(currentLine[2]));
                                textures.add(texture);
                        } else if (line.startsWith("vn ")) {
                                Vec3 normal = new Vec3(
                                      Float.parseFloat(currentLine[1]),
                                      Float.parseFloat(currentLine[2]),
                                      Float.parseFloat(currentLine[3]));
                                normals.add(normal);
                        } else if (line.startsWith("f ")) {
                                textureArray = new float[vertices.size() * 2];
                                normalsArray = new float[vertices.size() * 3];
                                break;
                        }
                }
                while (line != null) {
                        if (!line.startsWith("f ")) {
                                line = reader.readLine();
                                continue;
                        }
                        String[] currentLine = line.split(" ");
                        String[] vertex1 = currentLine[1].split("/");
                        String[] vertex2 = currentLine[2].split("/");
                        String[] vertex3 = currentLine[3].split("/");

                        processVertex(vertex1, textures, normals, indices, textureArray, normalsArray);
                        processVertex(vertex2, textures, normals, indices, textureArray, normalsArray);
                        processVertex(vertex3, textures, normals, indices, textureArray, normalsArray);
                        line = reader.readLine();
                }
                reader.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
                positionsArray = new float[vertices.size() * 3];
                indicesArray = new int[indices.size()];
                int vertexPointer = 0;
                for (Vec3 vertex : vertices) {
                        positionsArray[vertexPointer++] = vertex.x;
                        positionsArray[vertexPointer++] = vertex.y;
                        positionsArray[vertexPointer++] = vertex.z;
                }
                for (int i = 0; i < indices.size(); i++) {
                        indicesArray[i] = indices.get(i);
                }
                return new OBJ_Data(indicesArray, positionsArray, textureArray, normalsArray);
        }

        private static void processVertex(
                @NotNull final String[] vertexData,
                @NotNull final List<Vec2> textures,
                @NotNull final List<Vec3> normals,
                @NotNull List<Integer> indices,
                @NotNull float[] textureArray,
                @NotNull float[] normalsArray
        ) {
                int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
                indices.add(currentVertexPointer);

                Vec2 currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
                textureArray[currentVertexPointer * 2] = currentTex.x;
                textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;

                Vec3 currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
                normalsArray[currentVertexPointer * 3] = currentNorm.x;
                normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
                normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
        }
}
                  
                  
                  
                  
                  
                  
            