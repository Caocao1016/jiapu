package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lzy.okgo.request.BaseRequest;
import com.sensetime.liveness.motion.MotionLivenessActivity;
import com.vondear.rxtool.RxDeviceTool;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.evbus.LoginEvbusBean;
import com.yskj.daishuguan.entity.evbus.liveResultEvbusBean;
import com.yskj.daishuguan.entity.request.FaceRequest;
import com.yskj.daishuguan.modle.OCRView;
import com.yskj.daishuguan.presenter.OCRPresenter;
import com.yskj.daishuguan.util.Base64BitmapUtil;
import com.yskj.daishuguan.util.FileUtil;
import com.yskj.daishuguan.util.ImageUtil;
import com.yskj.daishuguan.util.SettingUtil;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2018/12/26
 *
 * @ClassName: FaceActivity
 * @Description: 人脸识别
 */

public class FaceActivity extends BaseActivity<OCRPresenter> implements OCRView {

    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    // 活体配置 默认值
    public String publicFilePath;
    @BindView(R.id.img_toFace)
    ImageView mFace;

    @Override
    protected OCRPresenter createPresenter() {
        return new OCRPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_face_title;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        publicFilePath = new StringBuilder(Environment
                .getExternalStorageDirectory().getAbsolutePath())
                .append(File.separator).append("cloudwalk")
                .append(File.separator).append(sdf.format(new Date()))
                .toString();
        File file = new File(publicFilePath);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            StringUtil.mkDir(publicFilePath);
        }
        if (!file.exists() && !file.isDirectory()) {
            publicFilePath = StringUtil.getDiskCacheDir(FaceActivity.this);
        }
    }


    @OnClick({R.id.btn_face_toliving, R.id.img_toFace})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_face_toliving:
                startCloudFace();
                break;
            case R.id.img_toFace:
                startCloudFace();
                break;

        }

    }

    private void startCloudFace() {
        //调用商汤
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean soundNotice = sp.getBoolean(Constant.NOTICE, true);
        Intent intent = new Intent(this, MotionLivenessActivity.class);
        intent.putExtra(MotionLivenessActivity.EXTRA_DIFFICULTY,
                SettingUtil.INSTANCE.getDifficulty(this));
        intent.putExtra(MotionLivenessActivity.EXTRA_VOICE, soundNotice);
        intent.putExtra(MotionLivenessActivity.EXTRA_SEQUENCES,
                SettingUtil.INSTANCE.getSequencesInt(this));
        startActivityForResult(intent, 0);// No need to set requestCode.

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode) {
            case RESULT_OK:
                try {
                    upLoadFace(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                Intent mResultintent = new Intent(this, LiveResultActivity.class);
                mResultintent.putExtra("extra_result_code", resultCode);
                startActivity(mResultintent);
                break;
        }
    }

    @Override
    public void onIDCardSuccess(BaseRequest response) {

    }

    @Override
    public void onFanceSuccess(BaseRequest response) {
        EventBus.getDefault().post(new liveResultEvbusBean());
        finish();
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onError() {

    }

    //    public void upLoadFace(String filePath) {
    public void upLoadFace(Intent intent) throws Exception {
//        FaceRequest faceRequest = new FaceRequest();
//        File lastDetectImagesFile = new File(MotionLivenessActivity.RESULT_PATH);
//
//        if (!lastDetectImagesFile.exists() || lastDetectImagesFile.list() == null) {
//            return;
//        }
//        String[] files = lastDetectImagesFile.list();
//        Bitmap bitmap = BitmapFactory.decodeFile(MotionLivenessActivity.RESULT_PATH + files[1]);
        String userid = RxSPTool.getString(this, Constant.USER_ID);
        String deviceCode = RxDeviceTool.getBuildBrandModel().trim();
//        faceRequest.userId = userid;
//        faceRequest.deviceCode = deviceCode;
//        faceRequest.livenessId = intent.getStringExtra("livenessId");
//         mPresenter.postFaceCard(faceRequest,Base64BitmapUtil.bitmapToBase64(bitmap));


        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + "auth/livingbody/faceRecognise");
        params.setMultipart(true);
        params.setConnectTimeout(60 * 1000);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("userId", userid);
        bMap.put("deviceCode", deviceCode);
        bMap.put("livenessId", intent.getStringExtra("livenessId"));
        BaseParams.getParams(bMap);

        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key).toString());
        }

        File lastDetectImagesFile = new File(MotionLivenessActivity.RESULT_PATH);

        if (!lastDetectImagesFile.exists() || lastDetectImagesFile.list() == null) {
            return;
        }
        String[] files = lastDetectImagesFile.list();
        Bitmap bitmap1 = BitmapFactory.decodeFile(MotionLivenessActivity.RESULT_PATH + files[1]);
        String fileName1 = String.valueOf(System.currentTimeMillis()) + ".jpg";
        Bitmap bitmap2 = ImageUtil.compressScale(bitmap1);
        File file = FileUtil.saveMyBitmap(bitmap2, this, publicFilePath, fileName1, false);

        params.addBodyParameter("fileFace", file);


        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                RxLogTool.e("ApiRetrofit--face-"+result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int retcode = jsonObject.getInt("retcode");
                    String retmsg = jsonObject.getString("retmsg");
                    if (1000 == retcode) {
                        EventBus.getDefault().post(new liveResultEvbusBean());
                        finish();
                    } else {
                        UIUtils.showToast(retmsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("----JSONException-----", "人脸识别结果" + e);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                RxLogTool.e("ApiRetrofit--onError--", "人脸识别error");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void liveResultEvbusBean(liveResultEvbusBean event) {
        startCloudFace();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
