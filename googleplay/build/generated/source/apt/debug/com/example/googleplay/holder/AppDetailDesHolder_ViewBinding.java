// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppDetailDesHolder_ViewBinding implements Unbinder {
  private AppDetailDesHolder target;

  @UiThread
  public AppDetailDesHolder_ViewBinding(AppDetailDesHolder target, View source) {
    this.target = target;

    target.mTvDes = Utils.findRequiredViewAsType(source, R.id.app_detail_des_tv_des, "field 'mTvDes'", TextView.class);
    target.mTvAuthor = Utils.findRequiredViewAsType(source, R.id.app_detail_des_tv_author, "field 'mTvAuthor'", TextView.class);
    target.mIvArrow = Utils.findRequiredViewAsType(source, R.id.app_detail_des_iv_arrow, "field 'mIvArrow'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AppDetailDesHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvDes = null;
    target.mTvAuthor = null;
    target.mIvArrow = null;
  }
}
