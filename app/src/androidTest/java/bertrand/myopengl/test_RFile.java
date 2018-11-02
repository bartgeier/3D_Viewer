package bertrand.myopengl;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import bertrand.myopengl.Tool.RFile.RFile;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class test_RFile {
        @Test
        public void notRescource() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream("raw/stall.obj");

                assertEquals(null, s);
        }

        @Test
        public void onlyDoublePoint() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":");

                assertEquals(null, s);
        }

        @Test
        public void onlyDoublePointFrontSlash() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/");

                assertEquals(null, s);
        }

        public void stall_0() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/stall");

                assertEquals(null, s);
        }

        @Test
        public void stall_1() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":raw/stall");

                assertEquals(null, s);
        }

        @Test
        public void autsch_dosent_exist() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/raw/autsch");

                assertEquals(null, s);
        }

        @Test
        public void stall_2() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/raw/stall");

                assertTrue(s != null);
        }

        @Test
        public void stall_3() {
                Context appContext = InstrumentationRegistry.getTargetContext();
                RFile f = new RFile(appContext);
                InputStream s = f.inputStream(":/raw/stall.obj");

                assertTrue(s != null);
        }
}
