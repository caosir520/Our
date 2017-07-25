package com.caosir.our.util.universal.mybehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建人: caosir
 * 创建时间：2017/7/20
 * 修改人：
 * 修改时间：
 * 类说明：
 */

public class TitleImageBehavior extends CoordinatorLayout.Behavior<View> {



    public TitleImageBehavior(Context context, AttributeSet attrs){
            super(context,attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Toolbar;
    }



}
