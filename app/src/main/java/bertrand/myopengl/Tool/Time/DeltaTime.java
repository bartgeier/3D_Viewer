package bertrand.myopengl.Tool.Time;

import android.os.SystemClock;

public class DeltaTime {
        private double lasttime = 0;
        public float dt() {
                final double time = SystemClock.elapsedRealtime();
                float delta;
                if (lasttime == 0) {
                        delta = 0;
                } else {
                        delta = (float)(time - lasttime);
                }
                lasttime = time;
                return delta;
        }
}
