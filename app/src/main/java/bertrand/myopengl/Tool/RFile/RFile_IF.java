package bertrand.myopengl.Tool.RFile;

import android.graphics.Bitmap;

import java.io.InputStream;

public interface RFile_IF {
        public InputStream inputStream(String path);
        public Bitmap bitMap(String path);
        public String path(final String filePath);
}
