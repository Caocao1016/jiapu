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
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.dialog.SmsDialog;
import com.yskj.daishuguan.entity.evbus.DeferFinshEvenbus;
import com.yskj.daishuguan.entity.request.UserInfoRequest;
import com.yskj.daishuguan.modle.UserInfoView;
import com.yskj.daishuguan.presenter.UserInfoPresenter;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;
import com.yskj.daishuguan.response.UserInfoResponse;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: PaymentDetails
 * @Description: 逾期还款详情
 */

public class OverdueDetailsActivity extends BaseActivity<UserInfoPresenter> implements UserInfoView {


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
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acttivity_overdue_details;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_payment_title;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        dialog = new SmsDialog();

        boolean aFalse = getIntent().getBooleanExtra("false", false);
        loanDate = getIntent().getStringExtra("loanDate");
        interestRate = getIntent().getStringExtra("interestRate");
        loanOrderNo = getIntent().getStringExtra("loanOrderNo");
        repayOrderNo = getIntent().getStringExtra("repayOrderNo");
        paymentDay = getIntent().getStringExtra("paymentDay");
        duedDay = getIntent().getStringExtra("duedDay");

        mCInterest.setText("应还利息：" + (StringUtil.isEmpty(interestRate) ? "0元" : interestRate));
        mTime.setText("周期：" + StringUtil.getValue(loanDate));
        mTv.setText("距离还款日还剩" + paymentDay + "天");
        String cardNumber = RxSPTool.getString(this, Constant.CARD_NUMBER);
        if (!StringUtil.isEmpty(cardNumber)) {
            mCode.setText(cardNumber.substring(cardNumber.length() - 4) + "的银行卡");
        } else {
            UserInfoRequest request = new UserInfoRequest();
            request.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
            request.userid = RxSPTool.getString(this, Constant.USER_ID);
            request.token = RxSPTool.getString(this, Constant.TOKEN);
            mPresenter.getuserInfo(request);
        }


        getData();
    }

    @Override
    protected void initData() {


        mTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OverdueDetailsActivity.this, Defer2MoneyActivity.class);
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
                if (id == R.id.cv_register_countdown) {
                    getCode();
                } else if (id == R.id.tv_rig) {
                    pay(code);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 登录成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void DeferFinshEvenbus(DeferFinshEvenbus event) {
        finish();
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
                       String money = json.getString("total");
                       String paymentMoney = json.getString("paymentMoney");
                        mMoney.setText("" + money );
                        mBorrowing.setText("借款金额" + paymentMoney);
                        mStartTime.setText("借款时间" + json.getString("startTime") + "至" + json.getString("endTime"));
                        mEndTime.setText("还款时间：" + json.getString("endTime"));
                        currentStage = json.getString("currentStage");
                        String overdue_rate = json.getString("overdue_rate");  //滞纳金利率
                        String bad_interest_rate = json.getString(" bad_interest_rate");  //逾期利率
                        mInterset.setText("应还罚息："+new BigDecimal(paymentMoney).multiply(new BigDecimal(duedDay)).multiply(new BigDecimal(bad_interest_rate)) +"元");
                        mLateMoney.setText("应还滞纳金："+new BigDecimal(paymentMoney).multiply(new BigDecimal(overdue_rate))+"元");
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
    public void onSuccess(UserInfoResponse response) {
        mCode.setText(response.getBankcard().substring(response.getBankcard().length() - 4) + "的银行卡");
    }

    @Override
    public void onCommonDataSuccess(CommonDataResponse response) {

    }

    @Override
    public void onHomeInfoSuccess(HomeInfoResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onError() {

    }
}
