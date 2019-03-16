package com.yskj.daishuguan.activity;

import android.view.View;
import android.widget.TextView;

import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.dialog.SmsDialog;
import com.yskj.daishuguan.entity.evbus.DeferFinshEvenbus;
import com.yskj.daishuguan.entity.evbus.FinshMoneyEvenbus;
import com.yskj.daishuguan.entity.request.DetailRequest;
import com.yskj.daishuguan.modle.DetailView;
import com.yskj.daishuguan.presenter.DetailPresenter;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: DeferMoneyActivity
 * @Description: 申请展期
 */

public class Defer2MoneyActivity extends BaseActivity<DetailPresenter> implements DetailView {

    @BindView(R.id.tv_money)
    TextView mMoney;
    @BindView(R.id.tv_content)
    TextView mContent;
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
    @BindView(R.id.tv_sure)
    TextView mSure;
    private String loanOrderNo;
    private String repayOrderNo;
    private String loanDate;
    private String interestRate;
    private String duedDay;
    private String currentStage;
    private String serialNo;
    private SmsDialog dialog;
    private String endTime;
    private String startTime;
    private String extendTimes;
    private String extendOrderNo;
    private String extendDays;
    private String extendFees;

    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acttivity_money_defer2;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_defer_title;
    }

    @Override
    protected void initView() {

//        ll_penalty_interest.setVisibility(View.GONE);
        dialog = new SmsDialog();
        loanOrderNo = getIntent().getStringExtra("loanOrderNo");
        repayOrderNo = getIntent().getStringExtra("repayOrderNo");
        loanDate = getIntent().getStringExtra("loanDate");
        interestRate = getIntent().getStringExtra("interestRate");
        duedDay = getIntent().getStringExtra("duedDay");
        mInterset.setText("应还利息：" + (StringUtil.isEmpty(interestRate) ? "0元" : interestRate + "元"));
        mCInterest.setText("应还利息：" + interestRate + "元");
        mTime.setText("周期：" + loanDate + "天");
        mInterset.setText("应还罚息：" + loanDate + "元");
        mContent.setText(
                "温馨提示：\n" +
                        "1.申请展期后我平台将从您绑定的银行卡扣缴展期费，请确认你的银\n" +
                        "行卡余额充足，以免申请展期失败。\n" +
                        "2.我平台仅支持在应还款目前申请展期，请合理安排时间。\n");
        getData();
    }

    @Override
    protected void initData() {

        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRollover();
            }
        });

        dialog.setOnTypeClickLitener(new SmsDialog.OnNoFinshClickLitener() {
            @Override
            public void onNoFinshClick(String code, int id) {
                if (id == R.id.cv_register_countdown) {
                    getCode();
                } else if (id == R.id.tv_rig) {
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
                    UIUtils.showToast("验证码已发送");
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
        rxDialogLoading.show();
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

                        EventBus.getDefault().post(new DeferFinshEvenbus());
                        EventBus.getDefault().post(new FinshMoneyEvenbus(1));
//        finish();
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
                rxDialogLoading.dismiss();

            }
        });
    }

    private void confirmRollover() {
        rxDialogLoading.show();
        DetailRequest detailRequest = new DetailRequest();
        detailRequest.token = RxSPTool.getString(this, Constant.TOKEN);
        detailRequest.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
        detailRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
        detailRequest.repaymentOrderNo = repayOrderNo;
        detailRequest.stageNum = currentStage;
        detailRequest.extendFees = extendFees;
        detailRequest.extendDays = extendDays;
        detailRequest.extendOrderNo = extendOrderNo;
        detailRequest.extendTimes = extendTimes;
        mPresenter.initiativeExtend(detailRequest);
    }
//
//        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.DelayToPay);
//        Map<String, Object> bMap = new HashMap<>();
//        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
//        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
//        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
//        bMap.put("repaymentOrderNo", repayOrderNo);
//        bMap.put("stageNum", currentStage);
//        bMap.put("extendFees", extendFees);
//        bMap.put("extendDays", extendDays);
//        bMap.put("extendOrderNo", extendOrderNo);
//        bMap.put("extendTimes", endTime);
//
//        BaseParams.getParams(bMap);
//
//
//        for (String key : bMap.keySet()) {
//            params.addBodyParameter(key, bMap.get(key) + "");
//        }
//        x.http().post(params, new Callback.ProgressCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                RxLogTool.d("flag", "确认展期2:" + result);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    int recode = jsonObject.getInt("retcode");
//                    String retmsg = jsonObject.getString("retmsg");
//                    if (recode == 1000) {
////                        Intent intent = new Intent(BMExtendActivity.this, BMExtend02Activity.class);
////                        intent.putExtra("extendOrderNo", extendOrderNo);
////                        intent.putExtra("extendTimes", extendTimes);
////                        intent.putExtra("extendDays", extendDays);
////                        intent.putExtra("extendFees", extendFees);
////                        intent.putExtra("serialNo", jsonObject.getString("data"));
////                        startActivity(intent);
////                        finish();
////                        pay(jsonObject.getString("data"));
//                    } else {
//                        UIUtils.showToast(retmsg);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onWaiting() {
//
//            }
//
//            @Override
//            public void onStarted() {
//
//            }
//
//            @Override
//            public void onLoading(long total, long current, boolean isDownloading) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                RxLogTool.d("flag", "确认展期2onError:" + ex.getMessage());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }

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

                        startTime = json.getString("startTime");
                        endTime = json.getString("endTime");
                        extendFees = json.getString("extendFees");
                        extendDays = json.getString("extendDays");
                        extendTimes = json.getString("extendTimes");
                        extendOrderNo = json.getString("extendOrderNo");
//                        allMoney = ;
                        mMoney.setText("" + json.getString("total"));
                        mBorrowing.setText("借款金额" + json.getString("total"));
                        mStartTime.setText("借款时间" + json.getString("startTime") + "至" + json.getString("endTime"));
                        mEndTime.setText("还款时间：" + endTime);
                        mLateMoney.setText("展期会员费：" + extendFees + "元");
                        currentStage = json.getString("currentStage");
//                        new BigDecimal(json.getString("total")).multiply(new BigDecimal(0.0005).multiply(new BigDecimal(duedDay)));
//                        tv_backMoney.setText(json.getString("principal"));
//                        tv_weiYueMoney.setText(json.getString("overdueInterest"));
//                        tv_manageMoney.setText(json.getString("overdueItfee"));

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


    @Override
    public void onSuccess(String response) {
        serialNo = response;
        if (!StringUtil.isEmpty(response)) {
            UIUtils.showToast("验证码已发送");
            dialog.show(getSupportFragmentManager(), "set");

        } else {
            UIUtils.showToast("审核已提交，请等待");
        }

        rxDialogLoading.dismiss();

    }

    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
    }

    @Override
    public void onSMsFailure(BaseResponse response) {
//        if (response.getRetcode() == 20001){
//            serialNo =response.getData()+" " ;
//            dialog.show(getSupportFragmentManager(),"set");
//        }
//        UIUtils.showToast(response.getRetmsg());
        rxDialogLoading.dismiss();
    }

    @Override
    public void onError() {
        rxDialogLoading.dismiss();
    }
}
