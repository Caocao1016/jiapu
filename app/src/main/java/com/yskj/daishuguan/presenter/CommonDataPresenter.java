package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.AuInfoRequest;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.SubmitRequest;
import com.yskj.daishuguan.modle.CommonDataView;
import com.yskj.daishuguan.response.AuthitemInfoResponse;
import com.yskj.daishuguan.response.BannerResponse;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class CommonDataPresenter extends BasePresenter<CommonDataView> {
    public CommonDataPresenter(CommonDataView view) {
        super(view);
    }

    public void getCommonData(BannerRequest request) {


        addSubscription(mApiService.geMyCommonData(BaseParams.getParams(request.params())), new SubscriberCallBack<CommonDataResponse>() {

            @Override
            protected void onSuccess(CommonDataResponse response) {

                mView.onGetCommonDataSuccess(response);
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

    public void getBanner(BannerRequest request) {

        addSubscription(mApiService.banner(BaseParams.getParams(request.params())), new SubscriberCallBack<List<BannerResponse>>() {

            @Override
            protected void onSuccess(List<BannerResponse> response) {

                mView.onGetBannerSuccess(response);
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

    public void getSubmit(SubmitRequest request) {

        addSubscription(mApiService.submit(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                mView.onSubmitSuccess(response);
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
