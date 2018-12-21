package bertrand.myopengl;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import bertrand.myopengl.Tool.Str;

import static org.junit.Assert.*;

public class test_Str {
        @Test
        public void inputStreamToString_0() {
                String example = "Hallo";
                InputStream is = new ByteArrayInputStream(example.getBytes(StandardCharsets.UTF_8));
                String s = Str.inputStreamToString(is);
                assertTrue(s.equals( "Hallo"));
        }

        @Test
        public void inputStreamToString_lineFeed_at_end() {
                String example = "Hallo\n";
                InputStream is = new ByteArrayInputStream(example.getBytes(StandardCharsets.UTF_8));
                String s = Str.inputStreamToString(is);
                assertTrue(s.equals("Hallo"));
        }

        @Test
        public void inputStreamToString_lineFeeds() {
                String example = "Hallo\n\n\nWelt";
                InputStream is = new ByteArrayInputStream(example.getBytes(StandardCharsets.UTF_8));
                String s = Str.inputStreamToString(is);
                assertTrue(s.equals("Hallo\n\n\nWelt"));
        }

        @Test
        public void inputStreamToString_lineFeed() {
                String example = "Hallo\nWorld";
                InputStream is = new ByteArrayInputStream(example.getBytes(StandardCharsets.UTF_8));
                String s = Str.inputStreamToString(is);
                assertTrue(s.equals("Hallo\nWorld"));
        }

        @Test
        public void inputStreamToString_empty() {
                String example = "";
                InputStream is = new ByteArrayInputStream(example.getBytes(StandardCharsets.UTF_8));
                String s = Str.inputStreamToString(is);
                assertTrue(s.equals(""));
        }


}
