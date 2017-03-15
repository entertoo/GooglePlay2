package com.example.googleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.conf.Constants.URLS;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片轮播
 * 
 * @author haopi
 *
 */
public class PictureHolder extends BaseHolder<List<String>> {

	@BindView(R.id.item_home_picture_pager) ViewPager mViewPager;
	@BindView(R.id.item_home_picture_container_indicator) LinearLayout mContainerIndicator;

	private List<String> mData;
	public AutoScrollTask mAutoScrollTask;

	public AutoScrollTask getAutoScrollTask() {
		if (mAutoScrollTask == null) {
			mAutoScrollTask = new AutoScrollTask();
		}
		return mAutoScrollTask;
	}

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(List<String> data) {
		mData = data;
		PictureAdapter adapter = new PictureAdapter();
		mViewPager.setAdapter(adapter);
		// 创建点点并加入线性容器
		createIndicatorView();
		// 给轮播图片设置点击事件
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// 实现图片轮播能够循环，防止越界
				position = position % mData.size();
				// 设置点点被点击与未被点击时的背景
				for (int i = 0; i < mData.size(); i++) {
					// 通过父容器mContainerIndicator获取儿子点点indicatorView
					View indicatorView = mContainerIndicator.getChildAt(i);
					// 当当前位置是被选中位置时，设置点点的背景为选中状态，否则设置点点的背景为默认颜色
					indicatorView.setBackgroundResource((i == position) ? R.drawable.indicator_selected : R.drawable.indicator_normal);
				}
			}

			// 当ViewPager滚动时的回调方法，只要手指动一下就会调用
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				
			}

			// 回调方法，当ViewPager滑动状态改变时的状态
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});

		int curItem = Integer.MAX_VALUE / 2;
		// 得到余数
		int diff = curItem % mData.size();
		// 设置第一次默认轮播图片为第一张图片
		mViewPager.setCurrentItem(curItem - diff);

		// 图片自动轮播任务
		mAutoScrollTask = getAutoScrollTask();
		mAutoScrollTask.start();

		// 用户触碰轮播图的时候停止播放
		mViewPager.setOnTouchListener(new OnTouchListener() {

			private float mDownX;
			private float mDownY;
			private long mDownTime;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mDownX = event.getX();
					mDownY = event.getY();
					mDownTime = System.currentTimeMillis();
					// 按下的时候停止图片轮播
					mAutoScrollTask.stop();
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_CANCEL:
					mAutoScrollTask.start();
					break;
				case MotionEvent.ACTION_UP:
					float upX = event.getX();
					float upY = event.getY();
					// 制作一个点击事件
					if (upX == mDownX && upY == mDownY) {
						long upTime = System.currentTimeMillis();
						if (upTime - mDownTime < 500) {
							//点击
							lunboPicClick(mViewPager.getCurrentItem() % mData.size());
						}
					}
					// 按下弹起的时候开始轮播
					mAutoScrollTask.start();
					break;

				default:
					break;
				}
				return false;
			}
		});
	}
	
	private void lunboPicClick(int item) {
		Toast.makeText(UIUtils.getContext(), "点击了第" + (item + 1)+ "张图片", Toast.LENGTH_SHORT).show();
	}

	/** 创建点点并加入线性容器 */
	private void createIndicatorView() {
		// 点点
		View indicatorView;
		// 根据数据的个数创建点点
		for (int i = 0; i < mData.size(); i++) {
			// 创建点点
			indicatorView = new View(UIUtils.getContext());
			// 设置点点背景
			indicatorView.setBackgroundResource(R.drawable.indicator_normal);
			// 设置点点宽高
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtils.dip2px(5), UIUtils.dip2px(5));
			// 设置点点的左边距
			layoutParams.leftMargin = UIUtils.dip2px(5);
			// 设置点点的下边距
			layoutParams.bottomMargin = UIUtils.dip2px(5);

			// 设置第一个图片的点点为选中背景
			if (i == 0) {
				indicatorView.setBackgroundResource(R.drawable.indicator_selected);
			}
			// 在容器中加入点点
			mContainerIndicator.addView(indicatorView, layoutParams);
		}
	}

	/** 自动轮播图片 */
	public class AutoScrollTask implements Runnable {
		
		// 开始轮播
		public void start() {
			// 延迟1.5秒后进入this.run()方法，进行递归
			UIUtils.postTaskDelay(this, 1500);
		}

		// 结束轮播
		public void stop() {
			UIUtils.postTaskAndMessagesRemove(null);
		}

		@Override
		public void run() {
			// 获取当前currentItem
			int currentItem = mViewPager.getCurrentItem();
			currentItem++;
			// 设置当前轮播的图片+1
			mViewPager.setCurrentItem(currentItem);
			// 结束-->再次开始,递归
			this.start();
		}
	}

	/** 继承PagerAdapter */
	private class PictureAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			if (mData != null) {
				// return mData.size();
				return Integer.MAX_VALUE;
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 实现图片轮播能够循环，防止越界
			position = position % mData.size();
			ImageView iv = new ImageView(UIUtils.getContext());
			// 使用 FILL 方式缩放图像
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.ic_default);
			// 获取图片完整路径
			String uri = URLS.IMAGE_BASE_URL + mData.get(position);
			BitmapHelper.display(iv, uri);
			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

}
