package com.example.googleplay.factory;

import com.example.googleplay.manager.ThreadPoolProxy;

/**
 * 线程池工厂
 * @author haopi
 *
 */
public class ThreadPoolFactory
{
	static ThreadPoolProxy mNormalPool;
	static ThreadPoolProxy mDownLoadPool;
	
	/**得到一般的线程池*/
	public static ThreadPoolProxy getmNormalPool()
	{
		if(null == mNormalPool)
		{
			synchronized (ThreadPoolProxy.class)
			{
				if(null == mNormalPool)
				{
					mNormalPool = new ThreadPoolProxy(5, 5, 3000);
				}
			}
		}
		
		return mNormalPool;
	}
	
	/**得到下载的线程池*/
	public static ThreadPoolProxy getmDownLoadPool()
	{
		if(null == mDownLoadPool)
		{
			synchronized (ThreadPoolProxy.class)
			{
				if(null == mDownLoadPool)
				{
					mDownLoadPool = new ThreadPoolProxy(3, 3, 3000);
				}
			}
		}
		
		return mDownLoadPool;
	}
}
