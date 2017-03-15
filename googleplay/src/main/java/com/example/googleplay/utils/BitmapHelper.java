package com.example.googleplay.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * bitmapUtils.display(container, uri)
 *
 * @author haopi
 */
public class BitmapHelper {

    public static void display(Context context, ImageView container, String uri) {
        Glide.with(context).load(uri).into(container);
    }

    public static void display(ImageView container, String uri) {
        Glide.with(UIUtils.getContext()).load(uri).into(container);
    }

}
