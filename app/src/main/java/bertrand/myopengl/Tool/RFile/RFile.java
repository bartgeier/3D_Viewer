package bertrand.myopengl.Tool.RFile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.ArrayList;

import bertrand.myopengl.Tool.Str;

public class RFile implements RFile_IF{
        private Context context;

        public RFile(Context c) {
                context = c;
        }

        public String string(String path) {
                return Str.inputStreamToString(inputStream(path));
        }

        public InputStream inputStream(String path) {
                if (isResource(path)) {
                        return resInputStream(resourceID(path));
                } else {
                        return null;
                }
        }

        public Bitmap bitMap(String path) {
               if (isResource(path)) {
                       return resBitmap(resourceID(path));
               } else {
                       return null;
               }
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

        private InputStream resInputStream(int id) {
                if (id != 0) {
                        return context.getResources().openRawResource(id);
                } else {
                        return null;
                }
        }

        private final Bitmap resBitmap(int id) {
                if (id != 0) {
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inScaled = false;   // No pre-scaling
                        return BitmapFactory.decodeResource(context.getResources(), id, options);
                } else {
                        return null;
                }
        }


        private static boolean isResource(final String path) {
                String[] list;
                if (path.length() >= 2) {
                        list = path.split("/");
                } else {
                        return false;
                }

                return ( list.length == 3 &&
                        path.charAt(0) == ':' && path.charAt(1) == '/');
        }

        private int resourceID(final String resPath) {
                String[] list = resPath.split("/");
                String resType = list[1];
                String fileName = list[list.length - 1].split("\\.")[0];
                return context.getResources().getIdentifier(
                        fileName,
                        resType,
                        context.getPackageName()
                );
        }


}


//int id = context.getResources().getIdentifier("stall_obj","raw", context.getPackageName());
//InputStream s = context.getResources().openRawResource(R.raw.stall_obj);
