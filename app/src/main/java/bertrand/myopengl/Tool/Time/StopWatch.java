package bertrand.myopengl.Tool.Time;

import android.os.SystemClock;

public class StopWatch {
        private long start_time;
        private float avarage = 0;

        public void start_ns() {
                start_time = SystemClock.elapsedRealtimeNanos();
        }

        /* use it with start_ns */
        /* return avarage in nano secounds */
        public int avarage_ns(int order) {
                final long measure = SystemClock.elapsedRealtimeNanos() - start_time;
                avarage = avarage - avarage/order + measure/order;
                return (int)avarage;
        }
}
