// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.astuetz.PagerSlidingTabStripExtends;
import com.example.googleplay.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.main_toolBar, "field 'toolbar'", Toolbar.class);
    target.mTabs = Utils.findRequiredViewAsType(source, R.id.main_tabs, "field 'mTabs'", PagerSlidingTabStripExtends.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.main_pager, "field 'mViewPager'", ViewPager.class);
    target.mDrawerLayout = Utils.findRequiredViewAsType(source, R.id.main_drawer_layout, "field 'mDrawerLayout'", DrawerLayout.class);
    target.mMenu = Utils.findRequiredViewAsType(source, R.id.main_menu, "field 'mMenu'", ScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.mTabs = null;
    target.mViewPager = null;
    target.mDrawerLayout = null;
    target.mMenu = null;
  }
}
