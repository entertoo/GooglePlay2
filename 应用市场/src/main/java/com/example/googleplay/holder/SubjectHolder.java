package com.example.googleplay.holder;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.bean.SubjectInfoBean;
import com.example.googleplay.conf.Constants.URLS;
import com.example.googleplay.utils.BitmapHelper;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.views.SmartImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.TextView;

public class SubjectHolder extends BaseHolder<SubjectInfoBean>
{
	
	@ViewInject(R.id.item_subject_iv_icon)
	SmartImageView subjectIvIcon;
	
	@ViewInject(R.id.item_subject_tv_title)
	TextView subjectTvTitle;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_subject_info, null);
		// 注入
		ViewUtils.inject(this, view);
		// 设置容器宽高比
		subjectIvIcon.setRatio((float) 2.43);
		
		return view;
	}

	@Override
	public void refreshHolderView(SubjectInfoBean data) {
		subjectTvTitle.setText(data.des);

		String uri = URLS.IMAGEBASEURL + data.url;
		BitmapHelper.display(subjectIvIcon, uri);
	}

}
