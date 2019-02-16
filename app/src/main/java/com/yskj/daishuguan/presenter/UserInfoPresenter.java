package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.entity.request.LoginRequest;
import com.yskj.daishuguan.entity.request.UserInfoRequest;
import com.yskj.daishuguan.modle.LoginView;
import com.yskj.daishuguan.modle.UserInfoView;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.LoginResponse;
import com.yskj.daishuguan.response.UserInfoResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class UserInfoPresenter extends BasePresenter<UserInfoView> {
    public UserInfoPresenter(UserInfoView view) {
        super(view);
    }

    public void getuserInfo(UserInfoRequest request) {

        addSubscription(mApiService.userInfo(BaseParams.getParams(request.params())), new SubscriberCallBack<UserInfoResponse>() {

            @Override
            protected void onSuccess(UserInfoResponse response) {

                mView.onSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
        public void geMyCommonData() {

        addSubscription(mApiService.geMyCommonData(BaseParams.getParams(null)), new SubscriberCallBack<CommonDataResponse>() {

            @Override
            protected void onSuccess(CommonDataResponse response) {

                mView.onCommonDataSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

}
