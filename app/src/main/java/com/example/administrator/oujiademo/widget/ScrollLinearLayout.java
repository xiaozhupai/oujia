package com.example.administrator.oujiademo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 可以滑动的linearlayout
 */
public class ScrollLinearLayout extends LinearLayout {

    private static final String TAG = "ScrollLinearLayout";

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }

    private int down_y, move_y;
    private int move_size;
    private int i = 0;
    private int l, t, r, b;
    private boolean isFirst = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        if (isFirst) {
            l = getLeft();
            t = getTop();
            r = getRight();
            b = getBottom();
            isFirst = false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_y = y;
                Log.d(TAG, "onTouchEvent: ACTION_DOWN=" + down_y);
                break;
            case MotionEvent.ACTION_MOVE:
                move_y = y;
                Log.d(TAG, "onTouchEvent: ACTION_MOVE down_y=" + down_y);
                Log.d(TAG, "onTouchEvent: ACTION_MOVE move_y=" + move_y);
                Log.d(TAG, "onTouchEvent: getHeight=" + getHeight());
                move_size = move_y - down_y;
                if (move_size < 0) {
                    if ((move_size + down_y) > 0) {
                        i += move_size;
                    }
                } else {
                    i += move_size;
                }
                if (i >= 0) {
                    layout(getLeft(), getTop() + move_size, getRight(), getBottom() + move_size);
                } else {
                    layout(l, t, r, b);
                }
                Log.d(TAG, "onTouchEvent: ACTION_MOVE move_size=" + move_size);
                Log.d(TAG, "onTouchEvent: ACTION_MOVE i=" + i);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP=" + i);
                if (i > 0) {
                    layout(getLeft(), getTop() - i, getRight(), getBottom() - i);
                } else {
                    layout(l, t, r, b);
                }
                i = 0;
                move_size = 0;
                break;
        }
        return true;
    }
}
