package com.demo.jiapu.base;


public class MyApp extends BaseApp  {


    private static MyApp INSTACNE;

    public MyApp() {
        INSTACNE = this;
    }

    public static MyApp getInstance() {
        if (INSTACNE == null) {
            synchronized (MyApp.class) {
                if (INSTACNE == null) {
                    INSTACNE = new MyApp();
                }
            }
        }
        return INSTACNE;
    }

}

