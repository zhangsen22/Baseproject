package com.dl.mybaseproject.demo5;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义behavior 用于控制appbar的滑动
 * 当tabLayout吸顶后 使下滑不触发appBar的滑动 使下拉刷新更顺畅
 */
public class NoScrollBehavior extends AppBarLayout.Behavior{

    public boolean noScroll = false;


    public NoScrollBehavior() {
    }

    public NoScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isNoScroll() {
        return noScroll;
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = true;
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        if (noScroll) {
            return false;
        } else {
            return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
        }


    }
}
