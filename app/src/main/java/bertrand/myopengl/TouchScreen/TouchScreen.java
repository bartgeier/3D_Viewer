package bertrand.myopengl.TouchScreen;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class TouchScreen implements ScaleGestureDetector.OnScaleGestureListener {
        private ScaleGestureDetector scaleGestureDetector;
        private boolean scaling_modus = false;

        public float scaleFactor;
        public boolean isScaling;

        public boolean isMoving;
        public float deltaX;
        public float deltaY;
        private float lastTouch_X;
        private float lastTouch_Y;
        private int pointerID;

        public TouchScreen(Context context) {
                this.scaleGestureDetector = new ScaleGestureDetector(context,this);
        }

        public void onTouchEvent(MotionEvent event) {
                isScaling = false;
                scaleGestureDetector.onTouchEvent(event);
                isMoving = false;
                switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN: {
                                /* start dragging */
                                scaling_modus = false;
                                final int pointerIndex = event.getActionIndex();
                                lastTouch_X = event.getX(pointerIndex);
                                lastTouch_Y = event.getY(pointerIndex);
                                pointerID = event.getPointerId(0);
                                break;
                        }
                        case MotionEvent.ACTION_MOVE: {
                                if (!scaling_modus) {
                                        final int pointerIndex = event.findPointerIndex(pointerID);
                                        final float x = event.getX(pointerIndex);
                                        final float y = event.getY(pointerIndex);
                                        deltaX = x - lastTouch_X;
                                        deltaY = y - lastTouch_Y;
                                        lastTouch_X = x;
                                        lastTouch_Y = y;
                                        isMoving = true;
                                }
                                break;
                        }
                        case MotionEvent.ACTION_UP: {
                                pointerID = INVALID_POINTER_ID;
                                break;
                        }
                        case MotionEvent.ACTION_POINTER_UP: {
                                final int pointerIndex = event.getActionIndex();
                                final int pointerId = event.getPointerId(pointerIndex);
                                if (pointerId == pointerID) {
                                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                                        lastTouch_X = event.getX(newPointerIndex);
                                        lastTouch_Y = event.getY(newPointerIndex);
                                        pointerID = event.getPointerId(newPointerIndex);
                                }
                                break;
                        }
                }
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
                scaleFactor = detector.getScaleFactor();
                isScaling = true;
                scaling_modus = true;
                return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
}
