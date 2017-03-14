package com.example.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.googleplay.adapter.AppItemAdapter;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.bean.AppInfoBean;
import com.example.googleplay.bean.HomeBean;
import com.example.googleplay.factory.ListViewFactory;
import com.example.googleplay.holder.AppItemHolder;
import com.example.googleplay.holder.PictureHolder;
import com.example.googleplay.manager.DownloadManager;
import com.example.googleplay.protocol.HomeProtocol;

import java.util.List;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment
{
	private List<AppInfoBean> mData;// ListView的数据源
	private List<String> mPictures; // 轮播图
	private HomeProtocol mHomeProtocol;
	private PictureHolder mPictureHolder;
	private HomeAdapter mHomeAdapter;

	/**
	 * @des 正在加载数据，必须实现，但BaseFragment不知道具体实现，所以需要成为抽象方法，让其子类实现
	 * @des LoadingPager 同名方法
	 * @call loadData()被调用的时候
	 */
	@Override
	public LoadedResult initData()// 真正加载数据
	{
		mHomeProtocol = new HomeProtocol();
		try {
			HomeBean mHomeBean = mHomeProtocol.loadData(0);
			// 检查homeBean及homeBean.list的数据是否为空
			LoadedResult state = checkState(mHomeBean);
			if (state != LoadedResult.SUCCESS) {
				return state;
			}
			state = checkState(mHomeBean.list);
			if (state != LoadedResult.SUCCESS) {
				return state;
			}
			// 保存数据
			mData = mHomeBean.list;
			mPictures = mHomeBean.picture;
			return LoadedResult.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	/**
	 * @des 加载成功的视图
	 * @call 正在加载数据完成之后，并且成功加载数据，我们必须告知具体的成功视图，让其子类实现
	 */
	@Override
	public View initSuccessView() {
		// 创建listView
		ListView listView = ListViewFactory.createListView();
		// 图片轮播
		mPictureHolder = new PictureHolder();
		// 把数据传过去，触发设置数据和刷新视图
		mPictureHolder.setDataAndRefreshHolderView(mPictures);
		// 将图片轮播加入ListView的头部
		View headView = mPictureHolder.getHolderView();
		listView.addHeaderView(headView);
		mHomeAdapter = new HomeAdapter(listView, mData);
		listView.setAdapter(mHomeAdapter);
		return listView;
	}

	private class HomeAdapter extends AppItemAdapter
	{
		private List<AppInfoBean> homeLoadMore;

		private HomeAdapter(AbsListView absListViewList, List<AppInfoBean> dataSource) {
			super(absListViewList, dataSource);
		}

		// 真正去加载更多网络数据，子类复写父类的方法
		@Override
		public List<AppInfoBean> onLoadMore() throws Exception {
			homeLoadMore = loadMore(mData.size());
			return homeLoadMore;
		}
	}

	/** 真正去加载更多网络数据*/
	public List<AppInfoBean> loadMore(int index) throws Exception {

		HomeBean homeBeanMore = mHomeProtocol.loadData(index);
		if (homeBeanMore == null) {
			return null;
		}
		if (homeBeanMore.list == null || homeBeanMore.list.size() == 0) {
			return null;
		}
		return homeBeanMore.list;
	}

	@Override
	public void onPause() {
		if (mPictureHolder != null) {
			if (mPictureHolder.mAutoScrollTask != null) {
				// 停止图片轮播
				mPictureHolder.mAutoScrollTask.stop();
			}
		}
		// 移除监听
		if (mHomeAdapter != null && mHomeAdapter.getAppItemHolders().size() != 0) {
			for (AppItemHolder appItemHolder : mHomeAdapter.getAppItemHolders()) {
				DownloadManager.getInstance().deleteObserver(appItemHolder);
			}
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		if (mPictureHolder != null) {
			// 界面可见时开启图片自动轮播任务
			mPictureHolder.getAutoScrollTask().start();
		}
		// 添加监听
		if (mHomeAdapter != null && mHomeAdapter.getAppItemHolders().size() != 0) {
			for (AppItemHolder appItemHolder : mHomeAdapter.getAppItemHolders()) {
				DownloadManager.getInstance().addObserver(appItemHolder);
			}
			// 手动刷新
			mHomeAdapter.notifyDataSetChanged();
		}
		super.onResume();
	}
}
