// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import com.example.googleplay.views.CircleProgressView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppItemHolder_ViewBinding implements Unbinder {
  private AppItemHolder target;

  @UiThread
  public AppItemHolder_ViewBinding(AppItemHolder target, View source) {
    this.target = target;

    target.mIvIcon = Utils.findRequiredViewAsType(source, R.id.item_appinfo_iv_icon, "field 'mIvIcon'", ImageView.class);
    target.mTvTitle = Utils.findRequiredViewAsType(source, R.id.item_appinfo_tv_title, "field 'mTvTitle'", TextView.class);
    target.mRbStars = Utils.findRequiredViewAsType(source, R.id.item_appinfo_rb_stars, "field 'mRbStars'", RatingBar.class);
    target.mTvSize = Utils.findRequiredViewAsType(source, R.id.item_appinfo_tv_size, "field 'mTvSize'", TextView.class);
    target.mTvDes = Utils.findRequiredViewAsType(source, R.id.item_appinfo_tv_des, "field 'mTvDes'", TextView.class);
    target.mCircleProgressView = Utils.findRequiredViewAsType(source, R.id.item_appinfo_circleprogress, "field 'mCircleProgressView'", CircleProgressView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AppItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIvIcon = null;
    target.mTvTitle = null;
    target.mRbStars = null;
    target.mTvSize = null;
    target.mTvDes = null;
    target.mCircleProgressView = null;
  }
}
