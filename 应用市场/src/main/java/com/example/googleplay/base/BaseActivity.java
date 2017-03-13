package com.example.googleplay.base;

import java.util.LinkedList;
import java.util.List;

import com.example.googleplay.activity.MainActivity;
import com.example.googleplay.utils.UIUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * 公共属性
 * 公共方法
 */
public abstract class BaseActivity extends ActionBarActivity
{
	private List<ActionBarActivity> activites = new LinkedList<ActionBarActivity>();
	private long mPreTime;

	// 最上层的Activity
	private Activity mCurrentActivity;

	public Activity getCurrentActivity()
	{
		return mCurrentActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 初始化传递的参数等等
		init();

		// 初始化View
		initView();

		// 初始ActionBar
		initActionBar();

		// 初始化Data
		initData();

		// 初始化监听器
		initListener();

		activites.add(this);
	}

	@Override
	protected void onDestroy()
	{
		activites.remove(this);
		super.onDestroy();
	}

	/** 完全退出 */
	public void exit()
	{
		// 结束所有的activity
		for (ActionBarActivity activity : activites)
		{
			activity.finish();
		}
	}

	/** 获取焦点的时候保存当前Activity */
	@Override
	protected void onResume()
	{
		mCurrentActivity = this;
		super.onResume();
	}

	/** 连续双击退出 */
	@Override
	public void onBackPressed()
	{
		if (this instanceof MainActivity)
		{
			// 如果两次操作时间超过2秒
			if ((System.currentTimeMillis() - mPreTime) > 2000)
			{
				Toast.makeText(UIUtils.getContext(), "再点击一次退出", Toast.LENGTH_SHORT).show();
				mPreTime = System.currentTimeMillis();
				return;
			}
		}
		super.onBackPressed();// finish();
	}

	/** 初始化传递的参数等等 */
	public void init()
	{
		// TODO Auto-generated method stub

	}

	/** 初始化视图，必须实现的方法不知道具体实现，让其子类实现 */
	public abstract void initView();

	/** 初始化ActionBar */
	public void initActionBar()
	{
		// TODO Auto-generated method stub

	}

	/** 初始化数据 */
	public void initData()
	{
		// TODO Auto-generated method stub

	}

	/** 设置监听器 */
	public void initListener()
	{
		// TODO Auto-generated method stub

	}
}
