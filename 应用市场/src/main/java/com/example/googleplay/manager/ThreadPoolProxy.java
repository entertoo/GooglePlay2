package com.example.googleplay.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author haopi
 * @des 创建线程池，执行任务，提交任务
 */
public class ThreadPoolProxy {

    private ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;//核心线程数
    private int mMaximumPoolSize;//最大线程数
    private long mKeepAliveTime;//保持时间

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        super();
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        mKeepAliveTime = keepAliveTime;
    }

    //创建唯一的线程池
    private ThreadPoolExecutor initThreadPoolExecutor() {
        if (null == mExecutor) {
            synchronized (ThreadPoolProxy.class) {
                if (null == mExecutor) {
                    TimeUnit unit = TimeUnit.MILLISECONDS;//毫秒
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(); //（无界队列）可变的阻塞队列
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();//默认线程工厂
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();//异常捕获器，弃任务并抛出RejectedExecutionException异常。

                    mExecutor = new ThreadPoolExecutor(
                            mCorePoolSize, //核心线程数
                            mMaximumPoolSize, //最大线程数
                            mKeepAliveTime, //保持时间
                            unit, //保持时间对应的单位
                            workQueue, //缓存队列，阻塞队列
                            threadFactory, //线程工厂
                            handler);//异常捕获器
                }
            }
        }
        return mExecutor;
    }

    // 执行任务
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    // 提交任务
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    // 移除未执行任务,已经执行的不会被移除
    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
}
