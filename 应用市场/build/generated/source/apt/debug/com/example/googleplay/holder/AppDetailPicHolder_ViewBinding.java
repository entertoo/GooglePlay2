// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppDetailPicHolder_ViewBinding implements Unbinder {
  private AppDetailPicHolder target;

  @UiThread
  public AppDetailPicHolder_ViewBinding(AppDetailPicHolder target, View source) {
    this.target = target;

    target.mPicIvContainer = Utils.findRequiredViewAsType(source, R.id.app_detail_pic_iv_container, "field 'mPicIvContainer'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AppDetailPicHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mPicIvContainer = null;
  }
}
