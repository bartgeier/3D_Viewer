package bertrand.myopengl;

import android.os.Message;

import android.os.Handler;


public class FrameMessageHandler extends Handler {
        public FrameMessageHandler(Handler.Callback callback) {
                super (callback);
        }

        public void sendMessage_FrameTimeUpdate(int time) {
                Message msg = obtainMessage();
                msg.what = 0;
                msg.arg1 = time;
                sendMessage(msg);
        }

        public void sendMessage_FrameRateUpdate(int time) {
                Message msg = obtainMessage();
                msg.what = 1;
                msg.arg1 = time;
                sendMessage(msg);
        }

}
