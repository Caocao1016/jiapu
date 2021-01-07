package com.demo.jiapu.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BasePresenter;
import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/1/24s
 *
 * @ClassName: SplashActivity
 * @Description: 闪屏
 */

public class SplashActivity extends BaseActivity {


    private final int TIMEALLLONG = 3000;
    private final int TIMELONG = 100;

    private TimeCount time;


    @BindView(R.id.title_iv)
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
        time = new TimeCount(TIMEALLLONG, TIMELONG);
        time.start();
    }

    @Override
    protected void initView() {
//        LayoutInflater.from(this).inflate()
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.cancel();
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
            startActivity(new Intent(SplashActivity.this, ViewHomeActivity.class));
        }
    }
}
