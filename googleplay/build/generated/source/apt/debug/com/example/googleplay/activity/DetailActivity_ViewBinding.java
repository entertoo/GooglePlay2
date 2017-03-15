// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding implements Unbinder {
  private DetailActivity target;

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target, View source) {
    this.target = target;

    target.appDetailBottom = Utils.findRequiredViewAsType(source, R.id.app_detail_bottom, "field 'appDetailBottom'", FrameLayout.class);
    target.appDetailInfo = Utils.findRequiredViewAsType(source, R.id.app_detail_info, "field 'appDetailInfo'", FrameLayout.class);
    target.appDetailSafe = Utils.findRequiredViewAsType(source, R.id.app_detail_safe, "field 'appDetailSafe'", FrameLayout.class);
    target.appDetailPic = Utils.findRequiredViewAsType(source, R.id.app_detail_pic, "field 'appDetailPic'", FrameLayout.class);
    target.appDetailDes = Utils.findRequiredViewAsType(source, R.id.app_detail_des, "field 'appDetailDes'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.appDetailBottom = null;
    target.appDetailInfo = null;
    target.appDetailSafe = null;
    target.appDetailPic = null;
    target.appDetailDes = null;
  }
}
