package com.demo.jiapu.activity;

import android.view.View;

import com.hjq.baselibrary.widget.ClearEditText;
import com.hjq.baselibrary.widget.CountdownView;
import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.modle.LoginView;
import com.demo.jiapu.presenter.LoginPresenter;
import com.demo.jiapu.response.LoginResponse;
import com.demo.jiapu.util.StringUtil;
import com.demo.jiapu.util.UIUtils;
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
//        mETphone.setText("15381097820");

    }

    @Override
    protected void initData() {

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

    }

    @Override
    public void onSuccess(LoginResponse response) {
        UIUtils.showToast("登录成功");

        finish();
    }

    @Override
    public void onCodeSuccess(BaseResponse response) {
        UIUtils.showToast("验证码发送成功");
    }

    @Override
    public void onError() {
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @OnClick({R.id.cv_register_countdown, R.id.tv_login, R.id.login_forget_password})
    public void submit(View view) {
        switch (view.getId()) {
            case R.id.cv_register_countdown:

                break;
        }
    }
}
