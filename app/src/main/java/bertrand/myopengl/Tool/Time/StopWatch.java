package bertrand.myopengl.Tool.Time;

import android.os.SystemClock;
import android.util.Log;

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

        public void start_ms() {
                start_time = SystemClock.elapsedRealtime();
        }

        public int stop_ms() {
                final long measure = SystemClock.elapsedRealtime() - start_time;
                String s_0 = "time [ms]: " + measure;
                Log.i("Info", s_0);
                return (int) measure;
        }
}
