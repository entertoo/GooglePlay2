// Generated code from Butter Knife. Do not modify!
package com.example.googleplay.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.googleplay.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CategoryInfoHolder_ViewBinding implements Unbinder {
  private CategoryInfoHolder target;

  @UiThread
  public CategoryInfoHolder_ViewBinding(CategoryInfoHolder target, View source) {
    this.target = target;

    target.mContainerItem1 = Utils.findRequiredViewAsType(source, R.id.item_category_item_1, "field 'mContainerItem1'", LinearLayout.class);
    target.mContainerItem2 = Utils.findRequiredViewAsType(source, R.id.item_category_item_2, "field 'mContainerItem2'", LinearLayout.class);
    target.mContainerItem3 = Utils.findRequiredViewAsType(source, R.id.item_category_item_3, "field 'mContainerItem3'", LinearLayout.class);
    target.mIvIcon1 = Utils.findRequiredViewAsType(source, R.id.item_category_icon_1, "field 'mIvIcon1'", ImageView.class);
    target.mIvIcon2 = Utils.findRequiredViewAsType(source, R.id.item_category_icon_2, "field 'mIvIcon2'", ImageView.class);
    target.mIvIcon3 = Utils.findRequiredViewAsType(source, R.id.item_category_icon_3, "field 'mIvIcon3'", ImageView.class);
    target.mTvName1 = Utils.findRequiredViewAsType(source, R.id.item_category_name_1, "field 'mTvName1'", TextView.class);
    target.mTvName2 = Utils.findRequiredViewAsType(source, R.id.item_category_name_2, "field 'mTvName2'", TextView.class);
    target.mTvName3 = Utils.findRequiredViewAsType(source, R.id.item_category_name_3, "field 'mTvName3'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CategoryInfoHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mContainerItem1 = null;
    target.mContainerItem2 = null;
    target.mContainerItem3 = null;
    target.mIvIcon1 = null;
    target.mIvIcon2 = null;
    target.mIvIcon3 = null;
    target.mTvName1 = null;
    target.mTvName2 = null;
    target.mTvName3 = null;
  }
}
