package bertrand.myopengl.Tool.Scene;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SceneParser {
        public static String[] names(@NotNull InputStream is) {
                InputStreamReader s = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(s);
                String line;
                try {
                        line = reader.readLine();
                        while (line != null) {
                                if (line.startsWith("m")) {
                                        String[] m = line.split(";");
                                        return Arrays.copyOfRange(m, 1, m.length);
                                }
                                line = reader.readLine();
                        }
                } catch (IOException e) {
                        System.err.println("SceneParser.names Error reading the file");
                }
                return null;
        }

        public static Hierarchy hierarchy(@NotNull InputStream is) {
                InputStreamReader s = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(s);
                String line;

                //List<HierarchyData> datas = new ArrayList<>();
                Hierarchy hierarchy = new Hierarchy();
                try {
                        line = reader.readLine();
                        while (line != null) {
                                if (line.startsWith("h")) {
                                        String[] l = line.split(";");
                                        int i = 1;
                                        HierarchyData h = new HierarchyData();
                                        h.idx = Integer.valueOf(l[i++]);
                                        h.model_idx = Integer.valueOf(l[i++]);
                                        i++;
                                        h.parent_idx = Integer.valueOf(l[i++]);
                                        i++;
                                        h.x = Float.valueOf(l[i++]);
                                        h.y = Float.valueOf(l[i++]);
                                        h.z = Float.valueOf(l[i++]);
                                        h.quatW = Float.valueOf(l[i++]);
                                        h.quatX = Float.valueOf(l[i++]);
                                        h.quatY = Float.valueOf(l[i++]);
                                        h.quatZ = Float.valueOf(l[i++]);
                                        h.scaleX = Float.valueOf(l[i++]);
                                        h.scaleY = Float.valueOf(l[i++]);
                                        h.scaleZ = Float.valueOf(l[i]);
                                        hierarchy.datas.add(h);
                                }
                                line = reader.readLine();
                        }
                } catch (IOException e) {
                        System.err.println("SceneParser.hierarchy Error reading the file");
                }
                return hierarchy;
        }
}
