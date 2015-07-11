package com.yan.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Yan on 2015/7/8.
 */
public class CustomBottomPageIndicator  extends LinearLayout implements ViewPager.OnPageChangeListener{

    public static final int INDICATOR_TYPE_ICON = 0;
    public static final int INDICATOR_TYPE_TEXT = 1;

    public enum IndicatorType {
        ICON(INDICATOR_TYPE_ICON),
        TEXT(INDICATOR_TYPE_TEXT),
        UNKOWN(-1);

        private int type;

        IndicatorType(int type) {
            this.type = type;
        }

        public static IndicatorType of(int value) {
            switch (value){
                case INDICATOR_TYPE_ICON:
                    return ICON;
                case INDICATOR_TYPE_TEXT:
                    return TEXT;
                default:
                    return UNKOWN;
            }
        }
    }

    public static final int DEFAULT_INDICATOR_SPACING = 5;
    private IndicatorType mIndicatorType = IndicatorType.of(INDICATOR_TYPE_ICON);
    private int mTextColor;
    private int mTextSize;
    private int mIndicatorSpacing;
    private Drawable solidDrawable, strokeDrawable;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mUserDefinedPageChangeListener;
    private boolean mIndicatorTypeChanged = false;
    private int mActivePosition = 0;

    public CustomBottomPageIndicator(Context context) {
        this(context, null);
    }

    public CustomBottomPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBottomPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomBottomPageIndicator, 0, 0);
        try {
            mIndicatorSpacing = a.getDimensionPixelSize(R.styleable.CustomBottomPageIndicator_indicator_spacing, DEFAULT_INDICATOR_SPACING);
            mTextSize = a.getDimensionPixelSize(R.styleable.CustomBottomPageIndicator_indicator_textSize, 14);
            mTextColor = a.getColor(R.styleable.CustomBottomPageIndicator_indicator_textColor, 0xffffff);
            solidDrawable = a.getDrawable(R.styleable.CustomBottomPageIndicator_solidDrawable); //实心球
            if (solidDrawable == null) {
                solidDrawable = a.getResources().getDrawable(R.drawable.circle_indicator_solid);
            }
            strokeDrawable = a.getDrawable(R.styleable.CustomBottomPageIndicator_strokeDrawable);
            if (strokeDrawable == null) {
                strokeDrawable = a.getResources().getDrawable(R.drawable.circle_indicator_stroke);
            }
            mIndicatorType = IndicatorType.of(a.getInt(R.styleable.CustomBottomPageIndicator_indicator_type, mIndicatorType.type));
        }finally {
            a.recycle();
        }
        init();
    }
    private void init() {
        setOrientation(HORIZONTAL);
        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            setLayoutParams(params);
        }
    }
    public void setViewPager(ViewPager pager) {
        mViewPager = pager;
        mUserDefinedPageChangeListener = getOnPageChangeListener(pager);
        pager.setOnPageChangeListener(this);
        setIndicatorType(mIndicatorType);

    }
    public void setIndicatorType(IndicatorType indicatorType) {
        mIndicatorType = indicatorType;
        mIndicatorTypeChanged = true;
        if (mViewPager != null) {
            addIndicator(mViewPager.getAdapter().getCount());
        }
    }
    private ViewPager.OnPageChangeListener getOnPageChangeListener(ViewPager pager) {
        try {
            Field f = pager.getClass().getDeclaredField("mOnPageChangeListener");
            f.setAccessible(true);
            return (ViewPager.OnPageChangeListener) f.get(pager);
        }catch(NoSuchFieldException e) {
            e.printStackTrace();
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void removeIndicator() {
        removeAllViews();
    }
    private void addIndicator(int count) {
        removeIndicator();
        if (count <= 0) {
            return;
        }
        if (mIndicatorType == IndicatorType.ICON) {
            for (int i = 0; i < count; i ++) {
                ImageView iv = new ImageView(getContext());
                LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.leftMargin = mIndicatorSpacing;
                params.rightMargin = mIndicatorSpacing;
                iv.setImageDrawable(strokeDrawable);
                addView(iv, params);
            }
        }else if (mIndicatorType == IndicatorType.TEXT) {
            TextView tv = new TextView(getContext());
            tv.setTag(count);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            addView(tv, params);
        }
        updateIndicator(mViewPager.getCurrentItem());
    }
    private void updateIndicator(int position) {
        if(mIndicatorTypeChanged || mActivePosition != position) {
            mIndicatorTypeChanged = false;
            if (mIndicatorType == IndicatorType.ICON) {
                ((ImageView)getChildAt(mActivePosition)).setImageDrawable(strokeDrawable);
                ((ImageView) getChildAt(position)).setImageDrawable(solidDrawable);
            }else if (mIndicatorType == IndicatorType.TEXT) {
                TextView textView = (TextView) getChildAt(0);
                textView.setTextColor(mTextColor);
                textView.setTextSize(mTextSize);
                textView.setText(String.format("%d/%d", position + 1, (int) textView.getTag()));
            }
            mActivePosition = position;
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mUserDefinedPageChangeListener != null){
            mUserDefinedPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        updateIndicator(position);
        if (mUserDefinedPageChangeListener != null) {
            mUserDefinedPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mUserDefinedPageChangeListener != null) {
            mUserDefinedPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
