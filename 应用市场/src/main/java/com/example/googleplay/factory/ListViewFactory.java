package com.example.googleplay.factory;

import com.example.googleplay.utils.UIUtils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

public class ListViewFactory
{
	public static ListView createListView()
	{
		ListView listView = new ListView(UIUtils.getContext());
		
		//简单设置
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);
		//设置选中透明，去掉蓝色背景
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		return listView;
	}
}
