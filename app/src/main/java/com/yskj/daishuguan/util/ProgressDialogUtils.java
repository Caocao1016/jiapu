package com.yskj.daishuguan.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class ProgressDialogUtils {
    private static ProgressDialog mprogressDialog;
    public static void showDialogYuan(Context context,String s){
            mprogressDialog =new ProgressDialog(context);
            mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mprogressDialog.setMessage(s);
            //回退都点不掉
           // mprogressDialog.setCancelable(false);
            //外围点不掉，回退可以点掉
            mprogressDialog.setCanceledOnTouchOutside(false);
        mprogressDialog.show();
    }
    public static void showDialogChang(Context Context,String s){

    }
    public static void dismissDialog(Context context){
        if (null!=mprogressDialog){
          //  mprogressDialog =new ProgressDialog(context);
            mprogressDialog.dismiss();
        }

    }
}
