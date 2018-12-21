package bertrand.myopengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import bertrand.myopengl.Tool.RFile.RFile;
import bertrand.myopengl.Tool.RFile.RFile_IF;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class test_RFile {
        @Test
        public void notRescource() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream("raw/stall_obj.obj");

                assertNull(s);
        }

        @Test
        public void onlyDoublePoint() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":");

                assertNull(s);
        }

        @Test
        public void onlyDoublePointFrontSlash() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/");

                assertNull(s);
        }

        public void stall_0() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/stall_obj");

                assertNull(s);
        }

        @Test
        public void stall_1() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":raw/stall_obj");

                assertNull(s);
        }

        @Test
        public void autsch_dosent_exist() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/raw/autsch");

                assertNull(s);
        }

        @Test
        public void stall_2() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/raw/stall_obj");

                assertNotNull(s);
        }

        @Test
        public void stall_3() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/raw/stall_obj.obj");

                assertNotNull(s);
        }

        @Test
        public void stall_png() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile_IF f = new RFile(appContext);
                Bitmap s = f.bitMap(":/raw/stall_png.png");

                assertNotNull(s);
        }

}
