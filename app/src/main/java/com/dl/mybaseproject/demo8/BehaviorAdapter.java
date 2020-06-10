package com.dl.mybaseproject.demo8;

import android.support.v7.widget.RecyclerView;
import com.dl.mybaseproject.R;
import com.dl.mybaseproject.main.MainDataBean;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

public class BehaviorAdapter extends BGARecyclerViewAdapter<MainDataBean> {

    public BehaviorAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.main_item);

    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, MainDataBean model) {
        helper.getImageView(R.id.iv_logo).setImageResource(model.getImgRes());
        helper.setText(R.id.tv_text, model.getContent());
        helper.setText(R.id.tv_name, "Demo" + (position + 1));
    }


    @Override
    public void onViewAttachedToWindow(BGARecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
}
