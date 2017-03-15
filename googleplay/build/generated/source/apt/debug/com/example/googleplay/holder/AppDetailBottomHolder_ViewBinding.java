// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import com.example.googleplay.views.ProgressButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppDetailBottomHolder_ViewBinding implements Unbinder {
  private AppDetailBottomHolder target;

  @UiThread
  public AppDetailBottomHolder_ViewBinding(AppDetailBottomHolder target, View source) {
    this.target = target;

    target.mBtnFavo = Utils.findRequiredViewAsType(source, R.id.app_detail_download_btn_favo, "field 'mBtnFavo'", Button.class);
    target.mBtnShare = Utils.findRequiredViewAsType(source, R.id.app_detail_download_btn_share, "field 'mBtnShare'", Button.class);
    target.mBtnDownload = Utils.findRequiredViewAsType(source, R.id.app_detail_download_btn_download, "field 'mBtnDownload'", ProgressButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AppDetailBottomHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnFavo = null;
    target.mBtnShare = null;
    target.mBtnDownload = null;
  }
}
