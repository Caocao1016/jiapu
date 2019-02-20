package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.response.CreateReportInfo;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/11
 *
 * @ClassName: CerPhoneActivity
 * @Description: 手机运营商认证项
 */

public class CerNumberActivity extends BaseActivity {

    @BindView(R.id.tv_phone)
    TextView mPhone;
    @BindView(R.id.tv_password)
    TextView mPassWord;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cer_number;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_number_title;
    }

    @Override
    protected void initView() {

        mPhone.setText(StringUtil.getString(RxSPTool.getString(this, Constant.USER_MOBILENO)));

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_sure)
    public void onClick(View view) {
        String phone = mPhone.getText().toString();
        String password = mPassWord.getText().toString();

        if (null != password && password.length() > 0) {
            rxDialogLoading.show();
            //539158
            getCreateReportInfo(phone, password);
        } else {
            UIUtils.showToast("请输入运营商服务密码");
        }
    }


    private void getCreateReportInfo(String phone, String servicepwd) {
        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.CREATEREPORT);
        params.setConnectTimeout(200 * 1000);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("passwd", servicepwd);
        BaseParams.getParams(bMap);
        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key).toString());
        }

        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                RxLogTool.e(result);
                CreateReportInfo info = new Gson().fromJson(result, CreateReportInfo.class);
                String retmsg = info.getRetmsg();
                if (1000 == info.getRetcode()) {
                    if (info.getData().isComplete()) {
                        Intent intent = new Intent(CerNumberActivity.this, CerFinshActivity.class);
                        intent.putExtra("what", 0);
                        startActivity(intent);
                        finish();
                    } else {
                        //跳转到第二步页面信息 短信--图片
                        if (info.getData().isSmscode()) {
                            CreateReportInfo.DataBean data = info.getData();
                            Intent intent = new Intent(CerNumberActivity.this, CerNumberTwoActivity.class);
                            intent.putExtra("taskid", data.getTaskid());
                            intent.putExtra("code", data.getCode());
                            intent.putExtra("nextstage", data.getNextstage());
                            intent.putExtra("complete", "" + data.isComplete());
                            startActivity(intent);
                            finish();
                        } else {
                            UIUtils.showToast(retmsg);
                        }
                    }
                } else {
                    UIUtils.showToast(retmsg);
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
    protected void onDestroy() {
        super.onDestroy();

    }

}
