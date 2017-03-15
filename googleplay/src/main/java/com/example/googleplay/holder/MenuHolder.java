package com.example.googleplay.holder;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Toast;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseHolder;
import com.example.googleplay.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuHolder extends BaseHolder<Object> {

    private DrawerLayout drawerLayout;

    public MenuHolder(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void refreshHolderView(Object data) {

    }

    @OnClick(R.id.home_layout)
    public void clickHome(View view) {

        Toast.makeText(UIUtils.getContext(), "这是首页", Toast.LENGTH_SHORT).show();
        //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

}
