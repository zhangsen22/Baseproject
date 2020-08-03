package com.dl.mybaseproject.demo11;

import android.os.Bundle;
import com.dl.common.base.BaseActivity;
import com.dl.mybaseproject.R;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Demo11Activity extends BaseActivity {
    @BindView(R.id.smail_layout)
    SmileLatout smailLayout;

    @Override
    public int getContentViewId() {
        return R.layout.activity_demo11;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.a);
        list.add(R.mipmap.b);
        list.add(R.mipmap.c);
        smailLayout.setView(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
