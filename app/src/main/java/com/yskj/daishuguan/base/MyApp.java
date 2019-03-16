package com.yskj.daishuguan.base;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.moxie.client.manager.MoxieSDK;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.vondear.rxtool.RxTool;
import me.jessyan.autosize.AutoSize;

public class MyApp extends BaseApp  {

    public static MyApp app;
    public static  String WEIXIN_APP_ID = "wx1bdba6acb5ccd3bf";
    public static    String WEIXIN_APP_SECRET = "93a1ed048621eaa6012aa1c083e519d7";

    //分享
    static {
        //微信
        PlatformConfig.setWeixin(WEIXIN_APP_ID, WEIXIN_APP_SECRET);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //**************************************相关第三方SDK的初始化等操作*************************************************
        KLog.init(true);//初始化KLog
        RxTool.init(this);
        AutoSize.initCompatMultiProcess(this);
 //5c678385b465f565fd000b5b
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this,"5847ad2a07fe655886000b3d"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        // 选用AUTO页面采集模式
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
//        MoxieSDK.init(this);
    }
    //获取单例
    public static MyApp getInstance(){
        if (app==null){
            app=new MyApp();
        }
        return app;
    }
    //deviceId：设备编号 IEMI
    public String getIMEI() {
        String devicedId = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            devicedId = telephonyManager.getDeviceId();
            if (null==devicedId){
                devicedId= Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
            }
            //LogUtil.d("flag","IMEI:"+devicedId);
            return devicedId;
        }catch (Exception e){
            e.printStackTrace();
            if (null==devicedId){
                devicedId= Settings.System.getString(getContentResolver(), Settings.System.DEVICE_PROVISIONED);
            }
            return devicedId;
        }
    }
}

