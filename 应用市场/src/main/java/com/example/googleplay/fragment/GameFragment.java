package com.example.googleplay.fragment;

import java.util.List;

import com.example.googleplay.adapter.AppItemAdapter;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.factory.ListViewFactory;
import com.example.googleplay.holder.AppItemHolder;
import com.example.googleplay.manager.DownloadManager;
import com.example.googleplay.protocol.GameProtocol;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 游戏
 * 
 * @author haopi
 *
 */
public class GameFragment extends BaseFragment
{
	private GameProtocol mGameProtocol;
	private List<AppInfoBean> mLoadData;
	private GameAdapter mGameAdapter;

	@Override
	public LoadedResult initData() {
		mGameProtocol = new GameProtocol();
		try {
			mLoadData = mGameProtocol.loadData(0);
			return checkState(mLoadData);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		ListView listView = ListViewFactory.createListView();

		mGameAdapter = new GameAdapter(listView, mLoadData);
		listView.setAdapter(mGameAdapter);

		return listView;
	}

	class GameAdapter extends AppItemAdapter
	{

		public GameAdapter(AbsListView absListViewList, List<AppInfoBean> dataSource) {
			super(absListViewList, dataSource);
		}

		@Override
		public List<AppInfoBean> onLoadMore() throws Exception {
			List<AppInfoBean> gameLoadMore = mGameProtocol.loadData(mLoadData.size());

			return gameLoadMore;
		}
	}

	@Override
	public void onPause() {
		// 移除监听
		if (mGameAdapter != null) {
			for (AppItemHolder appItemHolder : mGameAdapter.getAppItemHolders()) {
				DownloadManager.getInstance().deleteObserver(appItemHolder);
			}
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		// 添加监听
		if (mGameAdapter != null) {
			for (AppItemHolder appItemHolder : mGameAdapter.getAppItemHolders()) {
				DownloadManager.getInstance().addObserver(appItemHolder);
			}
			// 手动刷新
			mGameAdapter.notifyDataSetChanged();
		}

		super.onResume();
	}
}
