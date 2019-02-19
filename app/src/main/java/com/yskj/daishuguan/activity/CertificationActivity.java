package com.yskj.daishuguan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sensetime.liveness.motion.MotionLivenessActivity;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.dialog.NoFinshDialog;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.modle.CertificationDataView;
import com.yskj.daishuguan.presenter.CertificationPresenter;
import com.yskj.daishuguan.response.BannerResponse;
import com.yskj.daishuguan.response.CertificationResponse;
import com.yskj.daishuguan.util.FileUtil;
import com.yskj.daishuguan.util.ImageUtil;
import com.yskj.daishuguan.util.SettingUtil;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2018/11/22
 *
 * @ClassName: AccumulationFundActivity
 * @Description: 认证信息
 */

public class CertificationActivity extends BaseActivity<CertificationPresenter> implements CertificationDataView {

    @BindView(R.id.tv_money)
    TextView mTvMoney;

    @BindView(R.id.tv_day_rate)
    TextView mTvDayRate;
    @BindView(R.id.iv_phone_right)
    ImageView mIvPhoneRight;
    @BindView(R.id.tv_phone_right)
    TextView mTvPhoneRight;
    @BindView(R.id.iv_right_id)
    ImageView mIvIDRight;
    @BindView(R.id.tv_right_id)
    TextView mTvIDRight;
    @BindView(R.id.iv_number_right)
    ImageView mIvNumberRight;
    @BindView(R.id.tv_right_number)
    TextView mTvNumberRight;
    @BindView(R.id.iv_right_card)
    ImageView mIvCardRight;
    @BindView(R.id.tv_right_card)
    TextView mTvCardRight;
    private NoFinshDialog finshDialog;

    private boolean REAL_AUTh = false;
    private boolean IDCARD_AUTH = false;
    private boolean FACE_AUTH = false;
    private boolean CONTACT_AUTH = false;
    private boolean MNO_AUTH = false;
    private boolean isMNO_AUTH = false;   //最后一步
    // 活体配置 默认值
    public String publicFilePath;
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    private String what = "original";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cerification;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_fund_title;
    }

    @Override
    protected void initView() {
        String maxMoney = getIntent().getStringExtra("maxMoney");
        mTvMoney.setText(maxMoney);
        mTvDayRate.setText("1千元用一天，每日仅需" + StringUtil.getActualNUmber(1000, RxSPTool.getString(this, Constant.DAY_RATE)) + "元");
        createFileDir();
        mPresenter.operatorChannel(new BannerRequest());

        finshDialog = new NoFinshDialog();


        finshDialog.setOnTypeClickLitener(new NoFinshDialog.OnNoFinshClickLitener() {
            @Override
            public void onNoFinshClick() {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

        BannerRequest bannerRequest = new BannerRequest();
        bannerRequest.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
        bannerRequest.token = RxSPTool.getString(this, Constant.TOKEN);
        bannerRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
        mPresenter.authiteminfo(bannerRequest);

    }
    @Override
    public void onLeftClick(View v) {
        finshDialog.show(getSupportFragmentManager(),"set");
    }
    @Override
    protected CertificationPresenter createPresenter() {
        return new CertificationPresenter(this);
    }


    @OnClick({R.id.rl_cer_phone, R.id.rl_cer_id, R.id.rl_cer_card, R.id.rl_cer_number, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_cer_id:

                if (IDCARD_AUTH && FACE_AUTH) {
                    return;
                }
                if (IDCARD_AUTH) {
                    startCloudFace();
                } else {
                    startActivity(CerIDActivity.class);
                }

                break;
            case R.id.rl_cer_card:
                if (REAL_AUTh) {
                    return;
                }
                if (IDCARD_AUTH && FACE_AUTH) {
                    startActivity(CerCardTwoActivity.class);
                } else {
                    UIUtils.showToast("请先去完成身份信息认证");
                }

                break;

            case R.id.rl_cer_phone:

//                if (CONTACT_AUTH) {
//                    return;
//                }
//                if (IDCARD_AUTH && FACE_AUTH) {
//                    if (REAL_AUTh) {
                        startActivity(CerPhoneActivity.class);
//                    } else {
//                        UIUtils.showToast("请先去完成银行卡绑定");
//                    }
//                } else {
//                    UIUtils.showToast("请先去完成身份信息认证");
//                }

                break;

            case R.id.rl_cer_number:
//                if (MNO_AUTH) {
//                    return;
//                }
//
//                if (IDCARD_AUTH && FACE_AUTH) {
//                    if (REAL_AUTh) {
//
//                        if (CONTACT_AUTH) {

                if (what.equals("original")){
                    startActivity(CerNumberActivity.class);
                }else {

                }

//                        } else {
//                            UIUtils.showToast("请先去完成联系信息认证");
//                        }
//
//                    } else {
//                        UIUtils.showToast("请先去完成银行卡绑定");
//                    }
//                } else {
//                    UIUtils.showToast("请先去完成身份信息认证");
//                }


                break;
            case R.id.tv_sure:

                if ( REAL_AUTh && IDCARD_AUTH && FACE_AUTH &&CONTACT_AUTH &&MNO_AUTH){

                }else {
                    UIUtils.showToast("请先去完成相关认证");
                }
                break;
            default:
                break;
        }
    }

    private void createFileDir() {
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
            publicFilePath = StringUtil.getDiskCacheDir(CertificationActivity.this);
        }
    }

    /**
     * 跳转商汤活体认证页面
     */
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

                rxDialogLoading.setLoadingText("人脸识别中...");
                try {
                    upLoadFace(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("-----Exception-", e + "");
                }
                break;
            default:

                Intent mResultintent = new Intent(this, LiveResultActivity.class);
                mResultintent.putExtra("extra_result_code", resultCode);
                startActivity(mResultintent);
                break;
        }
    }

    public void upLoadFace(Intent intent) {


        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.FACE);
        params.setMultipart(true);
        params.setConnectTimeout(60 * 1000);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
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
                Log.e("---------", "人脸识别结果" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int retcode = jsonObject.getInt("retcode");
                    String retmsg = jsonObject.getString("retmsg");
                    if (1000 == retcode) {
                        initData();
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
                Log.e("flag", "人脸识别error");
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

    @Override
    public void onAuthiteminfoSuccess(CertificationResponse response) {
        List<CertificationResponse.CertificationListResponse> itemlist = response.getItemlist();
        for (CertificationResponse.CertificationListResponse  mList : itemlist) {
            String status = String.valueOf( mList.getStatus());
            String itemcode = mList.getItemcode();
            if (itemcode.equals("TB")) {
                if ("1".equals(status)) {
                    mIvPhoneRight.setVisibility(View.VISIBLE);
                    mTvPhoneRight.setVisibility(View.INVISIBLE);
                    mIvPhoneRight.setImageResource(R.mipmap.ic_fish);
                    CONTACT_AUTH = true;
                } else if ("2".equals(status)) {
                    CONTACT_AUTH = true;
                    mIvPhoneRight.setVisibility(View.VISIBLE);
                    mTvPhoneRight.setVisibility(View.INVISIBLE);
                    mIvPhoneRight.setImageResource(R.mipmap.ic_audit);
                } else if ("3".equals(status)) {
                    mIvPhoneRight.setVisibility(View.VISIBLE);
                    mTvPhoneRight.setVisibility(View.INVISIBLE);
                    mIvPhoneRight.setImageResource(R.mipmap.ic_no_approve);
                } else {
                    mIvPhoneRight.setVisibility(View.INVISIBLE);
                    mTvPhoneRight.setVisibility(View.VISIBLE);
                }
            } else if (itemcode.equals("IDCARD")) {

                if ("1".equals(status) || "2".equals(status)) {
                    IDCARD_AUTH = true;
                }

//                if ("1".equals(status)) {
//                    IDCARD_AUTH = true;
//                    mIvIDRight.setVisibility(View.VISIBLE);
//                    mTvIDRight.setVisibility(View.INVISIBLE);
//                    mIvIDRight.setImageResource(R.mipmap.ic_fish);
//                } else if ("2".equals(status)) {
//                    IDCARD_AUTH = true;
//                    mIvIDRight.setVisibility(View.VISIBLE);
//                    mTvIDRight.setVisibility(View.INVISIBLE);
//                    mIvIDRight.setImageResource(R.mipmap.ic_audit);
//                } else if ("3".equals(status)) {
//                    mIvIDRight.setVisibility(View.VISIBLE);
//                    mTvIDRight.setVisibility(View.INVISIBLE);
//                    mIvIDRight.setImageResource(R.mipmap.ic_no_approve);
//                } else {
//                    mIvIDRight.setVisibility(View.INVISIBLE);
//                    mTvIDRight.setVisibility(View.VISIBLE);
//                }
            } else if (itemcode.equals("FACE")) {
                if ("1".equals(status)) {
                    FACE_AUTH = true;
                    mIvIDRight.setVisibility(View.VISIBLE);
                    mTvIDRight.setVisibility(View.INVISIBLE);
                    mIvIDRight.setImageResource(R.mipmap.ic_fish);
                } else if ("2".equals(status)) {
                    FACE_AUTH = true;
                    mIvIDRight.setVisibility(View.VISIBLE);
                    mTvIDRight.setVisibility(View.INVISIBLE);
                    mIvIDRight.setImageResource(R.mipmap.ic_audit);
                } else if ("3".equals(status)) {
                    mIvIDRight.setVisibility(View.VISIBLE);
                    mTvIDRight.setVisibility(View.INVISIBLE);
                    mIvIDRight.setImageResource(R.mipmap.ic_no_approve);
                } else {
                    mIvIDRight.setVisibility(View.INVISIBLE);
                    mTvIDRight.setVisibility(View.VISIBLE);
                }
            } else if (itemcode.equals("MNO")) {
                if ("1".equals(status)) {
                    MNO_AUTH = true;
                    mIvNumberRight.setVisibility(View.VISIBLE);
                    mTvNumberRight.setVisibility(View.INVISIBLE);
                    mIvNumberRight.setImageResource(R.mipmap.ic_fish);
                } else if ("2".equals(status)) {
                    MNO_AUTH = true;
                    mIvNumberRight.setVisibility(View.VISIBLE);
                    mTvNumberRight.setVisibility(View.INVISIBLE);
                    mIvNumberRight.setImageResource(R.mipmap.ic_audit);
                } else if ("3".equals(status)) {
                    mIvNumberRight.setVisibility(View.VISIBLE);
                    mTvNumberRight.setVisibility(View.INVISIBLE);
                    mIvNumberRight.setImageResource(R.mipmap.ic_no_approve);
                } else {
                    mIvNumberRight.setVisibility(View.INVISIBLE);
                    mTvNumberRight.setVisibility(View.VISIBLE);
                }
            } else if (itemcode.equals("RNA")) {
                if ("1".equals(status)) {
                    REAL_AUTh = true;
                    mIvCardRight.setVisibility(View.VISIBLE);
                    mTvCardRight.setVisibility(View.INVISIBLE);
                    mIvCardRight.setImageResource(R.mipmap.ic_fish);
                } else if ("2".equals(status)) {
                    REAL_AUTh = true;
                    mIvCardRight.setVisibility(View.VISIBLE);
                    mTvCardRight.setVisibility(View.INVISIBLE);
                    mIvCardRight.setImageResource(R.mipmap.ic_audit);
                } else if ("3".equals(status)) {
                    mIvCardRight.setVisibility(View.VISIBLE);
                    mTvCardRight.setVisibility(View.INVISIBLE);
                    mIvCardRight.setImageResource(R.mipmap.ic_no_approve);
                } else {
                    mIvCardRight.setVisibility(View.INVISIBLE);
                    mTvCardRight.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    @Override
    public void onSuccess(String response) {
        what = response;
    }

    @Override
    public void onError() {

    }


    @Override
    public void onFailure(BaseResponse response) {

        UIUtils.showToast(response.getRetmsg());
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
