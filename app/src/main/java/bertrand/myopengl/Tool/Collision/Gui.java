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
                Vector<Integer> touchDetections,
                @NotNull SparseArray<Box.CircleCollider> circleColliders,
                @NotNull final SparseArray<Box.Location> guiLocations,
                @NotNull Vec2 touchPoint
        ) {
                touchDetections.clear();
                for (int i = 0; i < circleColliders.size(); i++) {
                        final Box.CircleCollider col = circleColliders.at(i);
                        final Box.Location  loc = guiLocations.atId(col.location_ID);
                        final Circle ci = new Circle(
                                Mathe.Tx(loc.MV),
                                Mathe.Ty(loc.MV),
                                col.radius
                        );
                        if (Tst.subset(ci,touchPoint)) {
                                touchDetections.add(col.location_ID);
                        }
                }
        }
}
