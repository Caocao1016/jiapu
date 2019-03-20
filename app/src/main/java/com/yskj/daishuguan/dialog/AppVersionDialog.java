package com.yskj.daishuguan.dialog;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.callback.FileCallback;

import com.lzy.okgo.OkGo;
import com.vondear.rxtool.RxAppTool;
import com.vondear.rxui.view.RxProgressBar;
import com.vondear.rxui.view.dialog.RxDialog;
import com.yskj.daishuguan.R;

import java.io.File;

/**
 * CaoPengFei
 * 2018/11/30
 *
 * @ClassName: AppVersionDialog
 * @Description:
 */

public class AppVersionDialog extends RxDialog {

    private ImageView mIvLogo;
    private TextView mTvContent;
    private TextView mTvSure;
    private TextView mTvCancel;
    private TextView mTvTitle;
    private int appForce;


    public AppVersionDialog(Activity context, String url, int appForce) {
        super(context);
        initView(context, url, appForce);
    }

    private void initView(Activity context, String url, int appForce) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update, null);
        RxProgressBar mRoundFlikerbar = (RxProgressBar) dialogView.findViewById(R.id.round_flikerbar);
        setContentView(dialogView);


        OkGo.get("http://wxz.myapp.com/16891/0669F69E8F4A697A8E66FDB86C06A06F.apk?fsname=com.tencent.mobileqq_7.9.0_954.apk&hsr=4d5s").tag(this)
                .execute(new FileCallback() {
                    public void onSuccess(File file, okhttp3.Call call, okhttp3.Response response) {
                        if (appForce == 0) {
                            dismiss();
                        }
                        //安装apk
                        RxAppTool.installApp(context, file);
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);

                        //取消所有请求
                        OkGo.getInstance().cancelAll();
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                        //total: 总大小
                        //current:当前下载的大小
                        //isUploading:标记是否是上传, false
                        int percent = (int) (currentSize * 100 / totalSize);
                        mRoundFlikerbar.setProgress(percent);
                    }
                });
    }


    private static void installApk(File file, Activity activity) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //兼容android7.0 使用共享文件的形式
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }


        //startActivity(intent);
        activity.startActivity(intent);
    }

}

