package com.example.googleplay.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStripExtends;
import com.example.googleplay.R;
import com.example.googleplay.base.BaseActivity;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.factory.FragmentFactory;
import com.example.googleplay.holder.MenuHolder;
import com.example.googleplay.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_toolBar) Toolbar toolbar;
    @BindView(R.id.mian_tabs) PagerSlidingTabStripExtends mTabs;
    @BindView(R.id.mian_pager) ViewPager mViewPager;
    @BindView(R.id.main_drawlayout) DrawerLayout mDrawerLayout;
    @BindView(R.id.main_meun) ScrollView mMenu;

    private String[] mMainTitles;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
        toolbar.setTitle("应用市场");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public void initData() {
        // 导航栏标题数据
        mMainTitles = UIUtils.getStringArr(R.array.main_titles);
        MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabs.setViewPager(mViewPager);
        // 左侧菜单
        MenuHolder menuHolder = new MenuHolder(mDrawerLayout);
        mMenu.addView(menuHolder.getHolderView());
        menuHolder.setDataAndRefreshHolderView(null);
    }

    @Override
    public void initListener() {
        // 给mTabs导航栏设置监听事件
        mTabs.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 获取缓存中的fragment
                BaseFragment fragment = FragmentFactory.getFragment(position);
                // 如果fragment不为空，触发加载数据
                if (fragment != null) {
                    // 加载数据
                    fragment.getLoadingPager().loadData();
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * FragmentStatePagerAdapter ---> fragment 填充mViewPager
     * 未缓存fragment，其实它缓存了fragment状态
     */
    private class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        private Fragment fragment;

        private MainFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = FragmentFactory.getFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mMainTitles != null) {
                return mMainTitles.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }

    }

    /**
     * PagerAdapter ---> view 填充mViewPager
     */
    private class MainPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (null != mMainTitles) {
                return mMainTitles.length;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(UIUtils.getContext());
            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        // 该方法需要重写，默认返回null，报空指针异常！
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }
    }

    /*
     * FragmentPagerAdapter ---> fragment 填充mViewPager
     * 缓存了fragment，如果fragment比较多，就不建议使用
     */
    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        private Fragment fragment;

        private MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 创建fragment
            fragment = FragmentFactory.getFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mMainTitles != null) {
                return mMainTitles.length;
            }
            return 0;
        }

        // 该方法需要重写，默认返回null，报空指针异常！
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }

    }

}
