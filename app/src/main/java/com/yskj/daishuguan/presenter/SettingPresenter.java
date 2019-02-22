package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.LoginRequest;
import com.yskj.daishuguan.entity.request.SettingPwRequest;
import com.yskj.daishuguan.entity.request.SettingRequest;
import com.yskj.daishuguan.modle.LoginView;
import com.yskj.daishuguan.modle.SettingfView;
import com.yskj.daishuguan.response.LoginResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class SettingPresenter extends BasePresenter<SettingfView> {
    public SettingPresenter(SettingfView view) {
        super(view);
    }

    public void getLogout(SettingRequest request) {

        addSubscription(mApiService.logout(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                mView.onLogOutSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }






}
