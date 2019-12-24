package com.dl.mybaseproject.demo5;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.dl.common.adapter.TabPageIndicatorAdapter;
import com.dl.common.base.BaseActivity;
import com.dl.common.utils.DisplayUtil;
import com.dl.common.utils.ToastUtil;
import com.dl.mybaseproject.R;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Demo5Activity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tab_layout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.snsns)
    ImageView snsns;
    @BindView(R.id.sjjsksksk)
    LinearLayout sjjsksksk;
    @BindView(R.id.sjjsajasjasjsajsjs)
    TextView sjjsajasjasjsajsjs;
//    @BindView(R.id.aaaaaaaaaa)
//    DisInterceptNestedScrollViewaaaa aaaaaaaaaa;
    private int lastState = 1;
    private List<String> list_title;
    private List<Integer> list_id;
    private List<Fragment> list_fragment;
    private TabPageIndicatorAdapter indicatorAdapter;
    //    private NoScrollBehavior myAppBarLayoutBehavoir;
    private Demo5ItemFragment currentFragment;

    @Override
    public int getContentViewId() {
        return R.layout.demo5_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initTab();
        initListener();
    }


    private void initView() {
        StatusBarUtil.setTranslucentForImageView(mActivity, 50, null);

        CollapsingToolbarLayout.LayoutParams lp1 = (CollapsingToolbarLayout.LayoutParams) titleLayout.getLayoutParams();
        lp1.topMargin = DisplayUtil.getStatusBarHeight(mActivity);
        titleLayout.setLayoutParams(lp1);

        //防止toolbar顶入状态栏
        CollapsingToolbarLayout.LayoutParams lp2 = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        lp2.topMargin = DisplayUtil.getStatusBarHeight(mActivity);
        toolbar.setLayoutParams(lp2);
    }

    private void initListener() {


        appBar.addOnOffsetChangedListener(this);

        AppBarLayoutOverScrollViewBehavior myAppBarLayoutBehavoir = (AppBarLayoutOverScrollViewBehavior)
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        myAppBarLayoutBehavoir.setOnProgressChangeListener(new AppBarLayoutOverScrollViewBehavior.onProgressChangeListener() {
            @Override
            public void onProgressChange(float progress, boolean isRelease) {
//                Log.e("TAG","isRelease    "+isRelease);
//                progressBar.setProgress((int) (progress * 360));
//                if (!isRelease) {
//                    aaaaaaaaaa.setLanJie(false);
//                }else {
//                    aaaaaaaaaa.setLanJie(true);
//                }
//                if (progress == 1 && !progressBar.isSpinning && isRelease) {
//                     刷新viewpager里的fragment
//                }
//                if (mMsgIv != null) {
//                    if (progress == 0 && !progressBar.isSpinning) {
//                        mMsgIv.setVisibility(View.VISIBLE);
//                    } else if (progress > 0 && mSettingIv.getVisibility() == View.VISIBLE) {
//                        mMsgIv.setVisibility(View.INVISIBLE);
//                    }
//                }
            }
        });
    }

    private void initTab() {
        //初始化tab
        list_title = new ArrayList<>();
        list_title.add("军事");
        list_title.add("娱乐");
        list_title.add("萌宠");
        list_title.add("生活");

        list_id = new ArrayList<>();
        list_id.add(1);
        list_id.add(2);
        list_id.add(3);
        list_id.add(4);

        list_fragment = new ArrayList<>();
        for (int i = 0; i < list_id.size(); i++) {
            Demo5ItemFragment orderItemFragment = Demo5ItemFragment.newInstance(list_id.get(i));
            list_fragment.add(orderItemFragment);
        }
        currentFragment = (Demo5ItemFragment) list_fragment.get(0);
        indicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), list_fragment, list_title);
        viewpager.setAdapter(indicatorAdapter);
        tabLayout.setupWithViewPager(viewpager);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appBar.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.e("TAG","verticalOffset     "+verticalOffset);
//        if(verticalOffset<0){
//                aaaaaaaaaa.setLanJie(true);
//        }else {
//                aaaaaaaaaa.setLanJie(false);
//        }


        //顶部渐变 标题栏处理
        float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
        int alpha = (int) (255 * percent);
        titleLayout.setBackgroundColor(Color.argb(alpha, 26, 128, 210));
        tvTitle.setAlpha(alpha);
//        bgImg.setBackgroundColor(Color.argb(alpha, 26, 128, 210));
        if (percent < 0.5) {
            rlBack.setBackgroundResource(R.drawable.oval_shadow);
        } else {
            rlBack.setBackground(null);
        }

        //滑动事件处理
//        if (percent == 0) {
//            当完全展开时  appbar可滑动  禁止refresh(可根据需求不禁止刷新)
//            myAppBarLayoutBehavoir.setNoScroll(false);
//            currentFragment.setRefreshState(false);
//        } else if (percent == 1) {
//            当完全折叠时  appbar不可滑动使tab吸顶   允许refresh
//            currentFragment.setRefreshState(true);
//            myAppBarLayoutBehavoir.setNoScroll(true);
//        } else {
//            滑动中 appbar可滑动 禁止refresh(建议禁止刷新,否则会appbar影响滑动流畅)
//            myAppBarLayoutBehavoir.setNoScroll(false);
            currentFragment.setRefreshState(false);
//        }
    }


    @Override
    public void onBackPressed() {

        //返回监听 当appBar处于不可滑动(即完全折叠)时，先释放appBar
//        if (myAppBarLayoutBehavoir.isNoScroll()) {
//            myAppBarLayoutBehavoir.setNoScroll(false);
//        appBar.setExpanded(true, true);
//        } else {
        super.onBackPressed();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * toolbar
     * 获取状态栏高度
     * ！！这个方法来自StatusBarUtil,因为作者将之设为private，所以直接copy出来
     *
     * @param context context
     * @return 状态栏高度
     */
    private int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    @OnClick({R.id.sjjsksksk, R.id.sjjsajasjasjsajsjs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sjjsksksk:
                ToastUtil.success("我是轮播图");
                break;
            case R.id.sjjsajasjasjsajsjs:
                ToastUtil.success("w我是文字");
                break;
        }
    }
}
