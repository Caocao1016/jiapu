package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
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
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.evbus.LoginEvbusBean;
import com.yskj.daishuguan.entity.evbus.liveResultEvbusBean;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.modle.OCRView;
import com.yskj.daishuguan.presenter.OCRPresenter;
import com.yskj.daishuguan.response.OCRResponse;
import com.yskj.daishuguan.util.FileUtil;
import com.yskj.daishuguan.util.ImageUtil;
import com.yskj.daishuguan.util.SettingUtil;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
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
import retrofit2.http.Body;

/**
 * CaoPengFei
 * 2018/12/28
 *
 * @ClassName: IDConfirmActivity
 * @Description: 身份证确认页面
 */

public class IDConfirmActivity extends BaseActivity<OCRPresenter> implements OCRView {


    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    // 活体配置 默认值
    public String publicFilePath;


    @BindView(R.id.tv_confirmer_name)
    TextView mName;
    @BindView(R.id.tv_confirmer_gender)
    TextView mGender;
    @BindView(R.id.tv_confirmer_time)
    TextView mTime;
    @BindView(R.id.tv_confirmer_address)
    TextView mAddress;
    @BindView(R.id.tv_confirmer_national)
    TextView mNational;
    @BindView(R.id.tv_confirmer_Idcard)
    TextView mIDCard;
    @BindView(R.id.tv_confirmer_organ)
    TextView mOrgan;
    @BindView(R.id.tv_confirmer_data)
    TextView mData;


    protected OCRPresenter createPresenter() {
        return new OCRPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_confrim;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_cnfirm_title;
    }

    @Override
    protected void initView() {
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
            publicFilePath = StringUtil.getDiskCacheDir(IDConfirmActivity.this);
        }
    }


//    @Override
//    public void onLeftClick(View v) {
//        startActivity(CerIDActivity.class);
//        finish();
//    }

    @Override
    protected void initData() {
        String stringExtra = getIntent().getStringExtra(Constant.ACTIVITY_ID_CARE);
        OCRResponse bean = new Gson().fromJson(stringExtra, OCRResponse.class);
        OCRResponse.OCRData data = bean.getData();

        mName.setText(StringUtil.getValue(data.getName()));
        RxSPTool.putString(this,Constant.USER_NAME,data.getName());
        mAddress.setText(StringUtil.getValue(data.getAddress()));
        mTime.setText(StringUtil.getValue(data.getBirthdate()));
        mGender.setText(StringUtil.getValue(data.getSex()));
        mNational.setText(StringUtil.getValue(data.getNation()));
        mIDCard.setText(StringUtil.getValue(data.getIdno()));
        mOrgan.setText(StringUtil.getValue(data.getIssuingAuthority()));
        mData.setText(StringUtil.getValue(data.getValidity()));
    }

    @OnClick({R.id.tv_confirmer_modification, R.id.btn_confirmer_toNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirmer_modification:
                startActivity(new Intent(IDConfirmActivity.this, CerIDActivity.class));
                finish();
                break;
            case R.id.btn_confirmer_toNext:
                rxDialogLoading.show();
                OCRRequest request = new OCRRequest();
                request.userid = RxSPTool.getString(this, Constant.USER_ID);
                request.token = RxSPTool.getString(this, Constant.TOKEN);
                mPresenter.postIDCard(request);
                break;
        }
    }

    @Override
    public void onIDCardSuccess(BaseRequest response) {
        rxDialogLoading.dismiss();
        startCloudFace();
    }

    @Override
    public void onFanceSuccess(BaseRequest response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
    }

    @Override
    public void onError() {

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
//                finish();
                break;
        }
    }

    public void upLoadFace(Intent intent) {

        rxDialogLoading.show();
        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.FACE);
        params.setMultipart(true);
        params.setConnectTimeout(60 * 1000);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
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

        params.addBodyParameter("file", file);


        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                rxDialogLoading.show();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int retcode = jsonObject.getInt("retcode");
                    String retmsg = jsonObject.getString("retmsg");
                    if (1000 == retcode) {
                        RxSPTool.putString(IDConfirmActivity.this,Constant.IDCARD_NUMBER,mIDCard.getText().toString());
                        finish();
                    } else {
                        UIUtils.showToast(retmsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                rxDialogLoading.dismiss();
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
}
