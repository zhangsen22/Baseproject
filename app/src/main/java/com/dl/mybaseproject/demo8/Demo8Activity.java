package com.dl.mybaseproject.demo8;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;
import com.dl.common.base.BaseActivity;
import com.dl.common.recyclerview.RecyItemTouchHelperCallback;
import com.dl.common.utils.ToastUtil;
import com.dl.mybaseproject.R;
import com.dl.mybaseproject.demo8.demo1.BehaviorSimpleDemo1Activity;
import com.dl.mybaseproject.main.MainDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Demo8Activity extends BaseActivity {

    @BindView(R.id.behavior_title_name)
    TextView behaviorTitleName;
    @BindView(R.id.behavior_list)
    RecyclerView behaviorList;

    private List<MainDataBean> data;
    private BehaviorAdapter mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_demo8;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initData();
        initList();
    }

    private void initView() {
        behaviorTitleName.setText("Behavior's  Demos");
        data = new ArrayList<>();
    }

    private void initData() {
        data.add(new MainDataBean(R.mipmap.home_more, "自定义BehaviorSimpleDemo1", BehaviorSimpleDemo1Activity.class));

        for (int i = 0; i < 20; i++) {
            data.add(new MainDataBean(R.mipmap.home_more, "待续", BehaviorSimpleDemo1Activity.class));
        }
    }

    private void initList() {
        mainAdapter = new BehaviorAdapter(behaviorList);
        behaviorList.setAdapter(mainAdapter);
        mainAdapter.setData(data);
        RecyItemTouchHelperCallback itemTouchHelperCallback = new RecyItemTouchHelperCallback(mainAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(behaviorList);

        mainAdapter.setOnRVItemLongClickListener((parent, itemView, position) -> {
            ToastUtil.normal(position + "");
            return false;
        });
        mainAdapter.setOnRVItemClickListener((parent, itemView, position) -> mSwipeBackHelper.forward(data.get(position).getActivity()));
    }
}
