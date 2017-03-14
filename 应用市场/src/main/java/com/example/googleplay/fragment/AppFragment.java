package com.example.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.googleplay.adapter.AppItemAdapter;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.factory.ListViewFactory;
import com.example.googleplay.holder.AppItemHolder;
import com.example.googleplay.manager.DownloadManager;
import com.example.googleplay.protocol.AppProtocol;

import java.util.List;

/**
 * 应用
 *
 * @author haopi
 */
public class AppFragment extends BaseFragment {

    private AppProtocol mAppProtocol;
    private List<AppInfoBean> mData;
    private AppAdapter mAppAdapter;

    @Override
    public LoadedResult initData() {
        mAppProtocol = new AppProtocol();
        try {
            // 获取list数据
            mData = mAppProtocol.loadData(0);
            return checkState(mData);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadedResult.ERROR;
        }

    }

    @Override
    public View initSuccessView() {
        // 创建listView
        ListView listView = ListViewFactory.createListView();
        mAppAdapter = new AppAdapter(listView, mData);
        listView.setAdapter(mAppAdapter);
        return listView;
    }

    private class AppAdapter extends AppItemAdapter {
        List<AppInfoBean> appLoadMore;

        AppAdapter(AbsListView absListViewList, List<AppInfoBean> dataSource) {
            super(absListViewList, dataSource);
        }

        public List<AppInfoBean> onLoadMore() throws Exception {
            appLoadMore = mAppProtocol.loadData(mData.size());
            return appLoadMore;
        }

    }

    @Override
    public void onPause() {
        // 移除监听
        if (mAppAdapter != null && mAppAdapter.getAppItemHolders().size() != 0) {
            for (AppItemHolder appItemHolder : mAppAdapter.getAppItemHolders()) {
                DownloadManager.getInstance().deleteObserver(appItemHolder);
            }
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        // 添加监听
        if (mAppAdapter != null && mAppAdapter.getAppItemHolders().size() != 0) {
            for (AppItemHolder appItemHolder : mAppAdapter.getAppItemHolders()) {
                DownloadManager.getInstance().addObserver(appItemHolder);
            }
            // 手动刷新，BaseAdapter.notifyDataSetChanged()
            mAppAdapter.notifyDataSetChanged();
        }

        super.onResume();
    }
}
