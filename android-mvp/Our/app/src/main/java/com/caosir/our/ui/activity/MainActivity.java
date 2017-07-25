package com.caosir.our.ui.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.caosir.our.R;
import com.caosir.our.util.universal.MyAnimation;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static String TAG="MainActivty";

    //控制ToolBar变量
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    @Bind(R.id.main_iv_placeholder)
    ImageView mIvPlaceholder ; //大图片

    @Bind(R.id.main_ll_title_container)
    LinearLayout mLiTitleContainer ; // titile 的LinearLayout
    @Bind(R.id.main_fl_title)
    FrameLayout mFlTitleContainer; // Title的FrameLayout

    @Bind(R.id.main_abl_app_bar)
    AppBarLayout mAblAppBar; // 整个可以滑动的AppBar

    @Bind(R.id.main_tv_toolbar_title)
    TextView mTvToolbarTitle; // 标题栏Title

    @Bind(R.id.main_tb_toolbar)
    Toolbar mTbToolbar; // 工具栏

    @Bind(R.id.main_mune)
    FloatingActionsMenu floatingActionsMenu ;


    @Bind(R.id.our_menu_item_namecard)
    FloatingActionButton item_namecard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our);
        //ButterKnife 是一个专注宇android系统的view注入框架
        //可以减少findViewById和SetOncliceListener 可视化一键生城
        ButterKnife.bind(this);

        //设置activity动画
        setAnimationActivity();
        /**
         * 说明:设置滑动逻辑设置两个动画，监听AppBar的滑动，
         * 处理ToolBar 和 Title 的显示自动滑动效果*/
        mTbToolbar.setTitle("");

        //AppBar的监听
        mAblAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();  //获取最大的滑动高度
                float percentage = (float) Math.abs(verticalOffset)/(float)maxScroll; //滑动的程度
                Log.d(TAG,""+percentage);


                handleAlphaOnTitle(percentage);
                handleToolbarTitleVisibility(percentage);
            }
        });

        initParallaxValues(); // 自动滑动效果

        item_namecard.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private  void setAnimationActivity(){
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.BOTTOM);
        slideTransition.setDuration(800);
        slideTransition.setInterpolator(new DecelerateInterpolator()); //设置动画变化率 动画结束的时候弹起

        getWindow().setReenterTransition(slideTransition); //从新进入
        getWindow().setEnterTransition(slideTransition); //进入动画
       // getWindow().setExitTransition(slideTransition);

    }

    /**
     * 说明:设置自动滑动的动画效果
    *@author: caosir
    *@time：2017/7/17
    *@param：
    *@return
    */
    private void initParallaxValues (){
        //获取大图片的上层布局
        CollapsingToolbarLayout.LayoutParams petDetailsLp  = (CollapsingToolbarLayout.LayoutParams) mIvPlaceholder.getLayoutParams();
        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();
        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);
        mIvPlaceholder.setLayoutParams(petDetailsLp);
        mFlTitleContainer.setLayoutParams(petBackgroundLp);
    }
    //处理ToolBar的显示
    private void handleToolbarTitleVisibility(float percentage){
        //当滑动超过0.9的时候开始显示
        if(percentage>=PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR){
            //判断text是否可视化
            if(!mIsTheTitleVisible){
                //设置标题显示
                Log.d(TAG,"Toolbar显示200ms");
                MyAnimation.startAlphaAnimation(mTvToolbarTitle,ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                MyAnimation.startAlphaAnimation(floatingActionsMenu,ALPHA_ANIMATIONS_DURATION,View.VISIBLE);
                mTvToolbarTitle.setText("我的基本情况");
                mIsTheTitleVisible = true;
            }

        }
        else{
            if(mIsTheTitleVisible){
                Log.d(TAG,"Toolbar隐藏200ms");
                mTvToolbarTitle.setText("");
                //动画
                MyAnimation.startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                MyAnimation.startAlphaAnimation(floatingActionsMenu, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }
    // 控制Title的显示
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS ) {
            if (mIsTheTitleContainerVisible) {
                //
                MyAnimation.startAlphaAnimation(mLiTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                MyAnimation.startAlphaAnimation(mLiTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;

            }
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.our_menu_item_namecard:
            {
                menuItemNameCardOnClik();
                break;
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void menuItemNameCardOnClik(){
        Intent intent = new Intent();
        intent.setClass(this,NameCardActivity.class);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent,transitionActivityOptions.toBundle());
    }


}
