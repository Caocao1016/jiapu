package com.demo.jiapu.base;

import com.socks.library.KLog;

public class MyApp extends BaseApp  {


    private static MyApp INStANCE;

    public MyApp() {
        INStANCE = this;
    }

    public static MyApp getInstance() {
        if (INStANCE == null) {
            synchronized (MyApp.class) {
                if (INStANCE == null) {
                    INStANCE = new MyApp();
                }
            }
        }
        return INStANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //**************************************相关第三方SDK的初始化等操作*************************************************
        KLog.init(true);//初始化KLog

    }


}

