package com.yskj.daishuguan.presenter;


import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.AuthorRequest;
import com.yskj.daishuguan.entity.request.SettingAuthorRequest;
import com.yskj.daishuguan.modle.SettingAuthorizaView;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class SettingAuthorizationPresenter extends BasePresenter<SettingAuthorizaView> {
    public SettingAuthorizationPresenter(SettingAuthorizaView view) {
        super(view);
    }

    public void getReadyForAuthorize(SettingAuthorRequest request) {

        addSubscription(mApiService.readyForAuthorize(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

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

    public void getdoAuthorize(SettingAuthorRequest request) {

        addSubscription(mApiService.doAuthorize(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                mView.onSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }

            @Override
            protected void onFailure(BaseResponse response) {
                mView.onSFailure(response);
            }
        });
    }


    public void myAuthorizeRecord(AuthorRequest request) {

        addSubscription(mApiService.myAuthorizeRecord(BaseParams.getParams(request.params())), new SubscriberCallBack<AuthorizeRecordResponse>() {

            @Override
            protected void onSuccess(AuthorizeRecordResponse response) {

                mView.onListSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }

            @Override
            protected void onFailure(BaseResponse response) {
                mView.onAuthorFailure(response);
            }
        });


    }




}
