package com.caosir.our.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.caosir.our.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import butterknife.ButterKnife;

public class NameCardActivity extends baseActivity implements View.OnClickListener{
    private final String TAG="NameCard";

    private final int QQRERWEIMA = 1;

    private final int WXERWEIMA = 0;


    @Bind(R.id.app_bar_l_name_card)
    AppBarLayout mAppBarLayout;

    private List<String> mDatas;
    @Bind(R.id.iv_tb_name_card)
    ImageView iv_tb_erweima;
    @Bind(R.id.iv_qq_erweima)
    ImageView iv_qq_erweima;

    @Bind(R.id.iv_wx_erweima)
    ImageView iv_Wx_erweima;

    @Bind(R.id.con_lay_qq)
    ConstraintLayout con_qq;

    @Bind(R.id.con_lay_wx)
    ConstraintLayout con_wx;

    private boolean isShowTBV ; //定义toolbar上面控件显示


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_card);
        ButterKnife.bind(this);

        setActivityAnimation();

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxSrocll= mAppBarLayout.getTotalScrollRange();
                float biLi= (float) Math.abs(verticalOffset)/maxSrocll;
                Log.d(TAG,""+biLi);
                setTitleShow(biLi);
            }
        });

        con_qq.setOnClickListener(this);
        con_wx.setOnClickListener(this);
        iv_tb_erweima.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setActivityAnimation(){
        Slide slideInto = new Slide();
        slideInto.setDuration(1000);
        slideInto.setSlideEdge(Gravity.RIGHT);
        slideInto.setInterpolator(new BounceInterpolator());

        getWindow().setEnterTransition(slideInto);

        Slide slideOut = new Slide();
        slideOut.setDuration(500);
        slideOut.setSlideEdge(Gravity.TOP);
        slideOut.setInterpolator(null);


        getWindow().setReturnTransition(slideOut);

    }




    private void setTitleShow(float biLi){
        if(!isShowTBV&&biLi>0.7){
            findViewById(R.id.text_title_name_card).setVisibility(View.VISIBLE);
            findViewById(R.id.text_image_name_card).setVisibility(View.INVISIBLE);
            findViewById(R.id.civ_tb_name_card).setVisibility(View.VISIBLE);
            isShowTBV = true;
        }else if(isShowTBV&&biLi<0.7){

            findViewById(R.id.text_title_name_card).setVisibility(View.INVISIBLE);
            findViewById(R.id.text_image_name_card).setVisibility(View.VISIBLE);
            findViewById(R.id.civ_tb_name_card).setVisibility(View.INVISIBLE);
            isShowTBV = false;
        }
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()){
                case R.id.con_lay_wx:
                    Intent intent2 = new Intent();
                    intent2.putExtra("what",WXERWEIMA);
                    intent2.setClass(this,ErWeiMActivity.class);
                    transitionTo(intent2,2);
                    break;
                case R.id.iv_tb_name_card:
                    Intent intent = new Intent();
                    intent.putExtra("what",WXERWEIMA);
                    intent.setClass(this,ErWeiMActivity.class);
                    transitionTo(intent,1);
                    break;
                case R.id.con_lay_qq:
                    Intent intent1 = new Intent();
                    intent1.putExtra("what",QQRERWEIMA);
                    intent1.setClass(this,ErWeiMActivity.class);
                    transitionTo(intent1,3);
                    break;

            }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void transitionTo(Intent intent,int i){
        ActivityOptions options = null;
        switch (i){
            case 1:
                options =ActivityOptions.makeSceneTransitionAnimation(this,iv_tb_erweima,"erweima");
                break;
            case 2:
                options =ActivityOptions.makeSceneTransitionAnimation(this,iv_Wx_erweima,"erweima");
                break;
            case 3:
                options =ActivityOptions.makeSceneTransitionAnimation(this,iv_qq_erweima,"erweima");
                break;

        }
        Pair.create(iv_tb_erweima,"erweima");
        startActivity(intent,options.toBundle());
    }

}
