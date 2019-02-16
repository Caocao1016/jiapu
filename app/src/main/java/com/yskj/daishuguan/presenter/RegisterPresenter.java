package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.LoginRequest;
import com.yskj.daishuguan.entity.request.SmsCaptchaRequest;
import com.yskj.daishuguan.modle.LoginView;
import com.yskj.daishuguan.modle.RegisterView;
import com.yskj.daishuguan.response.CaptchaCodeResponse;
import com.yskj.daishuguan.response.LoginResponse;
import com.yskj.daishuguan.response.RegisterResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {
    public RegisterPresenter(RegisterView view) {
        super(view);
    }

    public void getRegister(LoginRequest request) {

        addSubscription(mApiService.register(BaseParams.getParams(request.params())), new SubscriberCallBack<RegisterResponse>() {

            @Override
            protected void onSuccess(RegisterResponse response) {

                mView.onSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }

            @Override
            protected void onFailure(BaseResponse response) {
                mView.onFailure(response);
            }
        });

    }

    public void captchaCode(SmsCaptchaRequest request) {

        addSubscription(mApiService.captchaCode(BaseParams.getParams(request.params())), new SubscriberCallBack<CaptchaCodeResponse>() {

            @Override
            protected void onSuccess(CaptchaCodeResponse response) {

                mView.onImgCodeSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }

            @Override
            protected void onFailure(BaseResponse response) {
                mView.onFailure(response);
            }
        });

    }


    public void getQuestSmsCaptcha(SmsCaptchaRequest request) {

        addSubscription(mApiService.getLoginCode(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                mView.onSmsSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onSmsError();
            }

            @Override
            protected void onFailure(BaseResponse response) {
                mView.onFailure(response);
            }
        });
    }

}
