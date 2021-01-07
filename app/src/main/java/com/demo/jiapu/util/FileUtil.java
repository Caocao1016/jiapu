package com.demo.jiapu.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.IOException;


public class FileUtil {



    private static int MIN_EXTERNAL_MEMORY = 2000;


    private static String WRITE_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";



    /**
     * 判断SD卡是否可用
     */
    public static boolean isSDCardAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有读取SD卡权限
     *
     * @param context
     * @return
     */
    public static boolean isHasSDCardPermission(Context context) {
        int permission = context.checkCallingOrSelfPermission(WRITE_STORAGE_PERMISSION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 创建文件
     *
     * @param context
     * @param path
     * @return
     */
    public static File createNewFile(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        if (!isSDCardAvailable()) {
            Log.e("TAG", "SDCard Unavailable");
            return null;
        }
        if (!isHasSDCardPermission(context)) {
            Log.e("TAG",
                    "No android.permission.WRITE_EXTERNAL_STORAGE Permission");
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e("TAG", "Create New File Error");
                return null;
            }
        }
        return file;
    }


}
