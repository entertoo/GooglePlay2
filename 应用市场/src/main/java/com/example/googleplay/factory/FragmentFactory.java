package com.example.googleplay.factory;

import android.support.v4.util.SparseArrayCompat;

import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.fragment.AppFragment;
import com.example.googleplay.fragment.CategoryFragment;
import com.example.googleplay.fragment.GameFragment;
import com.example.googleplay.fragment.HomeFragment;
import com.example.googleplay.fragment.HotFragment;
import com.example.googleplay.fragment.RecommendFragment;
import com.example.googleplay.fragment.SubjectFragment;

/**
 * 生产Fragment的工厂
 */
public class FragmentFactory {

    /*
     * <string-array name="main_titles"> <item>首页</item> <item>应用</item>
     * <item>游戏</item> <item>专题</item> <item>推荐</item> <item>分类</item>
     * <item>排行</item> </string-array>
     */
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_APP = 1;
    private static final int FRAGMENT_GAME = 2;
    private static final int FRAGMENT_SUBJECT = 3;
    private static final int FRAGMENT_RECOMMEND = 4;
    private static final int FRAGMENT_CATEGORY = 5;
    private static final int FRAGMENT_HOT = 6;

    // 使用系统推荐的SparseArray来保存fragment，我们这里使用v4兼容包SparseArrayCompat
    private static SparseArrayCompat<BaseFragment> cachesFragments = new SparseArrayCompat<>();

    public static BaseFragment getFragment(int position) {

        // 使用系统推荐的SparseArray来保存fragment，如果缓存里面有fragment，就直接返回该fragment
        BaseFragment fragment = cachesFragments.get(position);
        if (fragment != null) {
            return fragment;
        }

        switch (position) {
            case FRAGMENT_HOME:// 主页
                fragment = new HomeFragment();
                break;

            case FRAGMENT_APP:// 应用
                fragment = new AppFragment();
                break;

            case FRAGMENT_SUBJECT:// 专题
                fragment = new SubjectFragment();
                break;

            case FRAGMENT_GAME:// 游戏
                fragment = new GameFragment();
                break;

            case FRAGMENT_RECOMMEND:// 推荐
                fragment = new RecommendFragment();
                break;

            case FRAGMENT_CATEGORY:// 分类
                fragment = new CategoryFragment();
                break;

            case FRAGMENT_HOT:// 排行
                fragment = new HotFragment();
                break;
            default:
                break;
        }

        // 保存对应的fragment
        cachesFragments.put(position, fragment);

        return fragment;
    }
}
