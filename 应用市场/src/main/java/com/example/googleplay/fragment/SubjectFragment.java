package com.example.googleplay.fragment;

import java.util.List;

import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.base.SuperBaseAdapter;
import com.example.googleplay.bean.SubjectInfoBean;
import com.example.googleplay.factory.ListViewFactory;
import com.example.googleplay.holder.SubjectHolder;
import com.example.googleplay.protocol.SubjectProtocol;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 专题
 * @author haopi
 *
 */
public class SubjectFragment extends BaseFragment
{
	// subject数据
	private List<SubjectInfoBean> mLoadData;
	// subject数据协议
	private SubjectProtocol mSubjectProtocol;

	@Override
	public LoadedResult initData() {
		mSubjectProtocol = new SubjectProtocol();
		try {
			// 加载subject数据
			mLoadData = mSubjectProtocol.loadData(0);
			// 检查数据
			return checkState(mLoadData);

		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		// 创建subject里面的listView列表
		ListView listView = ListViewFactory.createListView();
		// 设置适配器，传入数据
		listView.setAdapter(new SubjectAdapter(listView, mLoadData));

		return listView;
	}

	// subject适配器
	class SubjectAdapter extends SuperBaseAdapter<SubjectInfoBean>
	{

		public SubjectAdapter(AbsListView absListViewList, List<SubjectInfoBean> dataSource) {
			super(absListViewList, dataSource);
		}

		// 获取holder
		@Override
		public BaseHolder<SubjectInfoBean> getSpecialHolder(int position) {
			return new SubjectHolder();
		}

		// 覆写父类方法，加载更多数据
		@Override
		public List<SubjectInfoBean> onLoadMore() throws Exception {
			List<SubjectInfoBean> subjectLoadMore = mSubjectProtocol.loadData(mLoadData.size());

			return subjectLoadMore;
		}
	}
}
