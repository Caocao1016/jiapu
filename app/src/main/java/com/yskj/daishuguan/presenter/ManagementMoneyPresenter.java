package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.ManagementListRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.entity.request.SendSmsRequest;
import com.yskj.daishuguan.modle.CardView;
import com.yskj.daishuguan.modle.ManagementMoneyView;
import com.yskj.daishuguan.response.CardResponse;
import com.yskj.daishuguan.response.CardSmsResponse;
import com.yskj.daishuguan.response.ManagementListResponse;
import com.yskj.daishuguan.response.ManagementResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class ManagementMoneyPresenter extends BasePresenter<ManagementMoneyView> {

    public ManagementMoneyPresenter(ManagementMoneyView view) {
        super(view);
    }

    public void couponTotal(OCRRequest request) {

        addSubscription(mApiService.couponTotal(BaseParams.getParams(request.params())), new SubscriberCallBack<ManagementResponse>() {

            @Override
            protected void onSuccess(ManagementResponse response) {

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

    public void couponUse(ManagementListRequest request) {

        addSubscription(mApiService.couponUse(BaseParams.getParams(request.params())), new SubscriberCallBack<ManagementListResponse>() {

            @Override
            protected void onSuccess(ManagementListResponse response) {

                mView.onCouponUseSuccess(response);
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
