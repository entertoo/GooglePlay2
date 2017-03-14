package com.example.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.base.SuperBaseAdapter;
import com.example.googleplay.bean.SubjectInfoBean;
import com.example.googleplay.factory.ListViewFactory;
import com.example.googleplay.holder.SubjectHolder;
import com.example.googleplay.protocol.SubjectProtocol;

import java.util.List;

/**
 * 专题
 * @author haopi
 *
 */
public class SubjectFragment extends BaseFragment
{
	// subject数据
	private List<SubjectInfoBean> mData;
	// subject数据协议
	private SubjectProtocol mSubjectProtocol;

	@Override
	public LoadedResult initData() {
		mSubjectProtocol = new SubjectProtocol();
		try {
			// 加载subject数据
			mData = mSubjectProtocol.loadData(0);
			// 检查数据
			return checkState(mData);

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
		listView.setAdapter(new SubjectAdapter(listView, mData));
		return listView;
	}

	private class SubjectAdapter extends SuperBaseAdapter<SubjectInfoBean>
	{
		List<SubjectInfoBean> subjectLoadMore;

		SubjectAdapter(AbsListView absListViewList, List<SubjectInfoBean> dataSource) {
			super(absListViewList, dataSource);
		}

		@Override
		public BaseHolder<SubjectInfoBean> getSpecialHolder(int position) {
			return new SubjectHolder();
		}

		@Override
		public List<SubjectInfoBean> onLoadMore() throws Exception {
			subjectLoadMore = mSubjectProtocol.loadData(mData.size());
			return subjectLoadMore;
		}
	}
}
