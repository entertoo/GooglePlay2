package com.example.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.bean.SubjectInfoBean;
import com.example.googleplay.conf.Constants.URLS;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.views.SmartImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectHolder extends BaseHolder<SubjectInfoBean> {

    @BindView(R.id.item_subject_iv_icon) SmartImageView subjectIvIcon;
    @BindView(R.id.item_subject_tv_title) TextView subjectTvTitle;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_subject_info, null);
        ButterKnife.bind(this, view);
        // 设置容器宽高比
        subjectIvIcon.setRatio((float) 2.43);
        return view;
    }

    @Override
    public void refreshHolderView(SubjectInfoBean data) {
        subjectTvTitle.setText(data.des);
        String uri = URLS.IMAGE_BASE_URL + data.url;
        BitmapHelper.display(subjectIvIcon, uri);
    }

}
