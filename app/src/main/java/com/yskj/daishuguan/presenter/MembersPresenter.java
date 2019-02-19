package com.yskj.daishuguan.presenter;


import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.MemberSmsRequest;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.ManagementListRequest;
import com.yskj.daishuguan.entity.request.MembersRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.modle.BillView;
import com.yskj.daishuguan.modle.MembersView;
import com.yskj.daishuguan.response.BillResponse;
import com.yskj.daishuguan.response.ManagementResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class MembersPresenter extends BasePresenter<MembersView> {
    public MembersPresenter(MembersView view) {
        super(view);
    }

    public void creditList(MembersRequest request) {

        addSubscription(mApiService.member(BaseParams.getParams(request.params())), new SubscriberCallBack<String>() {

            @Override
            protected void onSuccess(String response) {

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

    public void couponTotal(OCRRequest request) {

        addSubscription(mApiService.couponTotal(BaseParams.getParams(request.params())), new SubscriberCallBack<ManagementResponse>() {

            @Override
            protected void onSuccess(ManagementResponse response) {

                mView.onNumberSuccess(response);
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

    public void memberPayment(BannerRequest request) {

        addSubscription(mApiService.memberPayment(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                mView.onSendSuccess(response);
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


    public void memberConfirmRepayment(MemberSmsRequest request) {

        addSubscription(mApiService.memberConfirmRepayment(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                mView.onSmsSuccess(response);
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
