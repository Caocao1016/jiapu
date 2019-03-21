package com.yskj.daishuguan.activity;

import android.Manifest;
import android.app.Activity;
import android.app.UiAutomation;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.baselibrary.widget.ClearEditText;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.adapter.WindowAdapter;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.util.PermissionsUtils;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hjq.toast.ToastUtils.show;

/**
 * CaoPengFei
 * 2019/2/11
 *
 * @ClassName: CerPhoneActivity
 * @Description: 联系人认证项
 */

public class CerPhoneActivity extends BaseActivity {

    @BindView(R.id.cl_name)
    ClearEditText mName;

    @BindView(R.id.tv_phone)
    TextView mPhone;
    @BindView(R.id.tv_relation)
    TextView mRelation;
    private List<String> mList = new ArrayList<>();
    private String mContacts;//联系人
    private String emergeName;//选择的联系人
    private static final int CONNECT_PARENT = 0x5;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cer_phone;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_phone_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mList.add("朋友/同事/同学");
        mList.add("直系亲属");
        mList.add("非直系亲属");
    }

    @OnClick({R.id.ll_phone, R.id.tv_sure, R.id.ll_relation})
    public void onClick(View view) {
        if (view.getId() == R.id.ll_phone) {
            AndPermission.with(this)
                    .permission(Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS,Manifest.permission.GET_ACCOUNTS)
                    // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
                    .rationale((context, permissions, executor) -> {
                        // 此处可以选择显示提示弹窗
//                        executor.execute();
                        UIUtils.showToast("请去权限管理页面授权相关权限");
                    })
                    // 用户给权限了
                    .onGranted(permissions ->

                    {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, CONNECT_PARENT);
                        mContacts = PermissionsUtils.getContacts(CerPhoneActivity.this);

                    })
                    // 用户拒绝权限，包括不再显示权限弹窗也在此列
                    .onDenied(permissions -> {
                        // 判断用户是不是不再显示权限弹窗了，若不再显示的话进入权限设置页
                        if (AndPermission.hasAlwaysDeniedPermission(CerPhoneActivity.this, permissions)) {
                            // 打开权限设置页
                            AndPermission.permissionSetting(CerPhoneActivity.this).execute();
                            return;
                        }
                        UIUtils.showToast("用户拒绝权限");
                    })
                    .start();

//
//            XXPermissions.with(this)
//                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                    .permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
//                    .permission(Permission.READ_CONTACTS, Permission.WRITE_CONTACTS, Permission.GET_ACCOUNTS)
//                    .request(new OnPermission() {
//
//                        @Override
//                        public void hasPermission(List<String> granted, boolean isAll) {
//                            if (isAll) {
//                                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                                startActivityForResult(intent, CONNECT_PARENT);
//                                mContacts = PermissionsUtils.getContacts(CerPhoneActivity.this);
//                            } else {
//                                show("获取权限成功，部分权限未正常授予");
//                            }
//                        }
//
//                        @Override
//                        public void noPermission(List<String> denied, boolean quick) {
//                            if (quick) {
//                                show("被永久拒绝授权，请手动授予权限");
//                                //如果是被永久拒绝就跳转到应用权限系统设置页面
//                                XXPermissions.gotoPermissionSettings(CerPhoneActivity.this);
//                            } else {
//                                show("获取权限失败");
//                            }
//                        }
//                    });
        } else if (view.getId() == R.id.tv_sure) {
            String lianXiRen = mPhone.getText().toString();
            String re = mRelation.getText().toString();

            if (StringUtil.isEmpty(re)) {
                UIUtils.showToast("请选择紧急联系人关系!");
                return;
            }
            if (StringUtil.isEmpty(lianXiRen)) {
                UIUtils.showToast("请先选择紧急联系人!");
                return;
            }
            renZhengContact();
        } else {
            showPopwindow();
        }
    }

    private void renZhengContact() {

        String emergeMobile = mPhone.getText().toString();
        String name = mName.getText().toString();
        String relation = mRelation.getText().toString();
        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.contact);
        params.setMethod(HttpMethod.POST);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("contacts", mContacts);
        bMap.put("emergencyname", name);
        bMap.put("linkmanRelationship", relation);
//        bMap.put("usualName", name);
        bMap.put("emergencymobile", emergeMobile);

        BaseParams.getParams(bMap);

        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key).toString());
        }
        params.addBodyParameter("contacts", mContacts);

        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String retmsg = jsonObject.getString("retmsg");
                    int retcode = jsonObject.getInt("retcode");
                    if (1000 == retcode) {
                        finish();
                    } else {
                        UIUtils.showToast(retmsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinished() {
                rxDialogLoading.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }


            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            RxLogTool.d("flag", "联系人:" + mContacts);
            Uri contactData = data.getData();
            Cursor c = getContentResolver().query(contactData, null, null, null, null);
            if (null == c) {
                UIUtils.showToast("请您先去手机设置里开启通讯录权限吧!");
                return;
            }
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                String contactPhone;
                try {
                    contactPhone = this.getContactPhone(c);
                    String[] namePhone = contactPhone.split(",");
                    DisplayMetrics metric = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metric);
                    if (namePhone == null || namePhone.length == 0) {
                        UIUtils.showToast("打开通讯录权限，继续认证吧！");
                        return;
                    } else {
                        mPhone.setText(namePhone[0]);
                        mName.setText(namePhone[1]);
                        emergeName = namePhone[1];
                        RxLogTool.d("flag", "选择的利息人" + emergeName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                UIUtils.showToast("打开通讯录权限，继续认证吧！");
            }
        }

    }

    /*获取手机联系人电话*/
    private String getContactPhone(Cursor cursor) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);

        String phoneResult = "";
        System.out.print(phoneNum);
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            if (phones.moveToFirst()) {
                for (; !phones.isAfterLast(); phones.moveToNext()) {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(typeindex);
                    String phoneNumber = phones.getString(index).replace("-", "").replace(" ", "").trim();
                    if (phoneNumber.startsWith("+86")) {
                        phoneNumber.replace("+86", "");
                    }
                    if (phoneNumber.startsWith("86")) {
                        phoneNumber.replace("86", "");
                    }
                    if (phoneNumber.startsWith("")) {
                        phoneNumber.replace("", "请选择有效联系人");
                    }
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    if (name.startsWith("")) {
                        name.replace("", "请选择有效联系人");
                    }
                    phoneResult = phoneNumber + "," + name;
                    //  LogUtil.d("flag",phoneResult);
                    break;
                }
            }
            if (!phones.isClosed()) {
                phones.close();
            }

        }
        return phoneResult;
    }


    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_layout, null);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(mName,
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        WindowAdapter windowAdapter = new WindowAdapter(mList);
        mRecyclerView.setAdapter(windowAdapter);
        TextView mCancel = (TextView) view.findViewById(R.id.tv_cancel);

        windowAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<String> mList = adapter.getData();
                mRelation.setText(mList.get(position));
                window.dismiss();
            }
        });


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });

    }
}
