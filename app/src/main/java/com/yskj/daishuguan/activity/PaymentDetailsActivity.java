package com.yskj.daishuguan.activity;

import android.content.Intent;

import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: PaymentDetails
 * @Description: 还款详情
 */

public class PaymentDetailsActivity extends BaseActivity {
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

    }

    @Override
    protected void initData() {

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
