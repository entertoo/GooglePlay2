// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import com.example.googleplay.views.SmartImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubjectHolder_ViewBinding implements Unbinder {
  private SubjectHolder target;

  @UiThread
  public SubjectHolder_ViewBinding(SubjectHolder target, View source) {
    this.target = target;

    target.subjectIvIcon = Utils.findRequiredViewAsType(source, R.id.item_subject_iv_icon, "field 'subjectIvIcon'", SmartImageView.class);
    target.subjectTvTitle = Utils.findRequiredViewAsType(source, R.id.item_subject_tv_title, "field 'subjectTvTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubjectHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.subjectIvIcon = null;
    target.subjectTvTitle = null;
  }
}
