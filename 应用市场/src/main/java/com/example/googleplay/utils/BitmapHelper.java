package com.example.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

import android.view.View;

/**
 * bitmapUtils.display(container, uri)
 * @author haopi
 *
 */
public class BitmapHelper
{
	public static BitmapUtils bitmapUtils;
	static
	{
		bitmapUtils =  new BitmapUtils(UIUtils.getContext());
	}
	
	public static <T extends View> void display(T container, String uri)
	{
		bitmapUtils.display(container, uri);
	}
}
