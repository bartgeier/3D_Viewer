package bertrand.myopengl;

import org.junit.Test;

import java.util.Vector;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Tool.Gui.Gui;

import static org.junit.Assert.assertEquals;

public class test_Gui {
        @Test
        public void test_reduseTabActions() {
                final Vector<Box.Tab> tabs = new Vector<>(5,1);

                int startIdx = 0;
                tabs.add(new Box.Tab(0,0,1));
                tabs.add(new Box.Tab(1,0,1));
                tabs.add(new Box.Tab(3,0,1));
                tabs.add(new Box.Tab(2,0,1));
                Gui.reduseTabActions(tabs,startIdx);
                assertEquals(1, tabs.size());
                assertEquals(3, tabs.get(0).layer);
                tabs.clear();
                assertEquals(0, tabs.size());

                startIdx = 1;
                tabs.add(new Box.Tab(0,0,1));
                tabs.add(new Box.Tab(1,0,1));
                tabs.add(new Box.Tab(3,0,1));
                tabs.add(new Box.Tab(2,0,1));
                Gui.reduseTabActions(tabs,startIdx);
                assertEquals(2, tabs.size());
                assertEquals(0, tabs.get(0).layer);
                assertEquals(3, tabs.get(1).layer);
                tabs.clear();

                startIdx = 0;
                Gui.reduseTabActions(tabs,startIdx);
                assertEquals(0, tabs.size());
                tabs.clear();

        }
}
