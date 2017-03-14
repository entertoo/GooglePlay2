package com.example.googleplay.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class FrameLayoutView extends FrameLayout {

    private float mDownX;
    private float mDownY;

    public FrameLayoutView(Context context) {
        super(context, null);
    }

    public FrameLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();

                float diffX = Math.abs(mDownX - moveX);
                float diffY = Math.abs(mDownY - moveY);

                if (diffX > diffY) {
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
