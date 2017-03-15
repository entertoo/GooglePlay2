// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PictureHolder_ViewBinding implements Unbinder {
  private PictureHolder target;

  @UiThread
  public PictureHolder_ViewBinding(PictureHolder target, View source) {
    this.target = target;

    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.item_home_picture_pager, "field 'mViewPager'", ViewPager.class);
    target.mContainerIndicator = Utils.findRequiredViewAsType(source, R.id.item_home_picture_container_indicator, "field 'mContainerIndicator'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PictureHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.mContainerIndicator = null;
  }
}
