package com.yskj.daishuguan.activity;

import android.view.View;
import android.widget.TextView;

import com.vondear.rxtool.RxDeviceTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseApp;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.evbus.LoginEvbusBean;
import com.yskj.daishuguan.entity.evbus.OutLoginEvbusBean;
import com.yskj.daishuguan.entity.evbus.StickyEvenbus;
import com.yskj.daishuguan.entity.evbus.liveResultEvbusBean;
import com.yskj.daishuguan.entity.request.SettingRequest;
import com.yskj.daishuguan.modle.SettingfView;
import com.yskj.daishuguan.presenter.SettingPresenter;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2018/11/22
 *
 * @ClassName: SettingActivity
 * @Description: 设置
 */

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingfView {

    @BindView(R.id.tv_app_version)
    TextView mVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_setting_title;
    }

    @Override
    protected void initView() {

        mVersion.setText("v" + RxDeviceTool.getAppVersionName(this));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }


    @OnClick({R.id.tv_exit, R.id.rl_phone, R.id.rl_password})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_exit:

                if (RxSPTool.getString(this, Constant.IS_LOGIN).equals("0")) {
                    return;
                }

                rxDialogLoading.show();
                SettingRequest settingRequest = new SettingRequest();
                settingRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
                settingRequest.token = RxSPTool.getString(this, Constant.TOKEN);
                settingRequest.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
                mPresenter.getLogout(settingRequest);
                break;
            case R.id.rl_phone:

                break;
            case R.id.rl_password:

                break;
        }
    }

    @Override
    public void onLogOutSuccess(BaseResponse response) {
        rxDialogLoading.dismiss();

        RxSPTool.remove(this, Constant.TOKEN);
        RxSPTool.remove(this, Constant.USER_HEAD);
        RxSPTool.remove(this, Constant.USER_MOBILENO);
        RxSPTool.remove(this, Constant.USER_NAME);
        RxSPTool.remove(this, Constant.USER_ID);
        RxSPTool.putString(this, Constant.IS_LOGIN, "0");
        EventBus.getDefault().post(new LoginEvbusBean());
        EventBus.getDefault().postSticky(new StickyEvenbus());
        startActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void onPassWordSuccess(BaseResponse response) {

    }

    @Override
    public void onPhoneSuccess(BaseResponse response) {

    }

    @Override
    public void onError() {
        rxDialogLoading.dismiss();
    }
}
