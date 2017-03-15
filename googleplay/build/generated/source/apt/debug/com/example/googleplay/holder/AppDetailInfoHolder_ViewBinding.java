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
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppDetailInfoHolder_ViewBinding implements Unbinder {
  private AppDetailInfoHolder target;

  @UiThread
  public AppDetailInfoHolder_ViewBinding(AppDetailInfoHolder target, View source) {
    this.target = target;

    target.InfoIvIcon = Utils.findRequiredViewAsType(source, R.id.app_detail_info_iv_icon, "field 'InfoIvIcon'", ImageView.class);
    target.infoRbStar = Utils.findRequiredViewAsType(source, R.id.app_detail_info_rb_star, "field 'infoRbStar'", RatingBar.class);
    target.InfoTvName = Utils.findRequiredViewAsType(source, R.id.app_detail_info_tv_name, "field 'InfoTvName'", TextView.class);
    target.InfoTvDownloadNum = Utils.findRequiredViewAsType(source, R.id.app_detail_info_tv_downloadnum, "field 'InfoTvDownloadNum'", TextView.class);
    target.InfoTvVersion = Utils.findRequiredViewAsType(source, R.id.app_detail_info_tv_version, "field 'InfoTvVersion'", TextView.class);
    target.InfoTvTime = Utils.findRequiredViewAsType(source, R.id.app_detail_info_tv_time, "field 'InfoTvTime'", TextView.class);
    target.InfoTvSize = Utils.findRequiredViewAsType(source, R.id.app_detail_info_tv_size, "field 'InfoTvSize'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AppDetailInfoHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.InfoIvIcon = null;
    target.infoRbStar = null;
    target.InfoTvName = null;
    target.InfoTvDownloadNum = null;
    target.InfoTvVersion = null;
    target.InfoTvTime = null;
    target.InfoTvSize = null;
  }
}
