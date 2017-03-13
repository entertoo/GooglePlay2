package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.utils.UIUtils;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MenuHolder extends BaseHolder<Object>
{
	DrawerLayout drawerLayout;
	public MenuHolder(DrawerLayout drawerLayout) {
		this.drawerLayout = drawerLayout;
	}

	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
		
		RelativeLayout rlhome = (RelativeLayout) view.findViewById(R.id.home_layout);
		
		// 点击左侧菜单首页
		rlhome.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(UIUtils.getContext(), "这是首页", Toast.LENGTH_SHORT).show();
				//drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
			}
		});
		
		return view;
	}

	@Override
	public void refreshHolderView(Object data)
	{
		
	}

}
