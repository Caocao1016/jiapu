package com.yskj.daishuguan.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.baselibrary.base.BaseLazyFragment;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 支持沉浸式Fragment懒加载基类（默认不开启沉浸式）
 */
public abstract class UILazyFragment extends BaseLazyFragment implements OnTitleBarListener {

    private ImmersionBar mImmersionBar;//状态栏沉浸

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //初始化沉浸式状态栏
        if (isVisibleToUser() && isStatusBarEnabled() && isLazyLoad()) {
            statusBarConfig().init();
        }
        //初始化标题栏的监听
        if (getTitleBarId() > 0) {
            if (findViewById(getTitleBarId()) instanceof TitleBar) {
                ((TitleBar) findViewById(getTitleBarId())).setOnTitleBarListener(this);
            }
        }
        //设置标题栏
        if (getTitleBarId() > 0) {
            ImmersionBar.setTitleBar(mActivity, findViewById(getTitleBarId()));
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    public boolean isStatusBarEnabled() {
        return false;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    protected ImmersionBar getStatusBarConfig() {
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式
     */
    private ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
                .keyboardEnable(true);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        return mImmersionBar;
    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) mImmersionBar.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isStatusBarEnabled() && isLazyLoad()) {
            // 重新初始化状态栏
            statusBarConfig().init();
        }
    }

    /**
     * 标题栏左边的View被点击了
     */
    @Override
    public void onLeftClick(View v) {

    }


    /**
     * 标题栏中间的View被点击了
     */
    @Override
    public void onTitleClick(View v) {}

    /**
     * 标题栏右边的View被点击了
     */
    @Override
    public void onRightClick(View v) {}
}