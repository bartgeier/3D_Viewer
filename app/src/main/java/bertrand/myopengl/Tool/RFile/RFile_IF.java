package bertrand.myopengl.Tool.RFile;

import android.graphics.Bitmap;

import java.io.InputStream;

public interface RFile_IF {
        String string(String path);
        InputStream inputStream(String path);
        Bitmap bitMap(String path);
        String path(final String filePath);
}
