package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


import com.yskj.daishuguan.MainActivity;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/1/24
 *
 * @ClassName: SplashActivity
 * @Description: 闪屏
 */

public class SplashActivity extends BaseActivity {

    private final int TIMEALLLONG = 3000;
    private final int TIMELONG = 500;

    private TimeCount time;

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
        time = new TimeCount(TIMEALLLONG, TIMELONG);
        time.start();
    }

    @Override
    protected void initView() {
//
//        // 获取屏幕的高宽
//        Point outSize = new Point();
//        getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);
//
//        // 解析将要被处理的图片
//        Bitmap resourceBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_splash);
//
//        if (resourceBitmap == null) {
//            return;
//        }
//
//        // 开始对图片进行拉伸或者缩放
//
//        // 使用图片的缩放比例计算将要放大的图片的高度
//        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize.x * 1.0f / resourceBitmap.getWidth());
//
//        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
//        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize.x, bitmapScaledHeight, false);
//
//        iv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                //这里防止图像的重复创建，避免申请不必要的内存空间
//                if (scaledBitmap.isRecycled())
////必须返回true
//                    return true;
//
//
//                // 当UI绘制完毕，我们对图片进行处理
//                int viewHeight = iv.getMeasuredHeight();
//
//
//                // 计算将要裁剪的图片的顶部以及底部的偏移量
//                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;
//
//
//// 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
//                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(),
//                        scaledBitmap.getHeight() - offset * 2);
//
//
//                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
//                    scaledBitmap.recycle();
//                    System.gc();
//                }
//
//
//// 设置图片显示
//                iv.setBackgroundDrawable(new BitmapDrawable(getResources(), finallyBitmap));
//                return true;
//            }
//        });
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
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();
        }
    }
}
