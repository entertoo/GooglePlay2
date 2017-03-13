package com.example.googleplay.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.example.googleplay.R;
import com.example.googleplay.factory.ThreadPoolFactory;
import com.example.googleplay.utils.UIUtils;

/**
 * 一、显示页面分析 View
 * Fragment共性 -->页面共性 -->视图的展示
 * 任何应用其实就只有4中页面类型
 * 1.加载页面
 * 2.错误页面
 * 3.空页面
 * 4.成功页面
 * 		1.2.3.三种页面的一个应用基本是固定的
 * 		每一个fragment对应的页面4.就不一样
 * 		进入应用的时候显示1. 2.3.4.需要加载数据之后才知道显示哪个
 * 
 * 二、数据加载的流程 Model
 * 1.触发加载，进入页面开始加载/点击某个按钮的时候加载
 * 2.异步加载数据 -->显示加载视图
 * 3.处理加载结果
 * 		1.成功 -->显示成功视图
 * 		2.失败
 * 			1.数据为空-->显示空视图
 * 			2.数据加载失败-->显示加载失败的视图
 * 
 * 联想MVC模式：通过Control把View和Model整合起来
 */   

public abstract class LoadingPager extends FrameLayout
{
	public static final int STATE_NONE = -1; // 默认情况
	public static final int STATE_LOADING = 0;  //正在请求网络
	public static final int STATE_ERROR = 1; // 加载失败
	public static final int STATE_EMPTY = 2; // 加载为空
	public static final int STATE_SUCCESS = 3; // 加载成功
	
	//默认状态
	public int mCurState = STATE_NONE;
	
	//加载中视图
	private View mLoadingView;
	//加载错误视图
	private View mErrorView;
	//加载为空页面
	private View mEmptyView;
	//加载成功页面
	private View mSuceessView;

	// 构造方法
	public LoadingPager(Context context)
	{
		super(context);
		initCommonView();
	}

	/**
	 * @des 初始化常规视图
	 * @call LoadingPager初始化的时候 
	 */
	private void initCommonView()
	{
		// * 1.加载页面
		mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
		// 加入帧布局
		this.addView(mLoadingView);
		
		// * 2.错误页面
		mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
		// 点击重新加载数据按钮
		mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 点击触发加载：重新加载数据
				loadData();
			}
		});
		// 加入帧布局
		this.addView(mErrorView);
		
		// * 3.空页面
		mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
		// 加入帧布局
		this.addView(mEmptyView);
		
		// 刷新UI
		refreshUI();
	}

	/**
	 * @des 根据当前状态显示不同的view
	 * @call 1.LoadingPager初始化的时候
	 * @call 2.正在加载数据执行完成的时候
	 */
	private void refreshUI()
	{
		// 控制loadingView加载视图的隐藏和显示
		mLoadingView.setVisibility((mCurState == STATE_LOADING) || (mCurState == STATE_NONE) ? VISIBLE : INVISIBLE);
		// 控制errorView错误视图的隐藏和显示
		mErrorView.setVisibility((mCurState == STATE_ERROR) ? VISIBLE : INVISIBLE);
		// 控制emptyView空白视图的隐藏和显示
		mEmptyView.setVisibility((mCurState == STATE_EMPTY) ? VISIBLE : INVISIBLE);
		// 控制suceessView成功视图的隐藏和显示
		if(mSuceessView == null && mCurState == STATE_SUCCESS)
		{
			// 创建成功视图
			mSuceessView = initSuccessView();
			// 加入帧布局
			this.addView(mSuceessView);
		}
		
		if(mSuceessView != null)
		{
			// 控制successView视图的隐藏和显示
			mSuceessView.setVisibility((mCurState == STATE_SUCCESS) ? VISIBLE : INVISIBLE);
		}
	}
	
	/*
	 * 数据加载的流程 
	 * 1.触发加载，进入页面开始加载/点击某个按钮的时候加载
	 * 2.异步加载数据 -->显示加载视图
	 * 3.处理加载结果
	 * 		1.成功 -->显示成功视图
	 * 		2.失败
	 * 			1.数据为空-->显示空视图
	 * 			2.数据加载失败-->显示加载失败的视图
	 */
	/**
	 * @des 触发加载数据,开启线程加载数据
	 * @call 暴露给外界调用,当外界触发加载数据
	 */
	public void loadData()
	{
		int state;
		// 被触发加载--->如果当前状态为非成功且当前状态非加载中，开启线程池加载数据。
		if(mCurState != STATE_SUCCESS && mCurState != STATE_LOADING)
		{
			// 设置状态为加载中
			state = STATE_LOADING;
			mCurState = state;
			// 刷新加载中的视图
			refreshUI();
			
			//线程池
			ThreadPoolFactory.getmNormalPool().execute(new LoadDataTask());
		}
	}
	
	private class LoadDataTask implements Runnable
	{
		@Override
		public void run()
		{
			// 真正的去加载网络数据，异步加载数据，获取加载结果
			LoadedResult tmpCurState = initData();
			// 根据处理加载结果,得到状态：成功、失败、空白中的一种
			mCurState = tmpCurState.getLoadedResult();
			// 线程中安全的刷新UI
			UIUtils.postTaskSafely(new Runnable()
			{
				@Override
				public void run()
				{
					// 再次刷新刷UI，
					refreshUI();
				}
			});
		}
	}
	
	/**
	 * @des 正在加载数据，必须实现，但不知道具体实现，所以需要成为抽象方法，让其子类实现
	 * @call loadData()方法被调用的时候调用
	 */
	public abstract LoadedResult initData();
	
	/**
	 * @des 加载成功的视图
	 * @call 正在加载数据完成之后，并且成功加载数据，我们必须告知具体的成功视图，让其子类实现
	 */
	public abstract View initSuccessView();

	/*
	 * 加载后的枚举结果
	 */
	public enum LoadedResult
	{
		ERROR(STATE_ERROR),EMPTY(STATE_EMPTY),SUCCESS(STATE_SUCCESS);
		int state;
		
		public int getLoadedResult()
		{
			return state;
		}
		
		private LoadedResult(int state)
		{
			this.state = state;
		}
	}
}
