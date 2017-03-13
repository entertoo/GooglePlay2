package com.example.googleplay.utils;

import com.example.googleplay.base.BaseApplication;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

/**
 * 和UI相关的工具类
 * 
 * @author haopi
 *
 */
public class UIUtils
{
	// 得到上下文
	public static Context getContext()
	{
		return BaseApplication.getmContext();
	}

	// 得到Resource对象
	public static Resources getResouce()
	{
		return getContext().getResources();
	}

	// 得到String.xml中的字符串
	public static String getString(int resId)
	{
		return getResouce().getString(resId);
	}
	// 得到String.xml中的字符串及占位符
	public static String getString(int id, Object... formatArgs)
	{
		return getResouce().getString(id, formatArgs);
	}

	// 得到String.xml中的字符串数组
	public static String[] getStringArr(int resId)
	{
		return getResouce().getStringArray(resId);
	}

	// 得到color.xml中的颜色
	public static int getColor(int colorId)
	{
		return getResouce().getColor(colorId);
	}

	// 得到应用程序的包名
	public static String getPackageName()
	{
		return getContext().getPackageName();
	}

	// 得到主线程Id
	public static long getMainThreadId()
	{
		return BaseApplication.getmMainThreadId();
	}

	// 得到主线程Handler
	public static Handler getMainThreadHandler()
	{
		return BaseApplication.getmHandler();
	}

	// 安全的执行UI任务
	public static void postTaskSafely(Runnable task)
	{
		// 当前线程ID
		int curThreadId = android.os.Process.myTid();

		// 如果当前线程是主线程
		if (curThreadId == getMainThreadId())
		{
			task.run();
		}
		else
		{
			// 如果当前线程不是主线程
			getMainThreadHandler().post(task);
		}
	}
	
	// 延迟执行任务
	public static void postTaskDelay(Runnable task, int delayMillis)
	{
		getMainThreadHandler().postDelayed(task, delayMillis);
	}

	// 停止执行任务
	public static void postTaskRemove(Runnable task)
	{
		getMainThreadHandler().removeCallbacks(task);
	}
	
	// 停止执行任务
	public static void postTaskAndMessagesRemove(Object token)
	{
		getMainThreadHandler().removeCallbacksAndMessages(token);
	}
	
	// dip-->px
	public static int dip2px(int dip)
	{
		// px / dip = density
		float density = getResouce().getDisplayMetrics().density;
		int px = (int) (dip * density + .5f);
		return px;
	}

	// px-->dip
	public static int px2dip(int px)
	{
		// px / dip = density
		float density = getResouce().getDisplayMetrics().density;
		int dip = (int) (px / density + .5f);
		return dip;
	}
}
