package bertrand.myopengl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bertrand.myopengl.OpenGL.ColoredModel;
import bertrand.myopengl.OpenGL.SimpleShader;
import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.Vec3;

public class OBJLoader {

      public static ColoredModel loadObjModel(InputStreamReader s) {
            BufferedReader reader = new BufferedReader(s);
            String line;
            ArrayList<Vec3> vertices = new ArrayList<>();
            List<Vec2> textures = new ArrayList<>();
            List<Vec3> normals = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();

            float[] verticesArray = null;
            float[] normalsArray = null;
            float[] textureArray = null;
            int[] indicesArray = null;

            try {
                  while (true) {
                        line = reader.readLine();
                        String[] currentLine = line.split(" ");
                        if (line.startsWith("v ")) {
                              Vec3 vertex = new Vec3(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                              vertices.add(vertex);
                        } else if (line.startsWith("vt ")) {
                              Vec2 texture = new Vec2(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                              textures.add(texture);
                        } else if (line.startsWith("vn ")) {
                              Vec3 normal = new Vec3(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
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

                        processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                        processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                        processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                        line = reader.readLine();
                  }
                  reader.close();
            } catch (Exception e) {
                  e.printStackTrace();
            }

            verticesArray = new float[vertices.size() * 3];
            indicesArray = new int[indices.size()];

            int vertexPointer = 0;
            for (Vec3 vertex : vertices) {
                  verticesArray[vertexPointer++] = vertex.x;
                  verticesArray[vertexPointer++] = vertex.y;
                  verticesArray[vertexPointer++] = vertex.z;
            }

            for (int i = 0; i < indices.size(); i++) {
                  indicesArray[i] = indices.get(i);
            }



            float[]  colorsArray = color(verticesArray);

            Stall o = new Stall();
            o.init(indicesArray, verticesArray,colorsArray,normalsArray, new SimpleShader());
            o.position.x = -0.5f;
            o.position.y = -3f;
            o.position.z = -10f;
            return o;//loader.loadToVAO(verticesArray, textureArray, indicesArray);
      }

      private static float[] color(float[] vertices) {
            float[] colors = new float[(vertices.length/3) * 4];
            for (int idx = 0; idx < colors.length; idx += 4) {
                  colors[idx+0] = 0.5f;
                  colors[idx+1] = 0.5f;
                  colors[idx+2] = 0.5f;
                  colors[idx+3] = 1f;
            }
            return colors;
      }



      private static void processVertex(
              String[] vertexData,
              List<Integer> indices,
              List<Vec2> textures,
              List<Vec3> normals,
              float[] textureArray,
              float[] normalsArray) {

            int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
            indices.add(currentVertexPointer);

/*
            Float2 currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
            textureArray[currentVertexPointer * 2] = currentTex.x;
            textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
*/

            Vec3 currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
            normalsArray[currentVertexPointer * 3] = currentNorm.x;
            normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
            normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;

      }
}
                  
                  
                  
                  
                  
                  
            