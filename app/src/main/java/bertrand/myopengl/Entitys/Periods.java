package bertrand.myopengl.Entitys;


import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;


public class Periods {
        public SparseArray<Box.Periode> array = new SparseArray<>();
        public void addEntity(int entity_ID) {
                Box.Periode l = new Box.Periode(
                        entity_ID,
                        Box.Periode.Type.UNDEF,
                        0,
                        0
                );
                array.append(entity_ID, l);
        }

}
