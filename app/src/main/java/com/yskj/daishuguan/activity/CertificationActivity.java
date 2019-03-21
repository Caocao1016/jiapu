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
import android.widget.TextView;

import com.moxie.client.manager.MoxieCallBack;
import com.moxie.client.manager.MoxieCallBackData;
import com.moxie.client.manager.MoxieContext;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.model.MxLoginCustom;
import com.moxie.client.model.MxParam;
import com.sensetime.liveness.motion.MotionLivenessActivity;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.dialog.NoFinshDialog;
import com.yskj.daishuguan.entity.evbus.FinshCertificationEvenbus;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.MoxieRequest;
import com.yskj.daishuguan.modle.CertificationDataView;
import com.yskj.daishuguan.presenter.CertificationPresenter;
import com.yskj.daishuguan.response.CertificationResponse;
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
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
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
    private String what = "A";
    private boolean isreloan;

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
        isreloan = getIntent().getBooleanExtra("isreloan", false);
        String maxMoney = getIntent().getStringExtra("maxMoney");
        mTvMoney.setText(maxMoney);
        mTvDayRate.setText("1千元用一天，每日仅需" + StringUtil.getActualNUmber(1000, RxSPTool.getString(this, Constant.DAY_RATE)) + "元");
        createFileDir();
        BannerRequest bannerRequest = new BannerRequest();
        bannerRequest.userid = RxSPTool.getContent(this, Constant.USER_ID);
        mPresenter.operatorChannel(bannerRequest);

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

        rxDialogLoading.setLoadingText("");
        rxDialogLoading.show();
        BannerRequest bannerRequest = new BannerRequest();
        bannerRequest.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
        bannerRequest.token = RxSPTool.getString(this, Constant.TOKEN);
        bannerRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
        mPresenter.authiteminfo(bannerRequest);

    }

    @Override
    public void onLeftClick(View v) {
        if (!isreloan) {
            if (REAL_AUTh && IDCARD_AUTH && FACE_AUTH && CONTACT_AUTH && MNO_AUTH) {
                finish();
            } else {
                finshDialog.show(getSupportFragmentManager(), "set");
            }
        }

    }


    @Override
    public void onRightClick(View v) {
        rxDialogLoading.setLoadingText("");
        rxDialogLoading.show();
        BannerRequest bannerRequest = new BannerRequest();
        bannerRequest.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
        bannerRequest.token = RxSPTool.getString(this, Constant.TOKEN);
        bannerRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
        mPresenter.authiteminfo(bannerRequest);
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
                if (MNO_AUTH || isMNO_AUTH) {
                    return;
                }
                if (IDCARD_AUTH && FACE_AUTH) {
                    if (REAL_AUTh) {

                        if (CONTACT_AUTH) {

                            if (what.equals("B")) {
                                startActivity(CerNumberActivity.class);
                            } else {
                                startMoxie();
                            }

                        } else {
                            UIUtils.showToast("请先去完成联系信息认证");
                        }

                    } else {
                        UIUtils.showToast("请先去完成银行卡绑定");
                    }
                } else {
                    UIUtils.showToast("请先去完成身份信息认证");
                }


                break;
            case R.id.tv_sure:

                if (!isMNO_AUTH) {
                    if (REAL_AUTh && IDCARD_AUTH && FACE_AUTH && CONTACT_AUTH && MNO_AUTH) {
                        EventBus.getDefault().post(new FinshCertificationEvenbus());
                        finish();
                    } else {
                        UIUtils.showToast("请先去完成相关认证");
                    }
                } else {
                    UIUtils.showToast("正在审核中");
                }

                break;
            default:
                break;
        }
    }

    /**
     * 打开魔蝎
     */
    private void startMoxie() {
//合作方系统中的客户ID
        String mUserId = RxSPTool.getString(this, Constant.USER_ID);  //合作方系统中的客户ID
        String mPhone = RxSPTool.getString(this, Constant.USER_MOBILENO);
        String mName = RxSPTool.getString(this, Constant.USER_NAME);
        MxParam mxParam = new MxParam();
        mxParam.setUserId(mUserId);                      //必传
        mxParam.setThemeColor("#FFB700");                      //必传
        mxParam.setApiKey("45b4ad5ad58b44e8a50d28b3c2a8d834");                      //必传
        mxParam.setTaskType(MxParam.PARAM_TASK_CARRIER);    //必传 选择产品
        MxLoginCustom loginCustom = new MxLoginCustom();
        Map<String, Object> loginParam = new HashMap<>();
        loginParam.put("phone", mPhone);          // 手机号
        loginParam.put("name", mName);               // 姓名
        loginParam.put("idcard", RxSPTool.getString(this, Constant.IDCARD_NUMBER));  // 身份证
        loginCustom.setLoginParams(loginParam);
        mxParam.setLoginCustom(loginCustom);
        MoxieSDK.getInstance().start(this, mxParam, new MoxieCallBack() {
            /**
             * 物理返回键和左上角返回按钮的back事件以及sdk退出后任务的状态都通过这个函数来回调
             *
             * @param moxieContext      可以用这个来实现在魔蝎的页面弹框或者关闭魔蝎的界面
             * @param moxieCallBackData 我们可以根据 MoxieCallBackData 的code来判断目前处于哪个状态，以此来实现自定义的行为
             * @return 返回true表示这个事件由自己全权处理，返回false会接着执行魔蝎的默认行为(比如退出sdk)
             * <p>
             * # 注意，假如设置了MxParam.setQuitOnLoginDone(MxParam.PARAM_COMMON_YES);
             * 登录成功后，返回的code是MxParam.ResultCode.IMPORTING，不是MxParam.ResultCode.IMPORT_SUCCESS
             */
            @Override
            public boolean callback(MoxieContext moxieContext, MoxieCallBackData moxieCallBackData) {
                /**
                 *  # MoxieCallBackData的格式如下：
                 *  1.1.没有进行账单导入，未开始！(后台没有通知)
                 *      "code" : MxParam.ResultCode.IMPORT_UNSTART, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "", "loginDone": false, "businessUserId": ""
                 *  1.2.平台方服务问题(后台没有通知)
                 *      "code" : MxParam.ResultCode.THIRD_PARTY_SERVER_ERROR, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "xxx", "loginDone": false, "businessUserId": ""
                 *  1.3.魔蝎数据服务异常(后台没有通知)
                 *      "code" : MxParam.ResultCode.MOXIE_SERVER_ERROR, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "xxx", "loginDone": false, "businessUserId": ""
                 *  1.4.用户输入出错（密码、验证码等输错且未继续输入）
                 *      "code" : MxParam.ResultCode.USER_INPUT_ERROR, "taskType" : "mail", "taskId" : "", "message" : "密码错误", "account" : "xxx", "loginDone": false, "businessUserId": ""
                 *  2.账单导入失败(后台有通知)
                 *      "code" : MxParam.ResultCode.IMPORT_FAIL, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": false, "businessUserId": ""
                 *  3.账单导入成功(后台有通知)
                 *      "code" : MxParam.ResultCode.IMPORT_SUCCESS, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": true, "businessUserId": "xxxx"
                 *  4.账单导入中(后台有通知)
                 *      "code" : MxParam.ResultCode.IMPORTING, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": true, "businessUserId": "xxxx"
                 *
                 *  code           :  表示当前导入的状态
                 *  taskType       :  导入的业务类型，与MxParam.setTaskType()传入的一致
                 *  taskId         :  每个导入任务的唯一标识，在登录成功后才会创建
                 *  message        :  提示信息
                 *  account        :  用户输入的账号
                 *  loginDone      :  表示登录是否完成，假如是true，表示已经登录成功，接入方可以根据此标识判断是否可以提前退出
                 *  businessUserId :  第三方被爬取平台本身的userId，非商户传入，例如支付宝的UserId
                 *
                 */
                if (moxieCallBackData != null) {
                    Log.e("-------MoxieSDK---", "MoxieSDK Callback Data : " + moxieCallBackData.toString());
                    switch (moxieCallBackData.getCode()) {
                        /**
                         * 账单导入中
                         *
                         * 如果用户正在导入魔蝎SDK会出现这个情况，如需获取最终状态请轮询贵方后台接口
                         * 魔蝎后台会向贵方后台推送Task通知和Bill通知
                         * Task通知：登录成功/登录失败
                         * Bill通知：账单通知
                         */
                        case MxParam.ResultCode.IMPORTING:
                            if (moxieCallBackData.isLoginDone()) {
                                //状态为IMPORTING, 且loginDone为true，说明这个时候已经在采集中，已经登录成功
                                Log.e("------MoxieSDK----", "任务已经登录成功，正在采集中，SDK退出后不会再回调任务状态，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");
                            } else {
                                //状态为IMPORTING, 且loginDone为false，说明这个时候正在登录中
                                Log.e("------MoxieSDK----", "任务正在登录中，SDK退出后不会再回调任务状态，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");
                            }
                            break;
                        /**
                         * 任务还未开始
                         *
                         * 假如有弹框需求，可以参考 {@link BigdataFragment#showDialog(MoxieContext)}
                         *
                         * example:
                         *  case MxParam.ResultCode.IMPORT_UNSTART:
                         *      showDialog(moxieContext);
                         *      return true;
                         * */
                        case MxParam.ResultCode.IMPORT_UNSTART:
                            Log.e("-----MoxieSDK-----", "任务未开始");
                            break;
                        case MxParam.ResultCode.THIRD_PARTY_SERVER_ERROR:
                            UIUtils.showToast("导入失败(1)");
                            Log.e("-----MoxieSDK-----", "导入失败(1)");
                            break;
                        case MxParam.ResultCode.MOXIE_SERVER_ERROR:
                            UIUtils.showToast("导入失败(魔蝎数据服务异常)");
                            Log.e("-----MoxieSDK-----", "魔蝎数据服务异常");
                            break;
                        case MxParam.ResultCode.USER_INPUT_ERROR:
                            Log.e("-----MoxieSDK-----", "导入失败");
                            UIUtils.showToast("导入失败(" + moxieCallBackData.getMessage() + ")");
                            break;
                        case MxParam.ResultCode.IMPORT_FAIL:
                            UIUtils.showToast("导入失败)");
                            Log.e("-----MoxieSDK-----", "导入失败)");
                            break;
                        case MxParam.ResultCode.IMPORT_SUCCESS:
                            Log.e("-----MoxieSDK-----", "任务采集成功，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");
                            //根据taskType进行对应的处理
                            switch (moxieCallBackData.getTaskType()) {
                                case MxParam.PARAM_TASK_CARRIER:
                                    Log.e("-----MoxieSDK-----", "导入失败)");
                                    MoxieRequest request = new MoxieRequest();
                                    request.taskId = moxieCallBackData.getTaskId();
                                    request.token = RxSPTool.getString(CertificationActivity.this, Constant.TOKEN);
                                    request.userid = RxSPTool.getString(CertificationActivity.this, Constant.USER_ID);
                                    mPresenter.taskSave(request);
                                    break;
                            }
                            moxieContext.finish();

                            return true;

                    }
                }
                return false;
            }
        });

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

        rxDialogLoading.setLoadingText("人脸识别中...");
        rxDialogLoading.show();
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
        rxDialogLoading.dismiss();
        List<CertificationResponse.CertificationListResponse> itemlist = response.getItemlist();
        for (CertificationResponse.CertificationListResponse mList : itemlist) {
            String status = String.valueOf(mList.getStatus());
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
//                    mTv   IDRight.setVisibility(View.VISIBLE);
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
                    isMNO_AUTH = false;
                    mIvNumberRight.setVisibility(View.VISIBLE);
                    mTvNumberRight.setVisibility(View.INVISIBLE);
                    mIvNumberRight.setImageResource(R.mipmap.ic_fish);
                } else if ("2".equals(status)) {
                    isMNO_AUTH = true;
                    mIvNumberRight.setVisibility(View.VISIBLE);
                    mTvNumberRight.setVisibility(View.INVISIBLE);
                    mIvNumberRight.setImageResource(R.mipmap.ic_audit);
                } else if ("3".equals(status)) {
                    isMNO_AUTH = false;
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
    public void onMOxieSuccess(BaseResponse response) {
        initData();
    }

    @Override
    public void onSuccess(String response) {
        what = response;
    }

    @Override
    public void onError() {
        rxDialogLoading.dismiss();
    }


    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
        UIUtils.showToast(response.getRetmsg());
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
