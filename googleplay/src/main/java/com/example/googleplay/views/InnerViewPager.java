package com.example.googleplay.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 处理图片轮播时点击滑动处理
 *
 * @author haopi
 */
public class InnerViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;
    private float mMoveX;
    private float mMoveY;

    public InnerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        // 上下滑动，父亲处理
        // 左右滑动，自己处理
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取按下时的位置
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取移动中的位置
                mMoveX = ev.getRawX();
                mMoveY = ev.getRawY();

                // 获取移动位置的差
                int diffX = (int) (mDownX - mMoveX);
                int diffY = (int) (mDownY - mMoveY);

                // 如果是左右滑动的位置差 > 上下滑动的位置差，就视为左右滑动，否则为上下滑动
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    // 左右滑动，自己处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    // 上下滑动，父亲处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 按钮弹起的逻辑
                break;

            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		// 上下滑动，父亲处理
//		// 左右滑动，自己处理
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			// 获取按下时的位置
//			mDownX = ev.getRawX();
//			mDownY = ev.getRawY();
//			break;
//		case MotionEvent.ACTION_MOVE:
//			// 获取移动中的位置
//			mMoveX = ev.getRawX();
//			mMoveY = ev.getRawY();
//
//			// 获取移动位置的差
//			int diffX = (int) (mDownX - mMoveX);
//			int diffY = (int) (mDownY - mMoveY);
//
//			// 如果是左右滑动的位置差 > 上下滑动的位置差，就视为左右滑动，否则为上下滑动
//			if (Math.abs(diffX) > Math.abs(diffY)) {
//				// 左右滑动，自己处理
//				getParent().requestDisallowInterceptTouchEvent(true);
//			} else {
//				// 上下滑动，父亲处理
//				getParent().requestDisallowInterceptTouchEvent(false);
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			// 按钮弹起的逻辑
//			break;
//
//		default:
//			break;
//		}
//
//		return super.onTouchEvent(ev);
//	}

}
