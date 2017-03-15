// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppDetailSafeHolder_ViewBinding implements Unbinder {
  private AppDetailSafeHolder target;

  @UiThread
  public AppDetailSafeHolder_ViewBinding(AppDetailSafeHolder target, View source) {
    this.target = target;

    target.mPicContainer = Utils.findRequiredViewAsType(source, R.id.app_detail_safe_pic_container, "field 'mPicContainer'", LinearLayout.class);
    target.mIvArrow = Utils.findRequiredViewAsType(source, R.id.app_detail_safe_iv_arrow, "field 'mIvArrow'", ImageView.class);
    target.mDesContainer = Utils.findRequiredViewAsType(source, R.id.app_detail_safe_des_container, "field 'mDesContainer'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AppDetailSafeHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mPicContainer = null;
    target.mIvArrow = null;
    target.mDesContainer = null;
  }
}
