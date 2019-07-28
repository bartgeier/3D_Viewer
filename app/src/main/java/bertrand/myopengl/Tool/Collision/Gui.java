package bertrand.myopengl.Tool.Collision;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.Tool.Circle;
import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.SparseArray.SparseArray;
import bertrand.myopengl.Tool.Tst;
import bertrand.myopengl.Tool.Vec2;


public class Gui {
        static public void circleCollision(
                @NotNull Vector<Box.Tab> tabs,
                @NotNull SparseArray<Box.CircleCollider> circleColliders,
                @NotNull final SparseArray<Box.Location> guiLocations,
                @NotNull Vec2 touchPoint
        ) {
                final int idx = tabs.size();
                for (int i = 0; i < circleColliders.size(); i++) {
                        final Box.Tab t = circleColliders.at(i).tab;
                        final Box.Location  loc = guiLocations.atId(t.guiLocation_ID);
                        final Circle ci = new Circle(
                                Mathe.Tx(loc.MV),
                                Mathe.Ty(loc.MV),
                                circleColliders.at(i).radius
                        );
                        if (Tst.subset(ci,touchPoint)) {
                                tabs.insertElementAt(t,idx);
                        }
                }
        }

        static public void reduseTabActions(
                @NotNull final Vector<Box.Tab> tabs,
                final int start_idx
        ) {
                int i = start_idx;
                while (i < tabs.size()) {
                        if (tabs.get(start_idx).layer < tabs.get(i).layer) {
                                tabs.removeElementAt(start_idx);
                        } else if (tabs.get(start_idx).layer > tabs.get(i).layer) {
                                tabs.removeElementAt(i);
                        } else if (start_idx != i) {
                                tabs.removeElementAt(start_idx);
                        } else {
                                i++;
                        }
                }
        }

        static public void noTabActionOnIdx(
                @NotNull final Vector<Box.Tab> tabs,
                final int idx
        ) {
                if(tabs.size() == idx) {
                        tabs.add(new Box.Tab(0, 0, 0));
                }
        }

        static public void excutePressTabAction(
                @NotNull final SparseArray<Box.Location> guiLocations,
                @NotNull final SparseArray<Box.TabAction> actions,
                @NotNull final Vector<Box.Tab> tabs
        ) {
                for (Box.Tab  tab: tabs) {
                        Box.Location guiLocation = guiLocations.atId(tab.guiLocation_ID);
                        Box.TabAction action = actions.atId(tab.tabAction_ID);
                        guiLocation.texId = action.texNo_press;
                        Box.TabAction.Function_IF x =  actions.atId(tab.tabAction_ID).press;
                        x.f(tab);
                }
        }

        static public void excuteReleaseTabAction(
                @NotNull final SparseArray<Box.Location> guiLocations,
                @NotNull final SparseArray<Box.TabAction> actions,
                @NotNull final Vector<Box.Tab> tabs
        ) {
                for (Box.Tab  tab: tabs) {
                        Box.Location guiLocation = guiLocations.atId(tab.guiLocation_ID);
                        Box.TabAction action = actions.atId(tab.tabAction_ID);
                        if (guiLocation.texId == action.texNo_press) {
                                Box.TabAction.Function_IF x = actions.atId(tab.tabAction_ID).release;
                                x.f(tab);
                        }
                        guiLocation.texId = action.texNo_release;
                }
        }

        static public void excuteChangeTabAction(
                @NotNull final SparseArray<Box.Location> guiLocations,
                @NotNull final SparseArray<Box.TabAction> actions,
                @NotNull final Vector<Box.Tab> tabs,
                final Box.Touch touch
        ) {
                if (tabs.size() != 2) {
                        throw new AssertionError(
                                "Gui.excuteChangeTabAction, tabs.size() != 2");
                }
                Box.Location guiLocation;
                Box.TabAction action;
                Box.TabAction.Function_IF x;
                Box.Tab  last =  tabs.get(0);
                Box.Tab  actual =  tabs.get(1);
                if ( last.layer < actual.layer) {
                        // collider entry //
                        guiLocation = guiLocations.atId(actual.guiLocation_ID);
                        action = actions.atId(actual.tabAction_ID);
                        guiLocation.texId = action.texNo_hover;
                        x =  action.entry;
                        x.f(actual);
                } else if ( last.layer > actual.layer) {
                        // collider exit //
                        guiLocation = guiLocations.atId(last.guiLocation_ID);
                        action = actions.atId(last.tabAction_ID);
                        guiLocation.texId = action.texNo_release;
                        x =  action.exit;
                        x.f(last);
                } else {
                        if (actual.layer != 0) {
                                action = actions.atId(actual.tabAction_ID);
                                Box.TabAction.Change_IF change = action.change;
                                change.f(actual, touch);
                        }
                }

        }
}
