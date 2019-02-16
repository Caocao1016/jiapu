package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.hjq.baselibrary.widget.ClearEditText;
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
import okhttp3.HttpUrl;

/**
 * CaoPengFei
 * 2019/2/11
 *
 * @ClassName: CerNumberTwoActivity
 * @Description:
 */

public class CerNumberTwoActivity extends BaseActivity {

    @BindView(R.id.cl_code)
    ClearEditText mCode;

    private String taskid, code, nextstage, authcode, complete;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cer_number_sure;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_number_title;
    }

    @Override
    protected void initView() {
        nextstage = getIntent().getStringExtra("nextstage");
        authcode = getIntent().getStringExtra("authcode");
        complete = getIntent().getStringExtra("complete");
        code = getIntent().getStringExtra("code");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_sure)
    public void onClick(View view) {
        String s = mCode.getText().toString();

        if (StringUtil.isEmpty(s)) {
            UIUtils.showToast("请输入雁阵吗");
            return;
        }
        getCrawLingreport(s);
    }


    private void getCrawLingreport(String etcode) {
        rxDialogLoading.show();
        RequestParams params = new RequestParams(ApiConstant.BASE_SERVER_URL + ApiConstant.CRAWLINGREPORT);
        params.setConnectTimeout(200 * 1000);
        Map<String, Object> bMap = new HashMap<>();
        bMap.put("token", RxSPTool.getString(this, Constant.TOKEN));
        bMap.put("userid", RxSPTool.getString(this, Constant.USER_ID));
        bMap.put("mobileno", RxSPTool.getString(this, Constant.USER_MOBILENO));
        bMap.put("taskid", taskid);
        bMap.put("authcode", authcode);
        bMap.put("smscode", etcode);
        bMap.put("nextstage", nextstage);
        bMap.put("code", code);
        bMap.put("complete", complete);
        bMap.put("base64img", "");

        BaseParams.getParams(bMap);


        for (String key : bMap.keySet()) {
            params.addBodyParameter(key, bMap.get(key).toString());
        }
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                RxLogTool.e("运营商2重复认证" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String retmsg = jsonObject.getString("retmsg");
                    int retcode = jsonObject.getInt("retcode");
                    if (1000 == retcode) {
                        CreateReportInfo info = new Gson().fromJson(result, CreateReportInfo.class);
                        if (info.getData() != null) {
                            if (info.getData().isComplete()) {
                                finish();
                            } else if (info.getData().isNeedtostart()) {
                                Intent intent = new Intent(CerNumberTwoActivity.this, CerNumberActivity.class);
                                startActivity(intent);
                                UIUtils.showToast(retmsg);
                                finish();
                            } else if (info.getData().isSmscode()) {
//                                Intent intent = new Intent(MNO2Activity.this, MNO3Activity.class);
//                                intent.putExtra("taskid", info.getData().getTaskid());
//                                intent.putExtra("code", info.getData().getCode());
//                                intent.putExtra("nextstage", info.getData().getNextstage());
//                                startActivity(intent);
//                                UIUtils.showToast(retmsg);
//                                finish();
                            }

                        }
                    } else {
                        UIUtils.showToast(retmsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
}
