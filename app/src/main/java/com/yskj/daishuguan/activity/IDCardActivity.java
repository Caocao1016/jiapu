package com.yskj.daishuguan.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.hjq.toast.ToastUtils;
import com.lzy.okgo.request.BaseRequest;
import com.sensetime.sample.common.idcard.ActivityUtils;
import com.sensetime.sample.common.idcard.IdCardActivity;
import com.sensetime.senseid.sdk.ocr.id.IdCardSide;
import com.sensetime.senseid.sdk.ocr.id.KeyRequires;
import com.sensetime.senseid.sdk.ocr.id.ScanMode;
import com.sensetime.senseid.sdk.ocr.id.ScanSide;
import com.vondear.rxtool.RxDeviceTool;
import com.vondear.rxtool.RxImageTool;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yanzhenjie.permission.AndPermission;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseApp;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.modle.OCRView;
import com.yskj.daishuguan.presenter.OCRPresenter;
import com.yskj.daishuguan.response.OCRResponse;
import com.yskj.daishuguan.util.FilePostUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: IDCardActivity
 * @Description:
 */

public class IDCardActivity extends BaseActivity<OCRPresenter> implements OCRView {


    @BindView(R.id.img_ocrIDcard_zheng)
    ImageView mIDZheng;
    @BindView(R.id.img_ocrIDcard_fan)
    ImageView mIDFan;

    private String filePathFront;
    private String filePathBack;
    private Bitmap frontBitmap;
    private Bitmap backBitmap;


    @Override
    protected OCRPresenter createPresenter() {
        return new OCRPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_id_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_IDcard_discern, R.id.img_ocrIDcard_zheng, R.id.img_ocrIDcard_fan})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_IDcard_discern:
                //上传图片 获取结果
                if (null != frontBitmap && null != backBitmap) {
//                    OCRRequest request = new OCRRequest();
//                    request.userId = RxSPTool.getString(this, Constant.USER_ID);
//                    request.deviceCode = RxDeviceTool.getBuildBrandModel().trim();
//                    request.mobileNo = RxSPTool.getString(this, Constant.USER_MOBILENO);
//                    String[] file = new String[]{filePathFront};
//                    mPresenter.postIDCard(request, FilePostUtil.files2Parts("file", file, MediaType.parse("multipart/form-data")));
                    identityOcr();
                } else {
                    UIUtils.showToast("请拍摄身份证正反面照片");
                }
                break;
            case R.id.img_ocrIDcard_zheng:
                checkPermissionToDetect(Constant.SCAN_TYPE_SINGLE_FRONT);
                break;
            case R.id.img_ocrIDcard_fan:
                checkPermissionToDetect(Constant.SCAN_TYPE_SINGLE_BACK);
                break;
        }
    }

    private void identityOcr() {
        File fileFront = new File(filePathFront);
        File fileBack = new File(filePathBack);
        String userid = RxSPTool.getString(this, Constant.USER_ID);
        String mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
        String deviceCode = RxDeviceTool.getBuildBrandModel().trim();
        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + "auth/livingbody/idCardOCR");
        params.setMultipart(true);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("userId", userid);
        bMap.put("mobileNo", mobileno);
        bMap.put("deviceCode", deviceCode);
        BaseParams.getParams(bMap);

        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key).toString());
        }
        params.addBodyParameter("fileFront", fileFront);
        params.addBodyParameter("fileBack", fileBack);
        params.setConnectTimeout(120 * 1000);
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String retmsg = jsonObject.getString("retmsg");
                    RxLogTool.e("----" + result);
                    int retcode = jsonObject.getInt("retcode");
                    if (1000 == retcode) {
                        Intent intent = new Intent(IDCardActivity.this, IDConfirmActivity.class);
                        intent.putExtra(Constant.ACTIVITY_ID_CARE, result);
                        startActivity(intent);
                        finish();
                    } else {
                        UIUtils.showToast(retmsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    RxLogTool.e("----e" + e.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                RxLogTool.e("----onError" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

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

    private void checkPermissionToDetect(int scanType) {


        AndPermission.with(this)
                .permission(Permission.Group.STORAGE)
                .permission(Permission.CAMERA)
                // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
                .rationale((context, permissions, executor) -> {
                    // 此处可以选择显示提示弹窗
//                        executor.execute();
                    UIUtils.showToast("请去权限管理页面授权相关权限");
                })
                // 用户给权限了
                .onGranted(permissions ->

                {
                    GoToActivity(scanType);
                })
                // 用户拒绝权限，包括不再显示权限弹窗也在此列
                .onDenied(permissions -> {
                    UIUtils.showToast("用户拒绝权限");
                    // 判断用户是不是不再显示权限弹窗了，若不再显示的话进入权限设置页
                    if (AndPermission.hasAlwaysDeniedPermission(this, permissions)) {
                        // 打开权限设置页
                        AndPermission.permissionSetting(this).execute();
                        return;
                    }

                })
                .start();

//
//        if (Build.VERSION.SDK_INT >= 23) {
//            List<String> permissions = null;
//            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                permissions = new ArrayList<>();
//                permissions.add(Manifest.permission.CAMERA);
//            }
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                if (permissions == null) {
//                    permissions = new ArrayList<>();
//                }
//                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            }
//            if (permissions != null) {
//                String[] permissionArray = new String[permissions.size()];
//                permissions.toArray(permissionArray);
//                /// Request the permission. The result will be received
//                // in onRequestPermissionResult()
//                requestPermissions(permissionArray, scanType);
//            } else {
//                GoToActivity(scanType);
//            }
//        } else {
//            GoToActivity(scanType);
//        }
    }

    public void GoToActivity(final int req_camera) {

        Intent intent = new Intent(IDCardActivity.this, IdCardActivity.class);

        if (req_camera == Constant.SCAN_TYPE_SINGLE_FRONT) {
            intent.putExtra(IdCardActivity.EXTRA_SCAN_MODE, ScanMode.SINGLE);
            intent.putExtra(IdCardActivity.EXTRA_SCAN_SIDE, ScanSide.FRONT);
            intent.putExtra(IdCardActivity.EXTRA_KEY_REQUIRE, KeyRequires.ALL_OF_FRONT);
        } else {
            intent.putExtra(IdCardActivity.EXTRA_SCAN_MODE, ScanMode.SINGLE);
            intent.putExtra(IdCardActivity.EXTRA_SCAN_SIDE, ScanSide.BACK);
            intent.putExtra(IdCardActivity.EXTRA_KEY_REQUIRE, KeyRequires.ALL_OF_BACK);
        }

        startActivityForResult(intent, Constant.REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUEST_CODE_RESULT) {
            return;
        }
        switch (resultCode) {

            case RESULT_CANCELED:
                UIUtils.showToast("取消扫描");
                break;
            case ActivityUtils.RESULT_CODE_NO_PERMISSIONS:
            case ActivityUtils.RESULT_CODE_CAMERA_ERROR:
                UIUtils.showToast("相机或读写SD卡授权失败，请在设置中打开相机和读写SD卡权限 ");
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_FILE_NOT_FOUND:
                UIUtils.showToast("授权文件不存在 ");
                break;
            case ActivityUtils.RESULT_CODE_WRONG_STATE:
                UIUtils.showToast("错误的方法状态调用 ");
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_EXPIRE:
                UIUtils.showToast("授权文件过期 ");
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_PACKAGE_NAME_MISMATCH:
                UIUtils.showToast("绑定包名错误 ");
                break;
            case ActivityUtils.RESULT_CODE_CHECK_LICENSE_FAIL:
                UIUtils.showToast("授权文件不合法 ");
                break;
            case ActivityUtils.RESULT_CODE_TIMEOUT:
                UIUtils.showToast("扫描超时，请重试一次 ");
                break;
            case ActivityUtils.RESULT_CODE_CHECK_MODEL_FAIL:
                UIUtils.showToast("模型文件错误");
                break;
            case ActivityUtils.RESULT_CODE_MODEL_FILE_NOT_FOUND:
                UIUtils.showToast("模型文件不存在");
                break;
            case ActivityUtils.RESULT_CODE_MODEL_EXPIRE:
                UIUtils.showToast("模型文件过期");
                break;
            case ActivityUtils.RESULT_CODE_ERROR_API_KEY_SECRET:
                UIUtils.showToast("API账户信息错误，请检查网络以及API_KEY和API_SECRET ");
                break;
            case ActivityUtils.RESULT_CODE_SERVER:
                UIUtils.showToast("扫服务器访问错误");
                break;
            case ActivityUtils.RESULT_CODE_NETWORK_TIMEOUT:
                UIUtils.showToast("网络请求超时,请稍后重试 ");
                break;
            case ActivityUtils.RESULT_CODE_INVALID_ARGUMENTS:
                UIUtils.showToast("参数设置不合法 ");
                break;
            case ActivityUtils.RESULT_CODE_CAPABILITY_NOT_SUPPORTED:
                UIUtils.showToast("授权文件能力不支持 ");
                break;
            case Activity.RESULT_OK:
                if (data.getIntExtra(IdCardActivity.EXTRA_CARD_SIDE, -1) == IdCardSide.FRONT) {
                    filePathFront = data.getStringExtra(IdCardActivity.EXTRA_FRONT_RESULT_IMAGE);
                    setImage(data.getIntExtra(IdCardActivity.EXTRA_CARD_SIDE, -1), filePathFront);
                } else if (data.getIntExtra(IdCardActivity.EXTRA_CARD_SIDE, -1) == IdCardSide.BACK) {
                    filePathBack = data.getStringExtra(IdCardActivity.EXTRA_BACK_RESULT_IMAGE);
                    setImage(data.getIntExtra(IdCardActivity.EXTRA_CARD_SIDE, -1), filePathBack);
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setImage(int resultCode, final String photoImgFilePath2) {
        try {
            switch (resultCode) {
                case IdCardSide.FRONT:
                    // 图片压缩
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            frontBitmap = RxImageTool.getBitmap(photoImgFilePath2);
                            if (frontBitmap == null)
                                return;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mIDZheng.setImageBitmap(frontBitmap);
                                }
                            });
                        }
                    }).start();
                    break;
                case IdCardSide.BACK:

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            backBitmap = RxImageTool.getBitmap(photoImgFilePath2);
                            if (backBitmap == null)
                                return;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mIDFan.setImageBitmap(backBitmap);
                                }
                            });
                        }
                    }).start();
                    break;
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onIDCardSuccess(BaseRequest response) {
        finish();
    }

    @Override
    public void onFanceSuccess(BaseRequest response) {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onError() {

    }
}
