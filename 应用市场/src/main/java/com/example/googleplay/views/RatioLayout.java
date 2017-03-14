package com.example.googleplay.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 游戏的图片宽高比
 */
public class RatioLayout extends FrameLayout
{
	// 图片宽高比
	public float mPicRatio = 2.43f;

	public void setPicRatio(float picRatio)
	{
		mPicRatio = picRatio;
	}

	public RatioLayout(Context context)
	{
		super(context, null);
	}

	public RatioLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		// obtainStyledAttributes 从R.styleable.RatioLayout中获取样式的所有属性
		// TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

		// index: Index of attribute to retrieve.从属性数组中获取属性值picRatio="2.43"
		// mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_picRatio, 0);
		// 从属性数组中获取属性值relative="height"
		// mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);

		// Give back a previously retrieved array, for later re-use.
		// typedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// 父容器宽度模式
		int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		// 父容器高度模式
		int parentHeightMode = MeasureSpec.getMode(heightMeasureSpec);

		/*
		 * 如果父容器宽度模式为精确的 如fillParent 或者 = 100dp 控件宽度固定，图片宽高比固定，求控件高度
		 * 如果父容器高度模式为精确的，如fillParent 或者 = 100dp 控件高度固定，图片宽高比固定，求控件宽度
		 */
		if (parentWidthMode == MeasureSpec.EXACTLY && parentHeightMode != MeasureSpec.EXACTLY && mPicRatio != 0)
		{
			// 父容器宽度
			int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);

			// 获取孩子的宽度 = 父容器宽度 - 父容器左右内边框和
			int childWidthSize = parentWidthSize - (getPaddingLeft() + getPaddingRight());

			// 图片的宽度 / 图片的高度 = mPicRatio;
			// 计算孩子的高度
			int childHeightSize = (int) (childWidthSize / mPicRatio + .5f);

			// 计算父容器的高度
			int parentHeightSize = childHeightSize + (getPaddingTop() + getPaddingBottom());

			// 主动绘制孩子，固定孩子大小
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
			measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

			// 设置自己的测试结果
			setMeasuredDimension(parentWidthSize, parentHeightSize);

		}
		else if (parentWidthMode != MeasureSpec.EXACTLY && parentHeightMode == MeasureSpec.EXACTLY && mPicRatio != 0 )
		{
			// 父容器高度
			int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);

			// 获取孩子的高度 = 父容器高度 - 父容器上下内边框和
			int childHeightSize = parentHeightSize - (getPaddingTop() + getPaddingBottom());

			// 图片的宽度 / 图片的高度 = mPicRatio;
			// 计算孩子的宽度
			int childWidthSize = (int) (childHeightSize * mPicRatio + .5f);

			// 计算父容器的宽度 = 孩子的宽度 + 父容器左右内边框和
			int parentWidthSize = childWidthSize + (getPaddingLeft() + getPaddingRight());

			// 主动绘制孩子，固定孩子的大小
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
			measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

			// 设置自己的测试结果
			setMeasuredDimension(parentWidthSize, parentHeightSize);
		}
		else
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
