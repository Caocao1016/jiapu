package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.baselibrary.widget.ClearEditText;
import com.hjq.baselibrary.widget.CountdownView;
import com.hjq.toast.ToastUtils;
import com.vondear.rxtool.RxDeviceTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.BuildConfig;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.evbus.LoginEvbusBean;
import com.yskj.daishuguan.entity.evbus.StickyEvenbus;
import com.yskj.daishuguan.entity.request.LoginRequest;
import com.yskj.daishuguan.entity.request.SmsCaptchaRequest;
import com.yskj.daishuguan.modle.RegisterView;
import com.yskj.daishuguan.presenter.RegisterPresenter;
import com.yskj.daishuguan.response.CaptchaCodeResponse;
import com.yskj.daishuguan.response.RegisterResponse;
import com.yskj.daishuguan.util.EditTextInputHelper;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * CaoPengFei
 * 2018/11/26
 *
 * @ClassName: RegisterActivity
 * @Description:
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView {


    @BindView(R.id.et_register_phone)
    ClearEditText mRPhone;
    @BindView(R.id.et_register_password)
    ClearEditText mRPassWord;
    @BindView(R.id.et_code)
    ClearEditText mRcode;
    @BindView(R.id.cv_register_countdown)
    CountdownView mRcountdown;
    @BindView(R.id.tv_register)
    TextView mRregister;
    @BindView(R.id.img_code)
    ImageView img_code;
    private String token;

//    private String latitude;
//    private String location;


    @Override
    public void onRightClick(View v) {
        startActivity(LoginActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_reg_title;
    }

    @Override
    protected void initView() {

        mRPhone.setText(StringUtil.getValue(getIntent().getStringExtra("phone")));
    }

    @Override
    protected void initData() {

        SmsCaptchaRequest smsCaptchaRequest = new SmsCaptchaRequest();
        smsCaptchaRequest.mobileno = mRPhone.getText().toString().trim();
        smsCaptchaRequest.reqType = "register";

        mPresenter.captchaCode(smsCaptchaRequest);
//        latitude = RxSPTool.getString(RegisterActivity.this, Constant.GPS_LATITUDE);
//        location = RxSPTool.getString(RegisterActivity.this, Constant.GPS_LONGITUDE);

    }

    /**
     * {@link View.OnClickListener}
     */
    @OnClick({R.id.tv_register, R.id.cv_register_countdown, R.id.login_forget_password, R.id.img_code})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cv_register_countdown:
                if (StringUtil.isEmpty(mRPhone.getText().toString())) {
                    // 重置验证码倒计时控件
                    mRcountdown.resetState();
                    UIUtils.showToast("请输入手机号码");
                    return;
                }
                if (!StringUtil.isMobileNO(mRPhone.getText().toString())) {
                    // 重置验证码倒计时控件
                    mRcountdown.resetState();
                    UIUtils.showToast("请检查手机号码");
                    return;
                }
                if (mRcode.length() < 4) {
                    // 重置验证码倒计时控件
                    mRcountdown.resetState();
                    UIUtils.showToast("请输入正确的图形验证码");
                    return;
                }

                SmsCaptchaRequest smsCaptchaRequest = new SmsCaptchaRequest();
                smsCaptchaRequest.captcha = mRcode.getText().toString().trim();
                smsCaptchaRequest.mobileno = mRPhone.getText().toString().trim();
                smsCaptchaRequest.reqType = "register";
                smsCaptchaRequest.token = RxSPTool.getString(this, Constant.TOKEN);
                mPresenter.getQuestSmsCaptcha(smsCaptchaRequest);
                break;

            case R.id.tv_register:

                if (StringUtil.isEmpty(mRPhone.getText().toString())) {
                    UIUtils.showToast("请输入手机号码");
                    return;
                }
                if (!StringUtil.isMobileNO(mRPhone.getText().toString())) {
                    UIUtils.showToast("请检查手机号码");
                    return;
                }

                if (StringUtil.isEmpty(mRPassWord.getText().toString())) {
                    UIUtils.showToast("请输入验证码");
                    return;
                }
                if (StringUtil.isEmpty(mRcode.getText().toString())) {
                    UIUtils.showToast("请输入图形码");
                    return;
                }
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.reqtype = "register";
                loginRequest.locaddress = "";
                loginRequest.locgps = "";
                loginRequest.invitationcode = "";
                loginRequest.mobileno = mRPhone.getText().toString().trim();
                loginRequest.verifycode = mRPassWord.getText().toString().trim();
                mPresenter.getRegister(loginRequest);
                break;
            case R.id.img_code:
                mRcode.setText("");
                initData();
                break;
            case R.id.login_forget_password:
                Intent intent2 = new Intent(RegisterActivity.this, WebViewActivity.class);
                intent2.putExtra(Constant.WEBVIEW_URL, RxSPTool.getString(this,Constant.REGISTER_PROTOCOL));
                intent2.putExtra(Constant.WEBVIEW_URL_TITLE,"用户服务协议");
                startActivity(intent2);
                break;
            default:
                break;
        }

    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSuccess(RegisterResponse response) {

        UIUtils.showToast("注册成功，这里属于你...");
        RxSPTool.putString(this, Constant.TOKEN, response.getToken());
        RxSPTool.putString(this, Constant.USER_MOBILENO, response.getMobileno());
        RxSPTool.putString(this, Constant.USER_ID, response.getUserid());
        RxSPTool.putString(this, Constant.USER_NAME, response.getName());
        RxSPTool.putString(this, Constant.INVITATION_CODE, response.getInvitationcode());
        RxSPTool.putString(this, Constant.IS_LOGIN, "1");
        EventBus.getDefault().post(new LoginEvbusBean());
        EventBus.getDefault().postSticky(new StickyEvenbus());
        finish();
    }

    @Override
    public void onSmsSuccess(BaseResponse response) {
        UIUtils.showToast("验证码发送成功");
    }

    @Override
    public void onFailure(BaseResponse response) {
        UIUtils.showToast(response.getRetmsg());
        if (1007 == response.getRetcode() || 1006 == response.getRetcode()) {
            mRcountdown.resetState();
            mRcode.setText("");
            initData();
        } else if (1501 == response.getRetcode()) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("phone", mRPhone.getText().toString());
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onImgCodeSuccess(CaptchaCodeResponse response) {
        String imgBase64 = response.getImgBase64();
        RxSPTool.putString(this, Constant.TOKEN, response.getToken());
        if (!imgBase64.isEmpty()) {
            byte[] bytes = Base64.decode(imgBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            img_code.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onSmsError() {
    }

    @Override
    public void onError() {

    }
}
