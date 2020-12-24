package com.demo.jiapu.activity;

import android.os.CountDownTimer;
import android.widget.ImageView;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BasePresenter;
import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/1/24
 *
 * @ClassName: SplashActivity
 * @Description: 闪屏
 */

public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
        }
    }
}
