package com.example.googleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * 定义一个全局的盒子， 里面放置的对象，属性，方法都是全局可以调用
 * 程序的入口 ,初始化一些常用的类及属性，放到盒子里面来
 *
 * @author haopi
 */
public class BaseApplication extends Application {

    private static BaseApplication Instance;

    // 主线程
    private static Thread mMainThread;
    // 主线程ID
    private static long mMainThreadId;
    // 主线程Looper对象
    private static Looper mMainLooper;
    // 主线程Handler
    private static Handler mHandler;

    public static Handler getHandler() {
        return mHandler;
    }

    public static Context getContext() {
        return Instance;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getmMainLooper() {
        return mMainLooper;
    }

    @Override
    public void onCreate() {
        Instance = this;

        /*
		 * 上下文
		 * getApplicationContext()取的是这个应用程序的Context，
		 * 返回应用的上下文，生命周期是整个应用，应用摧毁它才摧毁。
		 * Activity.this 返回当前activity的上下文，
		 * 生命周期只是它所在的Activity，activity 摧毁他就摧毁
		 */

        // 主线程
        mMainThread = Thread.currentThread();

        // 主线程ID
        mMainThreadId = android.os.Process.myTid();

		/*
		android.os.Process 获取当前进程的方法
	    android.os.Process.getElapsedCpuTime()：获取消耗的时间。
	    android.os.Process.myPid()：获取该进程的ID。
	    android.os.Process.myTid()：获取调用进程的ID。
	    android.os.Process.myUid()：获取该进程的用户ID。
	    android.os.Process.supportsProcesses：判断该进程是否支持多进程。
		*/

        // 主线程Looper对象
        mMainLooper = getMainLooper();
        // 主线程Handler
        mHandler = new Handler();

        super.onCreate();
    }


}
