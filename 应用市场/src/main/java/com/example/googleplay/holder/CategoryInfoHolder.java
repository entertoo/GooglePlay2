package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.bean.CategoryInfoBean;
import com.example.googleplay.conf.Constants.URLS;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.StringUtils;
import com.example.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryInfoHolder extends BaseHolder<CategoryInfoBean>
{
	@ViewInject(R.id.item_category_item_1)
	LinearLayout mContainerItem1;
	@ViewInject(R.id.item_category_item_2)
	LinearLayout mContainerItem2;
	@ViewInject(R.id.item_category_item_3)
	LinearLayout mContainerItem3;
	
	@ViewInject(R.id.item_category_icon_1)
	ImageView mIvIcon1;
	@ViewInject(R.id.item_category_icon_2)
	ImageView mIvIcon2;
	@ViewInject(R.id.item_category_icon_3)
	ImageView mIvIcon3;
	
	@ViewInject(R.id.item_category_name_1)
	TextView mTvName1;
	@ViewInject(R.id.item_category_name_2)
	TextView mTvName2;
	@ViewInject(R.id.item_category_name_3)
	TextView mTvName3;
	
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_category_info, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(CategoryInfoBean data)
	{
		setData(data.name1, data.url1, mTvName1, mIvIcon1);
		setData(data.name2, data.url2, mTvName2, mIvIcon2);
		setData(data.name3, data.url3, mTvName3, mIvIcon3);
	}

	public void setData(final String name, String url, TextView mTvName, ImageView mIvIcon)
	{
		if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(url))
		{
			mTvName.setText(name);
			mIvIcon.setImageResource(R.drawable.ic_default);
			BitmapHelper.display(mIvIcon, URLS.IMAGE_BASE_URL + url);
			
			((ViewGroup)mTvName.getParent()).setVisibility(View.VISIBLE);
			
			// 点击分类
			((ViewGroup)mTvName.getParent()).setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					Toast.makeText(UIUtils.getContext(), name, Toast.LENGTH_SHORT).show();
				}
			});
		}
		else
		{
			// 如果都为空，设置父容器隐藏
			((ViewGroup)mTvName.getParent()).setVisibility(View.INVISIBLE);
		}
	}

}
