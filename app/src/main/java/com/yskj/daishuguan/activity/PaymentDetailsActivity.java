package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.dialog.SmsDialog;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: PaymentDetails
 * @Description: 还款详情
 */

public class PaymentDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_money)
    TextView mMoney;
    @BindView(R.id.tv_borrowing)
    TextView mBorrowing;
    @BindView(R.id.tv_interest)
    TextView mCInterest;
    @BindView(R.id.tv_penalty_interest)
    TextView mInterset;
    @BindView(R.id.tv_late_money)
    TextView mLateMoney;
    @BindView(R.id.tv_time)
    TextView mTime;
    @BindView(R.id.tv_start_time)
    TextView mStartTime;
    @BindView(R.id.tv_end_time)
    TextView mEndTime;
    @BindView(R.id.tv_code)
    TextView mCode;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.tv_top)
    TextView mTop;
    @BindView(R.id.tv_sure)
    TextView mSure;
    @BindView(R.id.ll_late_money)
    LinearLayout mLLLateMoney;
     @BindView(R.id.ll_penalty_interest)
    LinearLayout mLLInterset;

    private String loanOrderNo;
    private String repayOrderNo;
    private String loanDate;
    private String interestRate;
    private String paymentDay;
    private String currentStage;
    private String serialNo;
    private SmsDialog dialog;
    private String duedDay;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acttivity_payment_details;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_payment_title;
    }

    @Override
    protected void initView() {

        dialog = new SmsDialog();

        boolean aFalse = getIntent().getBooleanExtra("false", false);
        loanDate = getIntent().getStringExtra("loanDate");
        interestRate = getIntent().getStringExtra("interestRate");
        loanOrderNo = getIntent().getStringExtra("loanOrderNo");
        repayOrderNo = getIntent().getStringExtra("repayOrderNo");
        paymentDay = getIntent().getStringExtra("paymentDay");
        duedDay = getIntent().getStringExtra("duedDay");
        if (aFalse) {
            mLLInterset.setVisibility(View.GONE);
            mLLLateMoney.setVisibility(View.GONE);
        } else {
            mLLInterset.setVisibility(View.VISIBLE);
            mLLLateMoney.setVisibility(View.VISIBLE);
        }

        mInterset.setText("应还利息：" +StringUtil.getValue(interestRate) );
        mTime.setText("周期：" +StringUtil.getValue( loanDate));
        String cardNumber = RxSPTool.getString(this, Constant.CARD_NUMBER);
        if (!StringUtil.isEmpty(cardNumber)) {
            mCode.setText(cardNumber.substring(cardNumber.length() - 4) + "的银行卡");
        }
        mTv.setText("距离还款日还剩" + paymentDay + "天");
        getData();
    }

    @Override
    protected void initData() {
        mTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentDetailsActivity.this, Defer2MoneyActivity.class);
                intent.putExtra("interestRate", interestRate);
                intent.putExtra("loanDate", loanDate);
                intent.putExtra("paymentDay", paymentDay);
                intent.putExtra("loanOrderNo", loanOrderNo);
                intent.putExtra("repayOrderNo", repayOrderNo);
                intent.putExtra("duedDay", duedDay);
                startActivity(intent);
            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huanKuan();
            }
        });
        dialog.setOnTypeClickLitener(new SmsDialog.OnNoFinshClickLitener() {
            @Override
            public void onNoFinshClick(String code, int id) {
                if (id == R.id.cv_register_countdown){
                    getCode();
                }else if (id == R.id.tv_rig){
                    pay(code);
                }
            }
        });

    }


    private void getCode() {
        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.BMGetSMS);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("serialNo", serialNo);
        BaseParams.getParams(bMap);

        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key) + "");
        }
        x.http().get(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                RxLogTool.d("flag", "-验证码" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int retcode = jsonObject.getInt("retcode");
                    String retmsg = jsonObject.getString("retmsg");
                    UIUtils.showToast(retmsg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                RxLogTool.d("flag", "验证码error");
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

    private void pay(String code) {
        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.ConfirmPay);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("paramCode", code);
        bMap.put("serialNo", serialNo);
        BaseParams.getParams(bMap);

        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key) + "");
        }
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                RxLogTool.d("flag", "还款2:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String retmsg = jsonObject.getString("retmsg");
                    int retcode = jsonObject.getInt("retcode");
                    UIUtils.showToast(retmsg);
                    if (1000 == retcode) {
//                        Intent intent =new Intent(BMImmHuan02Activity.this, HomeActivity.class);
//                        intent.putExtra("finishAC","finishAC");
//                        startActivity(intent);
//                        closeKebord();
                        finish();
                    } else {
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                RxLogTool.d("flag", "还款2error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void huanKuan() {

        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.PayJudge);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("repaymentOrderNo", repayOrderNo);
        bMap.put("stageNum", currentStage);
        BaseParams.getParams(bMap);


        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key) + "");
        }
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                RxLogTool.d("flag", "还款:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int recode = jsonObject.getInt("retcode");
                    String retmsg = jsonObject.getString("retmsg");
                    if (recode == 1000) {
                        serialNo = jsonObject.getString("data");

//                        pay(jsonObject.getString("data"));
//                        Intent intent = new Intent(BMImmHuanActivity.this, BMImmHuan02Activity.class);
//                        intent.putExtra("serialNo", );
//                        startActivity(intent);

                        dialog.show(getSupportFragmentManager(), "set");
                    } else {
                        UIUtils.showToast(retmsg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                RxLogTool.d("flag", "还款1error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getData() {

        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.repayTotal);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("repaymentOrderNo", repayOrderNo);
        bMap.put("loanOrderNo", loanOrderNo);
        BaseParams.getParams(bMap);

        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key) + "");
        }
        x.http().get(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                RxLogTool.d("flag", "订单详情:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int retcode = jsonObject.getInt("retcode");
                    if (1000 == retcode) {
                        String data = jsonObject.getString("data");
                        JSONObject json = new JSONObject(data);
//                        allMoney = ;
                        mMoney.setText("" + json.getString("total"));
                        mBorrowing.setText("借款金额" + json.getString("total"));
                        mStartTime.setText("借款时间" + json.getString("startTime") + "至" + json.getString("endTime"));
                        mEndTime.setText("还款时间：" + json.getString("endTime"));
//                        tv_backMoney.setText(json.getString("principal"));
//                        tv_weiYueMoney.setText(json.getString("overdueInterest"));
//                        tv_manageMoney.setText(json.getString("overdueItfee"));
                        currentStage = json.getString("currentStage");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                RxLogTool.d("flag", "订单详情error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //申请延期
    private void appapplyRollover() {
        rxDialogLoading.show();

        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.Delay);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
//        bMap.put("repaymentOrderNo", repayOrderNo);
//        bMap.put("stageNum", currentStage);
        BaseParams.getParams(bMap);


        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key) + "");
        }
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                RxLogTool.e("flag", "申请展期:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int retcode = jsonObject.getInt("retcode");
                    String retmsg = jsonObject.getString("retmsg");
                    if (1000 == retcode) {
                        String data = jsonObject.getString("data");
                        JSONObject json = new JSONObject(data);
//                        Intent intent = new Intent(OrderHuankuanActivity.this, BMExtendActivity.class);
//                        intent.putExtra("extendOrderNo", json.getString("extendOrderNo"));
//                        intent.putExtra("extendFees", json.getString("extendFees"));
//                        intent.putExtra("extendDays", json.getInt("extendDays"));
//                        intent.putExtra("extendTimes", json.getString("extendTimes"));
//                        intent.putExtra("extendDaysString", json.getString("extendDaysString"));
//                        intent.putExtra("currentStage", currentStage);
//                        intent.putExtra("repayOrderNo", repayOrderNo);
//                        startActivity(intent);
                    } else {
//                        ToastUtils.showToastLong(OrderHuankuanActivity.this, retmsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                rxDialogLoading.dismiss();
            }
        });
    }

}
