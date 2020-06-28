package com.dl.mybaseproject.demo10;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dl.common.base.BaseActivity;
import com.dl.mybaseproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Demo10Activity extends BaseActivity {
    @BindView(R.id.dbdbdb)
    CircleProgressImageView dbdbdb;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @Override
    public int getContentViewId() {
        return R.layout.activity_demo10;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.dbdbdb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dbdbdb:
                dbdbdb.startAnimator();
                break;
        }
    }
}
