package com.example.googleplay.base;

import android.view.View;

/**
 * 不仅刷新视图，而且设置数据
 * @author haopi
 *
 * @param <HolderBeanType>
 */
public abstract class BaseHolder<HolderBeanType>
{
	protected HolderBeanType mData;
	//相当于BaseAdapter中getView方法中的缓存视图convertView
	private View mHolderView;
	
	public View getHolderView()
	{
		return mHolderView;
	}
	
	public BaseHolder()
	{
		//初始化根布局，mHolderView相当于BaseAdapter中getView方法中的缓存视图convertView
		mHolderView = initHolderView();
		//绑定Tag
		mHolderView.setTag(this);
	}

	/**
	 * @des 手动设置数据刷新视图
	 * @call 需要数据和刷新数据的时候调用
	 */
	public void setDataAndRefreshHolderView(HolderBeanType data)
	{
		this.mData = data;
		refreshHolderView(data);
	}
	
	/**
	 * @des 初始化viewHolder/根视图，不知道具体实现，让子类去实现
	 * @call BaseHolder初始化被调用
	 */
	public abstract View initHolderView();
	
	/**
	 * @des 刷新视图，不知道具体实现，让子类去实现
	 * @call setDataAndRefreshHolderView被调用的时候调用
	 */
	public abstract void refreshHolderView(HolderBeanType data);
}
