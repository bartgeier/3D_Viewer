package bertrand.myopengl.Tool.RFile;

import android.content.Context;

import java.io.InputStream;
import java.util.ArrayList;

public class RFile implements RFile_IF{
        private Context context;

        public RFile(Context c) {
                context = c;
        }

        public InputStream inputStream(String path) {
                if (path.length() < 2) {
                        return null;
                }
                if(path.charAt(0) == ':' && path.charAt(1) == '/') { // :/  a resource file
                        String[] list = path.split(":/");
                        if (list.length > 0) {
                                return resource(list[1]);
                        } else {
                                return null;
                        }
                } else if(path.charAt(0) == ':') {
                        return null;
                }
                return null;
        }

        public String path(final String filePath){
                final String[] strList = filePath.split("/");
                ArrayList<String> s = new ArrayList<>();
                for (int idx = 0; idx < (strList.length - 1); idx++) {
                        s.add(strList[idx]);
                        s.add("/");
                }
                return s.toString();

        }

        private InputStream resource(String path) {
                if (path.length() == 0) {
                        return null;
                }
                String[] list = path.split("/");
                if (list.length != 2) {
                        return null;
                }
                String resType = list[0];
                String fileName = list[list.length - 1].split("\\.")[0];
                int id = context.getResources().getIdentifier(
                        fileName,
                        resType,
                        context.getPackageName()
                );
                if (id != 0) {
                        return context.getResources().openRawResource(id);
                } else {
                        return null;
                }
        }


}


//int id = context.getResources().getIdentifier("stall","raw", context.getPackageName());
//InputStream s = context.getResources().openRawResource(R.raw.stall);
