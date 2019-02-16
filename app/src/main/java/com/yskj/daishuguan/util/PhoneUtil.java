package com.yskj.daishuguan.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.yskj.daishuguan.bean.PhoneBean;

import java.util.ArrayList;
import java.util.List;

/**
 * CaoPengFei
 * 2018/11/22
 *
 * @ClassName: PhoneUtil
 * @Description:
 */

public class PhoneUtil {

    //号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    //上下文对象
    private Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public PhoneUtil(Context context) {
        this.context = context;
    }

    //获取所有联系人
    public List<PhoneBean> getPhone() {
        List<PhoneBean> phoneDtos = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
        while (cursor.moveToNext()) {
            PhoneBean phoneDto = new PhoneBean(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
            phoneDtos.add(phoneDto);
        }
        return phoneDtos;
    }
}


