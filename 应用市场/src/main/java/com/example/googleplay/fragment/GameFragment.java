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
import com.example.googleplay.protocol.GameProtocol;

import java.util.List;

/**
 * 游戏
 * 
 * @author haopi
 *
 */
public class GameFragment extends BaseFragment
{
	private GameProtocol mGameProtocol;
	private List<AppInfoBean> mData;
	private GameAdapter mGameAdapter;

	@Override
	public LoadedResult initData() {
		mGameProtocol = new GameProtocol();
		try {
			mData = mGameProtocol.loadData(0);
			return checkState(mData);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		ListView listView = ListViewFactory.createListView();
		mGameAdapter = new GameAdapter(listView, mData);
		listView.setAdapter(mGameAdapter);
		return listView;
	}

	private class GameAdapter extends AppItemAdapter
	{
		List<AppInfoBean> gameLoadMore;

		GameAdapter(AbsListView absListViewList, List<AppInfoBean> dataSource) {
			super(absListViewList, dataSource);
		}

		@Override
		public List<AppInfoBean> onLoadMore() throws Exception {
			gameLoadMore = mGameProtocol.loadData(mData.size());
			return gameLoadMore;
		}
	}

	@Override
	public void onPause() {
		// 移除监听
		if (mGameAdapter != null && mGameAdapter.getAppItemHolders().size() != 0) {
			for (AppItemHolder appItemHolder : mGameAdapter.getAppItemHolders()) {
				DownloadManager.getInstance().deleteObserver(appItemHolder);
			}
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		// 添加监听
		if (mGameAdapter != null && mGameAdapter.getAppItemHolders().size() != 0) {
			for (AppItemHolder appItemHolder : mGameAdapter.getAppItemHolders()) {
				DownloadManager.getInstance().addObserver(appItemHolder);
			}
			// 手动刷新
			mGameAdapter.notifyDataSetChanged();
		}

		super.onResume();
	}
}
