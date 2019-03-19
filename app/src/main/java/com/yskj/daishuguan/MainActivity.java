package com.yskj.daishuguan;


import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hjq.baselibrary.utils.OnClickUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.vondear.rxtool.RxAppTool;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;
import com.yskj.daishuguan.activity.LoginActivity;
import com.yskj.daishuguan.adapter.HomeViewPagerAdapter;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.dialog.AppVersionDialog;
import com.yskj.daishuguan.entity.evbus.FinshMoneyEvenbus;
import com.yskj.daishuguan.entity.evbus.QuanxianEvenbus;
import com.yskj.daishuguan.modle.AppVersionView;
import com.yskj.daishuguan.presenter.UpAppVersionPresenter;
import com.yskj.daishuguan.response.AppVersionResponse;
import com.yskj.daishuguan.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity<UpAppVersionPresenter> implements
        ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener, AppVersionView {

    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    private HomeViewPagerAdapter mAdapter;


    private MyLocationListener myListener = new MyLocationListener();
    private String mLatitude, mAddress = null;
    private LocationClient mLocationClient;
    private Location locationGoole;//gogle自带获取经纬度
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

        EventBus.getDefault().register(this);

        mViewPager.addOnPageChangeListener(this);
        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        initAdress();
        // 修复在 ViewPager 中点击 EditText 弹出软键盘导致 BottomNavigationView 还显示在 ViewPager 下面的问题
//        postDelayed(this, 1000);

    }


    @Override
    protected void initData() {
        mAdapter = new HomeViewPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
//        initUpdata();
    }



    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.menu);
                break;
//            case 2:
//                mBottomNavigationView.setSelectedItemId(R.id.home_message);
//                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.home_me);
                break;
        }
    }



    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.menu:
                if (RxSPTool.getString(this, Constant.IS_LOGIN) .equals("0")) {
                    UIUtils.showToast("请先去登录");
                    startActivity(LoginActivity.class);
                    return false;
                }
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.home_me:
                mViewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (OnClickUtils.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //销毁掉当前界面
                    finish();
                }
            }, 300);
        } else {
            UIUtils.showToastSafely(getResources().getString(R.string.home_exit_hint));
        }
    }

    @Override
    protected UpAppVersionPresenter createPresenter() {
        return new UpAppVersionPresenter(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (locationGoole != null) {
                mLatitude = locationGoole.getLongitude() + "|" + locationGoole.getLatitude();
            }
            if (mLatitude != null) {
                RxSPTool.putString(MainActivity.this,Constant.GPS_LATITUDE, mLatitude);
            }
            BigDecimal bigDecimal = new BigDecimal(location.getLongitude());
            BigDecimal bigDecima2 = new BigDecimal(location.getLatitude());

            RxLogTool.e("TAG", "首页经纬度" + bigDecimal.setScale(5, BigDecimal.ROUND_UP)
                    + "--" + bigDecima2.setScale(5, BigDecimal.ROUND_UP)+"--"+mAddress);
            mAddress = location.getAddrStr();
            RxSPTool.putString(MainActivity.this, Constant.GPS_ADDRESS, mAddress);
            if (!mAddress.isEmpty() && mAddress != null) {
                if (mLocationClient != null && mLocationClient.isStarted()) {
                    mLocationClient.stop();
                }
            }
        }
    }
    private void initAdress() {
        mAddress = RxSPTool.getString(MainActivity.this, Constant.GPS_ADDRESS);
        if (mAddress.isEmpty() || mAddress == null) {
            mLocationClient = new LocationClient(MainActivity.this);
            mLocationClient.registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
            option.setCoorType("bd09ll");
            option.setScanSpan(1000);
            option.setIsNeedAddress(true);
            mLocationClient.setLocOption(option);

            XXPermissions.with(MainActivity.this)
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_FINE_LOCATION,
                            Permission.CALL_PHONE, Permission.READ_PHONE_STATE,Permission.ACCESS_COARSE_LOCATION
                            , Permission.READ_EXTERNAL_STORAGE,
                            Permission.GET_ACCOUNTS)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            EventBus.getDefault().post(new QuanxianEvenbus());
                            if (isAll) {

                                mLocationClient.start();
                            } else {
                                UIUtils.showToast("获取权限成功，部分权限未正常授予");
                            }
                        }
                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                UIUtils.showToast("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(MainActivity.this);
                            } else {
                                UIUtils.showToast("获取权限失败");
                            }
                        }
                    });
        }
    }

    @Override
    public void onSuccess(AppVersionResponse response) {

        int appVersionCode = RxAppTool.getAppVersionCode(this);
        if (response.getAppVersion() > appVersionCode) {
            XXPermissions.with(this)
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                    .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE) //不指定权限则自动获取清单中的危险权限
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(MainActivity.this);
                                rxDialogSureCancel.setCancelable(false);
                                rxDialogSureCancel.getTitleView().setText(Constant.APP_UPDALE_TITLE);
                                rxDialogSureCancel.getCancelView().setText(Constant.APP_UPDALE_TRUE);
                                rxDialogSureCancel.getSureView().setTextColor(getResources().getColor(R.color.colorPrimary));
                                rxDialogSureCancel.getCancelView().setTextColor(getResources().getColor(R.color.colorPrimary));
                                rxDialogSureCancel.getSureView().setText(Constant.APP_UPDALE_FALSE);
                                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        exitApp();
                                    }
                                });
                                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rxDialogSureCancel.cancel();
                                        final AppVersionDialog rxAppVersionDialog = new AppVersionDialog(MainActivity.this, response.getAppDownloadUrl());
                                        rxAppVersionDialog.setCancelable(false);
                                        rxAppVersionDialog.show();
                                    }
                                });
                                rxDialogSureCancel.show();

                            } else {
                                ToastUtils.show("获取权限成功，部分权限未正常授予");
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                ToastUtils.show("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(MainActivity.this);
                            } else {
                                ToastUtils.show("获取权限失败");
                            }
                        }
                    });


        }
    }

    @Override
    public void onError() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinshMoneyEvenbus(FinshMoneyEvenbus event) {
        mViewPager.setCurrentItem(event.number);
    }
}
