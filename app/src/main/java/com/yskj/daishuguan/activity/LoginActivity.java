package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hjq.baselibrary.widget.ClearEditText;
import com.hjq.baselibrary.widget.CountdownView;
import com.hjq.toast.ToastUtils;
import com.vondear.rxtool.RxConstTool;
import com.vondear.rxtool.RxDeviceTool;
import com.vondear.rxtool.RxLocationTool;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.evbus.LoginEvbusBean;
import com.yskj.daishuguan.entity.request.LoginRequest;
import com.yskj.daishuguan.entity.request.SmsCaptchaRequest;
import com.yskj.daishuguan.modle.LoginView;
import com.yskj.daishuguan.presenter.LoginPresenter;
import com.yskj.daishuguan.response.LoginResponse;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2018/11/22
 *
 * @ClassName: LoginActivity
 * @Description: 登陆页面
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.et_login_phone)
    ClearEditText mETphone;
    @BindView(R.id.et_login_password)
    ClearEditText mETpassWord;
    @BindView(R.id.cv_register_countdown)
    CountdownView mRcountdown;
    private String latitude;
    private String location;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        mETphone.setText(StringUtil.getValue(getIntent().getStringExtra("phone")));
        mETphone.setText("15686141308");

    }

    @Override
    protected void initData() {

        latitude = RxSPTool.getString(LoginActivity.this, Constant.GPS_LATITUDE);
        location = RxSPTool.getString(LoginActivity.this, Constant.GPS_LONGITUDE);

    }

    @OnClick()
    public void onClick(View view) {

    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onRightClick(View v) {
        // 跳转到注册界面
        Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("phone",mETphone.getText().toString());
        startActivity(intent);
        finish();
    }


    @Override
    public void onSuccess(LoginResponse response) {
        UIUtils.showToast("登录成功");
        RxSPTool.putString(this, Constant.TOKEN, response.getToken());
        RxSPTool.putString(this, Constant.USER_HEAD, response.getUserimg());
        RxSPTool.putString(this, Constant.USER_MOBILENO, response.getMobileno());
        RxSPTool.putString(this, Constant.USER_NAME, response.getRealname());
        RxSPTool.putString(this, Constant.USER_ID, response.getUserid());
        RxSPTool.putString(this, Constant.INVITATION_CODE, response.getInvitationcode());
        RxSPTool.putString(this, Constant.IS_LOGIN, "1");
        EventBus.getDefault().post(new LoginEvbusBean());
        finish();
    }

    @Override
    public void onCodeSuccess(BaseResponse response) {
        UIUtils.showToast("验证码发送成功");
    }

    @Override
    public void onError() {
        UIUtils.showToast("服务器异常，请稍后再试");
        rxDialogLoading.dismiss();
    }

    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
        UIUtils.showToast(response.getRetmsg());
        if (response.getRetcode() == 1502) {
            Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("phone",mETphone.getText().toString());
            startActivity(intent);
            finish();
        }
    }


    @OnClick({R.id.cv_register_countdown, R.id.tv_login, R.id.login_forget_password})
    public void submit(View view) {
        switch (view.getId()) {
            case R.id.cv_register_countdown:

                if (StringUtil.isEmpty(mETphone.getText().toString())) {
                    // 重置验证码倒计时控件
                    mRcountdown.resetState();
                    UIUtils.showToast("请输入手机号码");
                    return;
                }
                if (!StringUtil.isMobileNO(mETphone.getText().toString())) {
                    // 重置验证码倒计时控件
                    mRcountdown.resetState();
                    UIUtils.showToast("请检查手机号码");
                    return;
                }

                SmsCaptchaRequest smsCaptchaRequest = new SmsCaptchaRequest();
                smsCaptchaRequest.mobileno = mETphone.getText().toString();
                smsCaptchaRequest.reqType = "login";
                mPresenter.getLoginCode(smsCaptchaRequest);
                break;
            case R.id.tv_login:
                if (StringUtil.isEmpty(mETphone.getText().toString())) {
                    UIUtils.showToast("请输入手机号码");
                    return;
                }
                if (!StringUtil.isMobileNO(mETphone.getText().toString())) {
                    UIUtils.showToast("请检查手机号码");
                    return;
                }

                if (StringUtil.isEmpty(mETpassWord.getText().toString())) {
                    UIUtils.showToast("请输入验证码");
                    return;
                }

                rxDialogLoading.show();
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.reqtype = "login" ;
                loginRequest.locaddress = ""    ;
                loginRequest.locgps = "" ;
                loginRequest.mobileno = mETphone.getText().toString() ;
                loginRequest.verifycode = mETpassWord.getText().toString() ;

                mPresenter.getLogin(loginRequest);
                break;
            case R.id.login_forget_password:
                break;
            default:
                break;
        }
    }
}
