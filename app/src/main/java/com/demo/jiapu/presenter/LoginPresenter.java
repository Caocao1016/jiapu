package com.demo.jiapu.presenter;

import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.modle.LoginView;

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

//    public void getLogin(LoginRequest request) {
//
//        addSubscription(mApiService.login(BaseParams.getParams(request.params())), new SubscriberCallBack<LoginResponse>() {
//
//            @Override
//            protected void onSuccess(LoginResponse response) {
//
//                mView.onSuccess(response);
//            }
//
//            @Override
//            protected void onError() {
//                mView.onError();
//            }
//            @Override
//            protected void onFailure(BaseResponse response) {
//                mView.onFailure(response);
//            }
//        });
//    }
//
//    public void getLoginCode(SmsCaptchaRequest request) {
//
//        addSubscription(mApiService.getLoginCode(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {
//
//            @Override
//            protected void onSuccess(BaseResponse response) {
//
//                mView.onCodeSuccess(response);
//            }
//
//            @Override
//            protected void onError() {
//                mView.onError();
//            }
//
//            @Override
//            protected void onFailure(BaseResponse response) {
//               mView.onFailure(response);
//            }
//        });
//    }

}
