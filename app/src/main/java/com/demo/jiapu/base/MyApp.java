package com.demo.jiapu.base;


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


    }


}

