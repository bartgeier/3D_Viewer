package bertrand.myopengl.Tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Str {
        public static String inputStreamToString(InputStream is) {
                InputStreamReader s = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(s);
                String txt = "";
                try {
                        txt = reader.readLine();
                        if (txt == null) {
                                txt = "";
                        }
                } catch(Exception e){
                        return txt;

                }
                while(true) {
                        try {
                                String line = reader.readLine();
                                if (line == null) {
                                        break;
                                }
                                txt = txt + "\n" + line;
                        } catch(Exception e){
                                break;
                        }
                }
                return txt;
        }

}
