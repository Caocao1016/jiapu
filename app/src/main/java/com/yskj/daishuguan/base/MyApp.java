package com.yskj.daishuguan.base;

import android.os.Build;

import com.moxie.client.manager.MoxieSDK;
import com.socks.library.KLog;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.vondear.rxtool.RxTool;
import me.jessyan.autosize.AutoSize;

public class MyApp extends BaseApp  {

    public static MyApp app;
    public static  String WEIXIN_APP_ID = "wx5129f0f46fc9ef4e";
    public static    String WEIXIN_APP_SECRET = "111000440eb35cbc31bbf0a35cfaed21";
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
        MoxieSDK.init(this);
    }
    //获取单例
    public static MyApp getInstance(){
        if (app==null){
            app=new MyApp();
        }
        return app;
    }
    //设备编号系统
    public String getBrand(){
        String brand = Build.BRAND+" "+Build.MODEL;
        if (brand.length()>31){
            return brand.substring(0,31);
        }
        return brand;
    }
}

