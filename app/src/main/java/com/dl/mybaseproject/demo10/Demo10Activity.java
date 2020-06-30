package com.dl.mybaseproject.demo10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.dl.common.base.BaseActivity;
import com.dl.mybaseproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Demo10Activity extends BaseActivity {
    @BindView(R.id.dbdbdb)
    CircleProgressImageView dbdbdb;
    @BindView(R.id.progress)
    RoundCornerProgressBar customPathProgressBar;
    @BindView(R.id.dghdhdhdhdhdhhdhdhdh)
    BGAProgressBar dghdhdhdhdhdhhdhdhdh;
    @BindView(R.id.pb_main_demo7)
    BGAProgressBar pbMainDemo7;

    private Handler mUiHandler = new Handler(Looper.getMainLooper());
    protected int progress;

    @Override
    public int getContentViewId() {
        return R.layout.activity_demo10;
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        customPathProgressBar.setProgress(0);
//        pbMainDemo7.setProgress(0);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (progress == 3600) {
                                progress = 0;
                            }
                            progress += 1;
//                            customPathProgressBar.setProgress(progress);
                            dghdhdhdhdhdhhdhdhdh.setProgress(progress);
//                            pbMainDemo7.setProgress(progress);
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
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
