package com.example.googleplay.utils;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * bitmapUtils.display(container, uri)
 *
 * @author haopi
 */
public class BitmapHelper {

    public static BitmapUtils bitmapUtils;

    static {
        bitmapUtils = new BitmapUtils(UIUtils.getContext());
    }

    public static <T extends View> void display(T container, String uri) {
        bitmapUtils.display(container, uri);
    }
}
