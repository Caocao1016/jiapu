package com.yskj.daishuguan.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by Administrator on 2017/12/15.
 */

public class PermissionsUtils {

    public static String getContacts(Context context){
        StringBuffer contacts = new StringBuffer();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name1 = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (null!=name1){
                    String name = name1.replaceAll(" ", "");
                    //第一条不用换行
                    contacts.append(name).append(":");
                }
                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    String phoneNumber1 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if (null!=phoneNumber1){
                        // 添加Phone的信息
                        String phoneNumber = phoneNumber1.replaceAll(" ", "");
                        contacts.append(phoneNumber).append(",");
                    }
                }
                contacts.append("|");
                phones.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        contacts = getSimContacts("content://icc/adn",contacts , context);//一般用这一条
        String s = contacts.toString();
        if (null!=contacts&&contacts.length()>=1){
            return s;
        }
        return null;
    }
    private static StringBuffer getSimContacts(String str , StringBuffer sb, Context context) {
        ContentResolver cr = context.getContentResolver();
        Intent intent = new Intent();
        intent.setData(Uri.parse(str));
        Uri uri = intent.getData();
        try {
            Cursor cursor = cr.query(uri, null,
                    null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 取得联系人名字
                    int nameFieldColumnIndex = cursor.getColumnIndex("name");
                    String string = cursor.getString(nameFieldColumnIndex);
                    if (null!=string){
                        String stringReplaceAll = string.replaceAll(" ", "");
                        sb.append(stringReplaceAll).append(":");
                    }
                    // 取得电话号码
                    int numberFieldColumnIndex = cursor.getColumnIndex("number");
                    String phone = cursor.getString(numberFieldColumnIndex);
                    if (null!=phone){
                        String phoneReplace = phone.replaceAll(" ", "");
                        sb.append(phoneReplace).append(",");
                    }
                    sb.append("|");
                }
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb;
    }
    /*public static boolean checkCameraPermission(Activity activity) {
        int cameraPermissionState = ContextCompat.checkSelfPermission(activity, CAMERA);

        boolean cameraPermissionGranted = cameraPermissionState == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermissionGranted) {
            activity.requestPermissions(PermissionsConstant.PERMISSIONS_CAMERA,
                    PermissionsConstant.REQUEST_CAMERA);
        }
        return cameraPermissionGranted;
    }
    public static boolean checkWriteStoragePermission(Activity activity) {

        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!writeStoragePermissionGranted) {
            activity.requestPermissions(PermissionsConstant.PERMISSIONS_EXTERNAL_WRITE,
                    PermissionsConstant.REQUEST_EXTERNAL_WRITE);
        }
        return writeStoragePermissionGranted;
    }*/

}
