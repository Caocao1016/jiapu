package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.LoginRequest;
import com.yskj.daishuguan.entity.request.SmsCaptchaRequest;
import com.yskj.daishuguan.modle.CommonDataView;
import com.yskj.daishuguan.modle.LoginView;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    public LoginPresenter(LoginView view) {
        super(view);
    }

    public void getLogin(LoginRequest request) {

        addSubscription(mApiService.login(BaseParams.getParams(request.params())), new SubscriberCallBack<LoginResponse>() {

            @Override
            protected void onSuccess(LoginResponse response) {

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

    public void getLoginCode(SmsCaptchaRequest request) {

        addSubscription(mApiService.getLoginCode(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                mView.onCodeSuccess(response);
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

}
