package com.example.googleplay.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.googleplay.activity.DetailActivity;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.protocol.RecommendProtocol;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.views.flyoutin.ShakeListener;
import com.example.googleplay.views.flyoutin.ShakeListener.OnShakeListener;
import com.example.googleplay.views.flyoutin.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * 推荐
 * @author haopi
 *
 */
public class RecommendFragment extends BaseFragment
{

	private List<String> mData;
	private ShakeListener mShakeListener;

	@Override
	public LoadedResult initData() {
		RecommendProtocol mRecommendProtocol = new RecommendProtocol();
		try {
			mData = mRecommendProtocol.loadData(0);
			return checkState(mData);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
		final RecommendAdapter adapter = new RecommendAdapter();
		stellarMap.setAdapter(adapter);
		// 设置内边距
		stellarMap.setInnerPadding(10, 10, 10, 10);
		// 设置第一页的时候显示
		stellarMap.setGroup(0, true);

		// 设置把屏幕拆分成多个格子
		stellarMap.setRegularity(15, 20);
		// 手动抖动事件
		mShakeListener = new ShakeListener(UIUtils.getContext());
		// 设置抖动监听
		mShakeListener.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake() {
				// 获取当前的组
				int groupIndex = stellarMap.getCurrentGroup();
				// 如果当前组为最后一组，摇一摇时设置到第一组去
				if (stellarMap.getCurrentGroup() == adapter.getGroupCount() - 1) {
					groupIndex = 0;
				} else {
					// 如果不是最后一组，摇一摇时设置到下一组去
					groupIndex++;
				}
				// 设置摇一摇时组的位置
				stellarMap.setGroup(groupIndex, true);
			}
		});

		return stellarMap;
	}

	@Override
	public void onResume() {
		if (mShakeListener != null) {
			mShakeListener.resume();
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		if (mShakeListener != null) {
			mShakeListener.pause();
		}
		super.onPause();
	}

	// 适配器继承Stellar中的Adapter接口
	private class RecommendAdapter implements StellarMap.Adapter
	{

		// 每页展示的个数
		private static final int PAGER_SIZE = 15;

		@Override
		public int getGroupCount()// 返回组数
		{
			int groupCount = mData.size() / PAGER_SIZE;
			if (mData.size() % PAGER_SIZE > 0)// 有余数
			{
				groupCount++;
			}
			return groupCount;
		}

		@Override
		public int getCount(int group)// 返回每组多少条数据
		{
			// 如果是最后一组
			if (group == getGroupCount() - 1) {
				// 如果有余数，就返回余数
				if (mData.size() % PAGER_SIZE > 0) {
					return mData.size() % PAGER_SIZE;
				}
			}
			return PAGER_SIZE;
		}

		@Override
		public View getView(int group, int position, View convertView) {
			// 把数据放在Textview中
			TextView tv = new TextView(UIUtils.getContext());

			// group代表第几组
			// position代表几组的第几个位置
			final int index = group * PAGER_SIZE + position;

			tv.setText(mData.get(index));

			Random random = new Random();

			// 随机的颜色
			int alpha = random.nextInt(180) + 30;
			int red = random.nextInt(180) + 30;
			int green = random.nextInt(180) + 30;
			int blue = random.nextInt(180) + 30;
			int argb = Color.argb(alpha, red, green, blue);
			tv.setTextColor(argb);
			// 随机的大小
			tv.setTextSize(random.nextInt(8) + 15); // 10 - 16

			// 设置点击事件
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String appName = mData.get(index);
					goToDetailActivity(appName);
				}
			});

			return tv;
		}

		@Override
		public int getNextGroupOnPan(int group, float degree) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	private void goToDetailActivity(String packageName) {
		Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("packageName", packageName);
		UIUtils.getContext().startActivity(intent);
	}
}
