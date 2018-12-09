package bertrand.myopengl.Tool;

import android.graphics.Bitmap;
import bertrand.myopengl.Tool.RFile.RFile_IF;

public class Texture_File_Loader {
        public static Bitmap getBitmap(RFile_IF file, String pngFilePath) {
                final Bitmap bitmap = file.bitMap(pngFilePath);
                return bitmap;
        }
}
                  
                  
                  
                  
                  
                  
            