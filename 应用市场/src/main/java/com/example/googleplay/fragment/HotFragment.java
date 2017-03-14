package com.example.googleplay.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.googleplay.activity.DetailActivity;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.LoadingPager.LoadedResult;
import com.example.googleplay.protocol.HotProtocol;
import com.example.googleplay.utils.UIUtils;
import com.example.googleplay.views.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * 排行
 *
 * @author haopi
 */
public class HotFragment extends BaseFragment {

    private List<String> mData;

    @Override
    public LoadedResult initData() {
        HotProtocol hotProtocol = new HotProtocol();
        try {
            mData = hotProtocol.loadData(0);
            return checkState(mData);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadedResult.ERROR;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public View initSuccessView() {
        // 返回成功的视图
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        // 自定义流布局
        FlowLayout fl = new FlowLayout(UIUtils.getContext());
        int padding = UIUtils.dip2px(5);
        fl.setPadding(padding, padding, padding, padding);

        // 给每个数据创建一个TextView
        for (final String data : mData) {
            TextView tv = new TextView(UIUtils.getContext());

            tv.setText(data);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(16);

            tv.setPadding(padding, padding, padding, padding);
            tv.setGravity(Gravity.CENTER);

            // 设置tv背景颜色及圆角半径，但是不能让tv有不同颜色的背景
            // tv.setBackgroundResource(R.drawable.shape_hot_fl_tv);

            // A Drawable with a color gradient for buttons, backgrounds, etc.
            GradientDrawable normalBackground = new GradientDrawable();

            // 设置填充颜色
            Random random = new Random();

            // 随机设置RGB三原色
            int alpha = 255;// 完全不透明
            int red = random.nextInt(190) + 20;// 0 - 255 建议 20 - 220
            int green = random.nextInt(190) + 20;
            // 0 - 255 建议 20 - 220
            int blue = random.nextInt(190) + 20;
            // 0 - 255 建议 20 - 220
            int argb = Color.argb(alpha, red, green, blue);

            normalBackground.setColor(argb);
            // 设置圆角半径
            normalBackground.setCornerRadius(UIUtils.dip2px(5));

            // A Drawable with a color gradient for buttons, backgrounds, etc.
            GradientDrawable pressedBackground = new GradientDrawable();
            // 设置按下去的颜色
            pressedBackground.setColor(Color.DKGRAY);
            // 设置圆角半径
            pressedBackground.setCornerRadius(UIUtils.dip2px(5));

            // 设置一个状态图片
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedBackground);
            stateListDrawable.addState(new int[]{}, normalBackground);
            // 设置不同的背景状态
            tv.setBackgroundDrawable(stateListDrawable);

            tv.setClickable(true);
            // 点击TextView进行跳转到应用详情
            tv.setOnClickListener(new OnClickListener() {
                String appName;

                @Override
                public void onClick(View v) {
                    appName = data;
                    goToDetailActivity(appName);
                }
            });

            // 加入流布局
            fl.addView(tv);
        }

        // 加入滑动布局
        scrollView.addView(fl);

        return scrollView;
    }

    private void goToDetailActivity(String data) {
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packgeName", data);
        UIUtils.getContext().startActivity(intent);
    }

}
