package bertrand.myopengl.Tool.OBJ_FILE;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bertrand.myopengl.Tool.Vec2;
import bertrand.myopengl.Tool.Vec3;

public class OBJParser {
	public static ModelData transform(@NotNull InputStream is) {
		//InputStream is = file.inputStream(objFilePath);
		InputStreamReader s = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(s);

		String line;
		List<VertexInfo> vs = new ArrayList<>();
		List<Vec2> vts = new ArrayList<>();
		List<Vec3> vns = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		try {
			while (true) {
				line = reader.readLine();
				if (line.startsWith("v ")) {
					String[] currentLine = line.split(" ");
					Vec3 v = new Vec3(
					        (float) Float.valueOf(currentLine[1]),
                                                (float) Float.valueOf(currentLine[2]),
                                                (float) Float.valueOf(currentLine[3])
                                        );
					VertexInfo vInfo = new VertexInfo(vs.size(), v);
					vs.add(vInfo);
				} else if (line.startsWith("vt ")) {
					String[] currentLine = line.split(" ");
					Vec2 texture = new Vec2(
                                                (float) Float.valueOf(currentLine[1]),
                                                (float) Float.valueOf(currentLine[2])
                                        );
					vts.add(texture);
				} else if (line.startsWith("vn ")) {
					String[] currentLine = line.split(" ");
					Vec3 vn = new Vec3(
					        (float) Float.valueOf(currentLine[1]),
                                                (float) Float.valueOf(currentLine[2]),
                                                (float) Float.valueOf(currentLine[3])
                                        );
					vns.add(vn);
				} else if (line.startsWith("f ")) {
					break;
				}
			}
			while (line != null) {
				if(line.startsWith("f ")) {
					String[] f = line.split(" ");
					String[] f1 = f[1].split("/");
					String[] f2 = f[2].split("/");
					String[] f3 = f[3].split("/");
					arrange_f_indexes(indices, vs, f1);
					arrange_f_indexes(indices, vs, f2);
					arrange_f_indexes(indices, vs, f3);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Error reading the file");
		}
		setUnusedVerticesZero(vs);
		float[] verticesArray = new float[vs.size() * 3];
		float[] texturesArray = new float[vs.size() * 2];
		float[] normalsArray = new float[vs.size() * 3];
		convertDataToArrays(
                        verticesArray,
                        texturesArray,
                        normalsArray,
                        vs,
                        vts,
                        vns
                );
		int[] indicesArray = convertIndicesListToArray(indices);
                return new ModelData(
		        verticesArray,
                        texturesArray,
                        normalsArray,
                        indicesArray
                );
	}

	private static void arrange_f_indexes(
                final List<Integer> indices,
                final List<VertexInfo> vs,
	        final String[] fx
        ) {
		VertexInfo vInfo = vs.get(Integer.parseInt(fx[0]) - 1);
		int textureIdx = Integer.parseInt(fx[1]) - 1;
		int normalIdx = Integer.parseInt(fx[2]) - 1;
		if (vInfo.missTextureIdx()) {
			vInfo.setIndex(textureIdx, normalIdx);
			indices.add(vInfo.idx());
			return;
		}

                VertexInfo x = vInfo;
	        do {
                        if (x.hasEqual(textureIdx, normalIdx)) {
                                indices.add(x.idx());
                                return;
                        }
                        x = x.next();
                } while(x != null);

                final VertexInfo v = new VertexInfo(vs.size(), vInfo.v());
                v.setIndex(textureIdx, normalIdx);
                vInfo.next(v);
                vs.add(v);
                indices.add(v.idx());
        }

        private static void setUnusedVerticesZero(List<VertexInfo> vs){
                for(VertexInfo vInfo:vs){
                        if(vInfo.missTextureIdx()){
                                vInfo.setIndex(0,0);
                        }
                }
        }

        private static void convertDataToArrays(
                float[] verticesArray,
                float[] texturesArray,
                float[] normalsArray,
                List<VertexInfo> vs,
                List<Vec2> vts,
                List<Vec3> vns
        ) {
                for (int i = 0; i < vs.size(); i++) {
                        VertexInfo vInfo = vs.get(i);
                        Vec3 v = vInfo.v();
                        Vec2 vt = vts.get(vInfo.getTextureIndex());
                        Vec3 vn = vns.get(vInfo.getNormalIndex());
                        verticesArray[i * 3] = v.x;
                        verticesArray[i * 3 + 1] = v.y;
                        verticesArray[i * 3 + 2] = v.z;
                        texturesArray[i * 2] = vt.x; //u
                        texturesArray[i * 2 + 1] = 1 - vt.y; //flip v axis
                        normalsArray[i * 3] = vn.x;
                        normalsArray[i * 3 + 1] = vn.y;
                        normalsArray[i * 3 + 2] = vn.z;
                }
        }

	private static int[] convertIndicesListToArray(List<Integer> indices) {
		int[] indicesArray = new int[indices.size()];
		for (int i = 0; i < indicesArray.length; i++) {
			indicesArray[i] = indices.get(i);
		}
		return indicesArray;
	}
}