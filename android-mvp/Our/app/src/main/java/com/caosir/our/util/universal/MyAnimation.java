package com.caosir.our.util.universal;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * 创建人: caosir
 * 创建时间：2017/7/17
 * 修改人：
 * 修改时间：
 * 类说明： 通用动画 。
 */

public class MyAnimation {
    /**
     * 说明:设置渐变动画
    *@author: caosir
    *@time：2017/7/17
    *@param：
    *
    */
    public static  void startAlphaAnimation (View v,long duration ,int visibility){
        //创建透明度渐变动画
        AlphaAnimation alphaAnimation = (visibility==View.VISIBLE)? new AlphaAnimation(0f,1f):new AlphaAnimation(1f,0f);
        //设置持续时间
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
