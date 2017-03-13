package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class AppDetailDesHolder extends BaseHolder<AppInfoBean>
{
	private AppInfoBean mData;
	private boolean isOpen = true;
	private int mMeasuredHeight;

	@ViewInject(R.id.app_detail_des_tv_des)
	TextView mTvDes;

	@ViewInject(R.id.app_detail_des_tv_author)
	TextView mTvAuthor;

	@ViewInject(R.id.app_detail_des_iv_arrow)
	ImageView mIvArrow;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_des, null);
		ViewUtils.inject(this, view);

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle(true);
			}
		});
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data) {
		mData = data;

		mTvDes.setText(data.des);
		mTvAuthor.setText(data.author);

		// 监听mTvDes开始的高度
		mTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				// 获取开始时view的高度
				mMeasuredHeight = mTvDes.getMeasuredHeight();
				// 默认打开的时候不展开
				toggle(false);
				// 如果不移除,一会高度变成7行的时候.mTvDesMeasuredHeight就会变
				mTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});

	}

	private void toggle(Boolean isAnimation) {
		if (isOpen) {
			int start = mMeasuredHeight;
			int end = shortHeight();

			if (isAnimation) {
				doAnimation(start, end);

				// 箭头
				ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvArrow, "rotation", 180, 0);
				objectAnimator.start();
			} else {
				// 如果不需要动画，直接设置高度
				mTvDes.setHeight(end);
			}
		} else {
			int end = mMeasuredHeight;
			int start = shortHeight();

			doAnimation(start, end);

			// 箭头
			ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180);
			objectAnimator.start();

		}
		isOpen = !isOpen;
	}

	public void doAnimation(int start, int end) {
		// 渐变动画
		ObjectAnimator animator = ObjectAnimator.ofInt(mTvDes, "height", start, end);
		animator.setDuration(100);
		animator.start();
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0)// 动画开始
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator arg0)// 动画重复
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator arg0)// 动画结束
			{
				ViewParent parent = mTvDes.getParent();
				while (true) {
					parent = parent.getParent();
					if (parent == null) {
						break;
					} else if (parent instanceof ScrollView) {
						// scrollView.fullScroll(ScrollView.FOCUS_DOWN);滚动到底部
						((ScrollView) parent).fullScroll(ScrollView.FOCUS_DOWN);
						break;
					}
				}
			}

			@Override
			public void onAnimationCancel(Animator arg0)// 动画取消
			{
				// TODO Auto-generated method stub

			}
		});
	}

	// 获取折叠时的最短高度
	private int shortHeight() {
		TextView tmpTextView = new TextView(UIUtils.getContext());
		tmpTextView.setLines(7);
		tmpTextView.setText(mData.des);

		// int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0,
		// MeasureSpec.UNSPECIFIED);
		// int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
		// MeasureSpec.UNSPECIFIED);
		// tmpTextView.measure(widthMeasureSpec, heightMeasureSpec);

		// 测量,就是让系统底层去测量这个view的宽高.
		tmpTextView.measure(0, 0);
		int measuredHeight = tmpTextView.getMeasuredHeight();

		return measuredHeight;
	}

}
