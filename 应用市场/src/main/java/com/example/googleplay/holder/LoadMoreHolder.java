package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.LinearLayout;

public class LoadMoreHolder extends BaseHolder<Integer>
{
	@ViewInject(R.id.item_loadmore_container_loading)
	LinearLayout mContainerLoading;
	
	@ViewInject(R.id.item_loadmore_container_retry)
	LinearLayout mContainerRetry;
	
	public static final int STATE_LOADING = 0;
	public static final int STATE_RETRY = 1;
	public static final int STATE_NONE = 2;
	
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
		//注入
		ViewUtils.inject(this, view);
		
		return view;
	}

	@Override
	public void refreshHolderView(Integer state)
	{
		mContainerLoading.setVisibility(8);
		mContainerRetry.setVisibility(8);
		switch (state)
		{
		case STATE_LOADING:
			mContainerLoading.setVisibility(0);
			break;
		case STATE_RETRY:
			mContainerRetry.setVisibility(0);
			break;
		case STATE_NONE:
			
			break;

		default:
			break;
		}
	}
}
