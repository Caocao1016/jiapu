package com.yskj.daishuguan.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: FileUtil
 * @Description:
 */

public class FileUtil {

    private static String WRITE_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static File f;
    private static int MIN_EXTERNAL_MEMORY = 2000;
    public static File saveMyBitmap(Bitmap mBitmap, Context mContext, String pacth, String name, boolean isShow) {

        if (FileUtil.isHasSDCardPermission(mContext)){

            if (FileUtil.isSDCardAvailable()) {
                if (!FileUtil.isExternalMemoryEnough()) {
                    return null;
                }
                f = new File(pacth, name);
            } else {
                return null;
            }
        }else {
            return null;
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();
            return f ;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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
     * 判断SD卡可用空间是否足够
     *
     * @return
     */
    public static boolean isExternalMemoryEnough() {
        if (!isSDCardAvailable()) {
            return false;
        }
        if (getAvailableExternalMemorySize() > MIN_EXTERNAL_MEMORY) {
            return true;
        }

        return false;
    }



    /**
     * 获取手机外部可用空间大小
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (!isSDCardAvailable()) {
            return -1;
        }
        String path = Environment.getExternalStorageDirectory().getPath();
        StatFs statFs = new StatFs(path);
        long blocks = statFs.getAvailableBlocks();
        long size = statFs.getBlockSize();
        return blocks * size;
    }
    /**
     * 判断SD卡是否可用
     */
    public static boolean isSDCardAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
