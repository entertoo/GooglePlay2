package com.example.googleplay.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.utils.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * 基类作用:
 * 1.对常规方法进行封装
 * 2.对常规属性，常规方法进行封装
 * <p>
 * BaseFragment抽取前分析（核心）:
 * <p>
 * 一、显示页面分析 View
 * Fragment共性 -->页面共性 -->视图的展示
 * 任何应用其实就只有4中页面类型
 * 1.加载页面
 * 2.错误页面
 * 3.空页面
 * 4.成功页面
 * 1.2.3.三种页面的一个应用基本是固定的
 * 每一个fragment对应的页面4.就不一样
 * 进入应用的时候显示1. 2.3.4.需要加载数据之后才知道显示哪个
 * <p>
 * 二、数据加载的流程 Model
 * 1.触发加载，进入页面开始加载/点击某个按钮的时候加载
 * 2.异步加载数据 -->显示加载视图
 * 3.处理加载结果
 * 1.成功 -->显示成功视图
 * 2.失败
 * 1.数据为空-->显示空视图
 * 2.数据加载失败-->显示加载失败的视图
 * <p>
 * 联想MVC模式：通过Control把View和Model整合起来
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPager mLoadingPager;

    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mLoadingPager == null)// 第一次执行
        {
            /*
			 * 匿名内部类继承了LoadingPager类，
			 * 父类的引用指向子类的对象
			 * 重写LoadingPager的类方法：
			 */
            mLoadingPager = new LoadingPager(UIUtils.getContext())//匿名内部类
            {
                @Override
                public LoadedResult initData() {
                    return BaseFragment.this.initData();
                }

                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }
            };
        } else if ((mLoadingPager.getParent()) != null) {
            // 第二次执行
            ((ViewGroup) mLoadingPager.getParent()).removeView(mLoadingPager);
        }

        return mLoadingPager;
    }

    /**
     * @des 正在加载数据，必须实现，但BaseFragment不知道具体实现，所以需要成为抽象方法，让其子类实现
     * @des LoadingPager 同名方法
     * @call loadData()被调用的时候
     */
    public abstract LoadedResult initData();

    /**
     * @des 加载成功的视图
     * @call 正在加载数据完成之后，并且成功加载数据，我们必须告知具体的成功视图，让其子类实现
     */
    public abstract View initSuccessView();

    /**
     * @des 网络数据json化之后的对象
     * @call 检测网络数据json化后对象是否为空
     */
    public LoadedResult checkState(Object obj) {
        if (obj == null) {
            return LoadedResult.EMPTY;
        }

        if (obj instanceof List) {
            if (((List<?>) obj).size() == 0) {
                return LoadedResult.EMPTY;
            }
        }

        if (obj instanceof Map) {
            if (((Map<?, ?>) obj).size() == 0) {
                return LoadedResult.EMPTY;
            }
        }

        return LoadedResult.SUCCESS;
    }
}
