package com.example.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.base.SuperBaseAdapter;
import com.example.googleplay.bean.CategoryInfoBean;
import com.example.googleplay.factory.ListViewFactory;
import com.example.googleplay.holder.CategoryInfoHolder;
import com.example.googleplay.holder.CategoryTitleHolder;
import com.example.googleplay.protocol.CategoryProtocol;

import java.util.List;

/**
 * 分类
 * 
 * @author haopi
 *
 */
public class CategoryFragment extends BaseFragment
{

	private List<CategoryInfoBean> mData;

	@Override
	public LoadedResult initData() {
		CategoryProtocol mCategoryProtocol = new CategoryProtocol();
		try {
			mData = mCategoryProtocol.loadData(0);
			return checkState(mData);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		ListView listView = ListViewFactory.createListView();
		listView.setAdapter(new CategoryAdapter(listView, mData));
		return listView;
	}

	private class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean>
	{

		CategoryAdapter(AbsListView absListViewList, List<CategoryInfoBean> dataSource) {
			super(absListViewList, dataSource);
		}

		@Override
		public BaseHolder<CategoryInfoBean> getSpecialHolder(int position) {
			CategoryInfoBean categoryInfoBean = mData.get(position);
			if (categoryInfoBean.isTitle) {
				return new CategoryTitleHolder();
			} else {
				return new CategoryInfoHolder();
			}
		}

		// 复写父类方法，增加第三种类型，返回加1
		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount() + 1; // 1 + 1 = 2
		}

		// 复写父类方法，类型有：0，1，2
		@Override
		public int getNormalType(int position) {
			CategoryInfoBean categoryInfoBean = mData.get(position);
			if (categoryInfoBean.isTitle) {
				// 返回一般类型
				return super.getNormalType(position);
			} else {
				// 返回第三种类型
				return super.getNormalType(position) + 1;
			}
		}

	}
}
