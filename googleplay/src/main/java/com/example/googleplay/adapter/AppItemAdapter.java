package com.example.googleplay.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.example.googleplay.activity.DetailActivity;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.base.SuperBaseAdapter;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.holder.AppItemHolder;
import com.example.googleplay.manager.DownloadManager;
import com.example.googleplay.utils.UIUtils;

import java.util.LinkedList;
import java.util.List;

public class AppItemAdapter extends SuperBaseAdapter<AppInfoBean> {

    public AppItemAdapter(AbsListView absListViewList, List<AppInfoBean> dataSource) {
        super(absListViewList, dataSource);
    }

    // 保存listView中的所有Holder
    private List<AppItemHolder> appItemHolders = new LinkedList<>();

    // 获取listView中的所有Holder
    public List<AppItemHolder> getAppItemHolders() {
        return appItemHolders;
    }

    // 子类实现具体的子类Holder
    @Override
    public BaseHolder<AppInfoBean> getSpecialHolder(int position) {
        AppItemHolder appItemHolder = new AppItemHolder();
        // 添加观察者
        DownloadManager.getInstance().addObserver(appItemHolder);
        // 保存Holder
        appItemHolders.add(appItemHolder);
        return appItemHolder;
    }

    // 统一重写item点击事件
    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 把包名传过去
        goToDetailActivity(mDataSource.get(position).packageName, mDataSource.get(position).name);
    }

    // 跳转到APP详情界面
    private void goToDetailActivity(String packageName, String name) {
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        intent.putExtra("name", name);
        UIUtils.getContext().startActivity(intent);
    }

}
