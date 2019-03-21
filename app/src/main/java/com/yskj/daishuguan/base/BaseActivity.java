package com.yskj.daishuguan.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.vondear.rxui.view.dialog.RxDialogLoading;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.listener.PermissionListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * CaoPengFei
 * 2018/11/20
 *
 * @ClassName: BaseActivity
 * @Description: 项目中的Activity基类
 */

public abstract class BaseActivity<T extends BasePresenter>  extends UIActivity
        implements OnTitleBarListener {

    private Unbinder mButterKnife;//View注解
    protected T mPresenter;
    private static Activity mCurrentActivity;// 对所有activity进行管理
    public static List<Activity> mActivities = new LinkedList<Activity>();
    public PermissionListener mPermissionListener;
    public RxDialogLoading rxDialogLoading;
    @Override
    public void init() {
        rxDialogLoading = new RxDialogLoading(this);
        rxDialogLoading.setLoadingColor(R.color.cl_F34F03);
        //初始化标题栏的监听
        if (getTitleBarId() > 0) {
            if (findViewById(getTitleBarId()) instanceof TitleBar) {
                ((TitleBar) findViewById(getTitleBarId())).setOnTitleBarListener(this);
            }
        }
        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            mActivities.add(this);
        }

        mPresenter = createPresenter();
        mButterKnife = ButterKnife.bind(this);

        initOrientation();

        super.init();
    }

    /**
     * 初始化横竖屏方向，会和 LauncherTheme 主题样式有冲突，注意不要同时使用
     */
    protected void initOrientation() {
        //如果没有指定屏幕方向，则默认为竖屏
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();
    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(int titleId) {
        setTitle(getText(titleId));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TitleBar titleBar = getTitleBar();
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    protected TitleBar getTitleBar() {
        if (getTitleBarId() > 0 && findViewById(getTitleBarId()) instanceof TitleBar) {
            return findViewById(getTitleBarId());
        }
        return null;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return true;
    }

    /**
     * {@link OnTitleBarListener}
     */

    /**
     * 标题栏左边的View被点击了
     */
    @Override
    public void onLeftClick(View v) {
        onBackPressed();
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

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mButterKnife != null) mButterKnife.unbind();


        //销毁的时候从集合中移除
        synchronized (mActivities) {
            mActivities.remove(this);
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
    /**
     * 退出应用的方法
     */
    public static void exitApp() {

        ListIterator<Activity> iterator = mActivities.listIterator();

        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }
    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    /**
     * 申请运行时权限
     */
    public void requestRuntimePermission(String[] permissions, PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            permissionListener.onGranted();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0) {
//                    List<String> deniedPermissions = new ArrayList<>();
//                    for (int i = 0; i < grantResults.length; i++) {
//                        int grantResult = grantResults[i];
//                        String permission = permissions[i];
//                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                            deniedPermissions.add(permission);
//                        }
//                    }
//                    if (deniedPermissions.isEmpty()) {
//                        mPermissionListener.onGranted();
//                    } else {
//                        mPermissionListener.onDenied(deniedPermissions);
//                    }
//                }
//                break;
//        }
//    }
}