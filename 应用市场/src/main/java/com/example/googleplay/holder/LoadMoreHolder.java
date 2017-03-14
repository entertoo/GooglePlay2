package com.example.googleplay.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreHolder extends BaseHolder<Integer> {

    @BindView(R.id.item_loadmore_container_loading) LinearLayout mContainerLoading;
    @BindView(R.id.item_loadmore_container_retry) LinearLayout mContainerRetry;

    public static final int STATE_LOADING = 0;
    public static final int STATE_RETRY = 1;
    public static final int STATE_NONE = 2;

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void refreshHolderView(Integer state) {
        mContainerLoading.setVisibility(View.INVISIBLE);
        mContainerRetry.setVisibility(View.INVISIBLE);
        switch (state) {
            case STATE_LOADING:
                mContainerLoading.setVisibility(View.VISIBLE);
                break;
            case STATE_RETRY:
                mContainerRetry.setVisibility(View.VISIBLE);
                break;
            case STATE_NONE:

                break;

            default:
                break;
        }
    }
}
