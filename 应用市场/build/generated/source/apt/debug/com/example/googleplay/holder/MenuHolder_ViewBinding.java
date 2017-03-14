// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MenuHolder_ViewBinding implements Unbinder {
  private MenuHolder target;

  private View view2131427473;

  @UiThread
  public MenuHolder_ViewBinding(final MenuHolder target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.home_layout, "method 'clickHome'");
    view2131427473 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickHome(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131427473.setOnClickListener(null);
    view2131427473 = null;
  }
}
