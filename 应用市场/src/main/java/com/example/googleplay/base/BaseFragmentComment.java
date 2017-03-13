package com.example.googleplay.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragmentComment extends Fragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		init();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		initData();
		initListener();
		super.onActivityCreated(savedInstanceState);
	}

	// 必须实现 初始化View 但是不知道具体实现，定义成抽象方法
	public abstract View initView();

	public void init()
	{

	}

	public void initData()
	{

	}

	public void initListener()
	{

	}

}
