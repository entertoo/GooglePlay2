package com.example.googleplay.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.googleplay.conf.Constants;
import com.example.googleplay.factory.ThreadPoolFactory;
import com.example.googleplay.holder.LoadMoreHolder;
import com.example.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView的适配器
 * 	
 * @修改提交者 $Author: chp $
 * @提交时间 $Date: 2016-07-15 22:10:36 +0800 (Fri, 15 Jul 2016) $
 * @当前版本 $Rev: 15 $
 *
 */
public abstract class SuperBaseAdapter<ItemBeanType> extends BaseAdapter implements OnItemClickListener
{
	protected List<ItemBeanType> mDataSource = new ArrayList<>();
	// 加载更多的视图类型
	private static final int VIEW_TYPE_LOAD_MORE = 0;
	// 普通的视图类型
	private static final int VIEW_TYPE_NORMAL = 1;

	private LoadMoreHolder mLoadMoreHolder;

	private LoadMoreTask mLoadMoreTask;

	public SuperBaseAdapter(AbsListView absListViewList, List<ItemBeanType> dataSource) {
		super();
		// 给ListView设置点击事件
		absListViewList.setOnItemClickListener(this);
		mDataSource = dataSource;
	}

	@Override
	public int getCount() {
		if (mDataSource != null) {
			return mDataSource.size() + 1;
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return mDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// 判断当前位置是否为数据底部
		if (position == getCount() - 1) {
			// 划到底部，返回加载更多
			return VIEW_TYPE_LOAD_MORE;// 0
		}
		// 未滑倒底部，正常返回 / 分类页面返回+1
		return getNormalType(position);
	}

	// 默认返回listView正常情况类型，子类可重写，返回所需要的类型
	public int getNormalType(int position) {
		// 默认返回普通格式的listView类型
		return VIEW_TYPE_NORMAL;// 1
	}

	@Override
	public int getViewTypeCount() {
		// 两种ListView 0，1（加载更多和普通item）
		return super.getViewTypeCount() + 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		BaseHolder<ItemBeanType> holder;

		// 初始化视图，决定根布局
		if (null == convertView) {
			if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
				// 加载更多数据的Holder
				holder = (BaseHolder<ItemBeanType>) getLoadMOreHolder();
			} else {
				// 主页、游戏、应用的Holder
				holder = getSpecialHolder(position);
			}
		} else {
			// 直接从convertView里面获取holder
			holder = (BaseHolder<ItemBeanType>) convertView.getTag();
		}

		// 数据展示
		if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
			// 开始去加载更多
			perFormLoadMore();
		} else {
			// 传入数据，触发设置数据刷新视图
			holder.setDataAndRefreshHolderView(mDataSource.get(position));
		}

		// 返回布局，相当于返回convertView
		return holder.getHolderView();
	}

	/**
	 * @des 返回具体的BaseHolder子类
	 * @call 当getView中convertView为空时创建特定的Holder，由子类实现：主页、游戏、应用的
	 */
	public abstract BaseHolder<ItemBeanType> getSpecialHolder(int position);

	/**
	 * @des LoadMoreHolder加载更多数据功能是通用功能，不需要定义成抽象方法，可以直接实现
	 * @call 创建加载更多数据的Holder。
	 */
	private BaseHolder<Integer> getLoadMOreHolder() {
		if (mLoadMoreHolder == null) {
			mLoadMoreHolder = new LoadMoreHolder();
		}
		return mLoadMoreHolder;
	}

	/**
	 * @des 划到底的时候开始去加载更多的数据
	 * @call 划到底的时候
	 */
	private void perFormLoadMore() {
		if (mLoadMoreTask == null) {
			// 修改loadMore当前的视图为加载中
			int state = LoadMoreHolder.STATE_LOADING;
			// 手动设置数据刷新视图
			mLoadMoreHolder.setDataAndRefreshHolderView(state);
			mLoadMoreTask = new LoadMoreTask();
			// 开启线程池加载更多
			ThreadPoolFactory.getNormalPool().execute(mLoadMoreTask);
		}
	}

	private class LoadMoreTask implements Runnable
	{
		int state;
		@Override
		public void run() {
			List<ItemBeanType> loadMoreDatas = null;
			// 默认加载状态
			state = LoadMoreHolder.STATE_LOADING;
			try {
				// 真正请求网络加载数据
				loadMoreDatas = onLoadMore();
				// 得到返回结果，处理结果
				if (loadMoreDatas == null)// 没有更多数据
				{
					state = LoadMoreHolder.STATE_NONE;
				} else {
					// 假如规定每页返回20条数据
					// 返回10<20==>没有加载更多
					if (loadMoreDatas.size() < Constants.PAGER_SIZE) {
						state = LoadMoreHolder.STATE_NONE;
					} else {
						state = LoadMoreHolder.STATE_LOADING;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				state = LoadMoreHolder.STATE_RETRY;
			}
			// 使用临时变量保存数据
			final int tmpState = state;
			final List<ItemBeanType> tmpLoadMoreDatas = loadMoreDatas;
			// 刷新loadmore视图
			UIUtils.postTaskSafely(new Runnable() {
				@Override
				public void run() {
					// 刷新loadmore视图
					mLoadMoreHolder.setDataAndRefreshHolderView(tmpState);
					// 刷新listview视图返回更多过后得到的数据mData.addAll()
					if (tmpLoadMoreDatas != null) {
						// Adds the objects in the specified collection to the end of this List
						// 把下载更多的数据加到原数据后面
						mDataSource.addAll(tmpLoadMoreDatas);
						/*
						 * Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself. 
						 * 刷新数据视图
						 */
						notifyDataSetChanged();
					}
				}
			});

			mLoadMoreTask = null;
		}
	}

	/**
	 * @des 真正去加载更多网络数据，让需要加载更多数据的子类去重写实现
	 * @call 划到底的时候
	 */
	public List<ItemBeanType> onLoadMore() throws Exception {
		return null;
	}

	/**
	 * 处理item的点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 如果是listView 需要减去类似轮播图的位置
		if (parent instanceof ListView) {
			position = position - ((ListView) parent).getHeaderViewsCount();
		}

		// 根据不同的位置设置不同的点击事件
		if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
			// 重新加载更多
			perFormLoadMore();
		} else {
			// 点击普通条目的点击事件
			onNormalItemClick(parent, view, position, id);
		}
	}

	/**
	 * @des 非加载更多情况下的点击普通条目的点击事件
	 * @call item发生点击事件的时候，如果子类需要处理，可以直接复写
	 */
	public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}
