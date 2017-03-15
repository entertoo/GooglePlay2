package com.example.googleplay.holder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.bean.CategoryInfoBean;
import com.example.googleplay.utils.UIUtils;

public class CategoryTitleHolder extends BaseHolder<CategoryInfoBean> {

    private TextView mTv;

    @Override
    public View initHolderView() {
        mTv = new TextView(UIUtils.getContext());
        int padding = UIUtils.dip2px(5);
        mTv.setPadding(padding, padding, padding, padding);
        mTv.setTextSize(16);
        mTv.setBackgroundColor(Color.WHITE);

        //设置宽高
        @SuppressWarnings("deprecation")
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        mTv.setLayoutParams(params);

        return mTv;
    }

    @Override
    public void refreshHolderView(CategoryInfoBean data) {
        mTv.setText(data.title);
    }

}
