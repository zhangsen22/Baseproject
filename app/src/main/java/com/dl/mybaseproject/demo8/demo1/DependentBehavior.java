package com.dl.mybaseproject.demo8.demo1;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class DependentBehavior extends CoordinatorLayout.Behavior<View> {

    public DependentBehavior() {
    }

    public DependentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
