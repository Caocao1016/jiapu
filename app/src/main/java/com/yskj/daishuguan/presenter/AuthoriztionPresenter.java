package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.CreditStartRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.entity.request.SendSmsRequest;
import com.yskj.daishuguan.modle.AuthoriztionView;
import com.yskj.daishuguan.modle.CardView;
import com.yskj.daishuguan.response.CardResponse;
import com.yskj.daishuguan.response.CardSmsResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class AuthoriztionPresenter extends BasePresenter<AuthoriztionView> {

    public AuthoriztionPresenter(AuthoriztionView view) {
        super(view);
    }

    public void creditStart(CreditStartRequest request) {

        addSubscription(mApiService.creditStart(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

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
    public void homeInfo(BannerRequest request) {

        addSubscription(mApiService.homeInfo(BaseParams.getParams(request.params())), new SubscriberCallBack<HomeInfoResponse>() {

            @Override
            protected void onSuccess(HomeInfoResponse response) {

                mView.onHomeInfoSuccess(response);
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
