package com.sensetime.liveness.motion.view;

import android.os.CountDownTimer;

public class TimeViewContoller {

    private ITimeViewBase mTimeView;
    private CountDownTimer mCountDownTimer;
    private float mCurrentTime;
    private int mMaxTime;
    private boolean mStop;
    private CallBack mCallBack;

    /**
     * 构造方法.
     */
    public TimeViewContoller(ITimeViewBase view) {
        mTimeView = view;
        mMaxTime = mTimeView.getMaxTime();
        mCountDownTimer = new CountDownTimer(mMaxTime * 1000, 50) {

            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentTime = mMaxTime - millisUntilFinished / 1000.0f;
                mTimeView.setProgress(mCurrentTime);
            }

            @Override
            public void onFinish() {
                mTimeView.setProgress(mMaxTime);
                onTimeEnd();
            }
        };
    }

    public void stop() {
        mStop = true;
        mCountDownTimer.cancel();
    }

    public void start() {
        start(true);
    }

    /**
     * 开始计时.
     */
    public void start(boolean again) {
        if (!again) {
            if (!mStop) {
                return;
            }
            mStop = false;
            if (mCurrentTime > mMaxTime) {
                onTimeEnd();
                return;
            }
            mCountDownTimer.cancel();
            mCountDownTimer.start();
        } else {
            reset();
        }
    }

    private void onTimeEnd() {
        if (null != mCallBack) {
            mCallBack.onTimeEnd();
        }
        if (!mStop) {
            hide();
        }
    }

    public void setCallBack(CallBack callback) {
        mCallBack = callback;
    }

    private void reset() {
        mCurrentTime = 0;
        mTimeView.setProgress(mCurrentTime);
        show();
        mCountDownTimer.cancel();
        mCountDownTimer.start();
    }

    /**
     * 隐藏计时.
     */
    public void hide() {
        mStop = true;
        mCountDownTimer.cancel();
        mTimeView.hide();
    }

    public void show() {
        mStop = false;
        mTimeView.show();
    }

    public interface CallBack {
        void onTimeEnd();
    }
}
