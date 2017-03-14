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

public class LoadMoreHolder_ViewBinding implements Unbinder {
  private LoadMoreHolder target;

  @UiThread
  public LoadMoreHolder_ViewBinding(LoadMoreHolder target, View source) {
    this.target = target;

    target.mContainerLoading = Utils.findRequiredViewAsType(source, R.id.item_loadmore_container_loading, "field 'mContainerLoading'", LinearLayout.class);
    target.mContainerRetry = Utils.findRequiredViewAsType(source, R.id.item_loadmore_container_retry, "field 'mContainerRetry'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoadMoreHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mContainerLoading = null;
    target.mContainerRetry = null;
  }
}
