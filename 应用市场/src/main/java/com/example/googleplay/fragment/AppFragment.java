package com.example.googleplay.fragment;

import java.util.List;

import com.example.googleplay.adapter.AppItemAdapter;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.factory.ListViewFactory;
import com.example.googleplay.holder.AppItemHolder;
import com.example.googleplay.manager.DownloadManager;
import com.example.googleplay.protocol.AppProtocol;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 应用
 * 
 * @author haopi
 *
 */
public class AppFragment extends BaseFragment
{

	private AppProtocol mAppProtocol;
	private List<AppInfoBean> mLoadData;
	private AppAdapter mAppAdapter;

	@Override
	public LoadedResult initData() {
		mAppProtocol = new AppProtocol();
		try {
			// 获取list数据
			mLoadData = mAppProtocol.loadData(0);

			return checkState(mLoadData);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}

	}

	@Override
	public View initSuccessView() {
		// 创建listView
		ListView listView = ListViewFactory.createListView();

		mAppAdapter = new AppAdapter(listView, mLoadData);
		listView.setAdapter(mAppAdapter);

		return listView;
	}

	class AppAdapter extends AppItemAdapter
	{

		public AppAdapter(AbsListView absListViewList, List<AppInfoBean> dataSource) {
			super(absListViewList, dataSource);
		}

		public List<AppInfoBean> onLoadMore() throws Exception {
			List<AppInfoBean> appLoadMore = mAppProtocol.loadData(mLoadData.size());

			return appLoadMore;
		}

	}

	@Override
	public void onPause() {
		// 移除监听
		if (mAppAdapter != null) {
			for (AppItemHolder appItemHolder : mAppAdapter.getAppItemHolders()) {
				DownloadManager.getInstance().deleteObserver(appItemHolder);
			}
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		// 添加监听
		if (mAppAdapter != null) {
			for (AppItemHolder appItemHolder : mAppAdapter.getAppItemHolders()) {
				DownloadManager.getInstance().addObserver(appItemHolder);
			}
			// 手动刷新，BaseAdapter.notifyDataSetChanged()
			mAppAdapter.notifyDataSetChanged();
		}

		super.onResume();
	}
}
