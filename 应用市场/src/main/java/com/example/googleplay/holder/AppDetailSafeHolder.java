package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.bean.AppInfoBean.SafeInfoBean;
import com.example.googleplay.conf.Constants.URLS;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppDetailSafeHolder extends BaseHolder<AppInfoBean> implements OnClickListener
{
	Boolean isOpen = true;

	@ViewInject(R.id.app_detail_safe_pic_container)
	LinearLayout mPicContainer;

	@ViewInject(R.id.app_detail_safe_iv_arrow)
	ImageView mIvArrow;

	@ViewInject(R.id.app_detail_safe_des_container)
	LinearLayout mDesContainer;

	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_safe, null);
		ViewUtils.inject(this, view);

		view.setOnClickListener(this);

		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data)
	{
		for (int i = 0; i < data.safe.size(); i++)
		{
			// 获取数据
			SafeInfoBean safeInfoBean = data.safe.get(i);

			// 设置安全图标
			ImageView ivIcon = new ImageView(UIUtils.getContext());
			String safeUri = URLS.IMAGEBASEURL + safeInfoBean.safeUrl;
			BitmapHelper.display(ivIcon, safeUri);
			// 加入容器
			mPicContainer.addView(ivIcon);

			// 线性布局，把安全描述包裹起来
			LinearLayout ll = new LinearLayout(UIUtils.getContext());
			ll.setOrientation(LinearLayout.HORIZONTAL);
			ll.setPadding(7, 5, 7, 5);

			// 安全描述图标
			ImageView ivDes = new ImageView(UIUtils.getContext());
			String safeDesUri = URLS.IMAGEBASEURL + safeInfoBean.safeDesUrl;
			BitmapHelper.display(ivDes, safeDesUri);

			// 安全描述内容
			TextView tvDes = new TextView(UIUtils.getContext());
			tvDes.setText(safeInfoBean.safeDes);
			tvDes.setGravity(Gravity.CENTER_HORIZONTAL);
			// 设置不同类型的文字
			if (safeInfoBean.safeDesColor == 0)
			{
				tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
			}
			else
			{
				tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
			}

			// 加入容器
			ll.addView(ivDes);
			ll.addView(tvDes);

			mDesContainer.addView(ll);
		}
		// 打开时默认折叠无动画
		toggle(false);
	}

	@Override
	public void onClick(View v)
	{
		// 点击有动画
		toggle(true);

	}

	private void toggle(Boolean isAnimation)
	{
		if (isOpen)// 如果是展开就开始折叠
		{
			/**
			 * 等同 mDesContainer.measure(0, 0); 
			 * int widthMeasureSpec =MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED); 
			 * int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED); 
			 * mDesContainer.measure(widthMeasureSpec, heightMeasureSpec);
			 */
			mDesContainer.measure(0, 0);
			// 获取当前高度
			int measuredHeight = mDesContainer.getMeasuredHeight();
			// 动画开始的高度
			int start = measuredHeight;
			// 动画结束的高度
			int end = 0;
			if (isAnimation)
			{
				// 需要动画的时候
				doAnimation(start, end);

				// 箭头动画
				ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvArrow, "rotation", 180, 0);
				objectAnimator.start();
			}
			else
			{
				// 不需要动画的时候直接设置高度
				LayoutParams layoutParams = mDesContainer.getLayoutParams();
				layoutParams.height = end;
				mDesContainer.setLayoutParams(layoutParams);
			}
		}
		else// 如果是折叠就开始展开
		{
			mDesContainer.measure(0, 0);
			// 获取当前高度
			int measuredHeight = mDesContainer.getMeasuredHeight();
			int end = measuredHeight;
			int start = 0;
			doAnimation(start, end);

			// 箭头动画
			ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180);
			objectAnimator.start();
		}
		isOpen = !isOpen;
	}
	
	/**
	 * 制造一个渐变动画，根据渐变动画的动态高度变化来设置容器的高度变化
	 */
	public void doAnimation(int start, int end)
	{
		// 渐变动画，高度从start-end
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.setDuration(300);
		// 开始动画
		animator.start();
		// 监听回调渐变高度，根据渐变高度修改并设置容器高度
		animator.addUpdateListener(new AnimatorUpdateListener()
		{

			@Override
			public void onAnimationUpdate(ValueAnimator value)
			{
				// 从动画中获取渐变高度值
				int height = (Integer) value.getAnimatedValue();
				// 根据动画中获取渐变高度值修改容器高度
				LayoutParams layoutParams = mDesContainer.getLayoutParams();
				layoutParams.height = height;
				mDesContainer.setLayoutParams(layoutParams);
			}
		});
	}

}
