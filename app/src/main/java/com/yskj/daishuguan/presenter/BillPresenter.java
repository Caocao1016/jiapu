package com.yskj.daishuguan.presenter;


import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.AuthorRequest;
import com.yskj.daishuguan.entity.request.HuanKuanRequest;
import com.yskj.daishuguan.entity.request.ManagementListRequest;
import com.yskj.daishuguan.entity.request.SettingAuthorRequest;
import com.yskj.daishuguan.modle.BillView;
import com.yskj.daishuguan.modle.SettingAuthorizaView;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;
import com.yskj.daishuguan.response.BillHuankuanResponse;
import com.yskj.daishuguan.response.BillResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class BillPresenter extends BasePresenter<BillView> {
    public BillPresenter(BillView view) {
        super(view);
    }

    public void creditList(ManagementListRequest request) {

        addSubscription(mApiService.creditList(BaseParams.getParams(request.params())), new SubscriberCallBack<BillResponse>() {

            @Override
            protected void onSuccess(BillResponse response) {

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


    public void bills(HuanKuanRequest request) {

        addSubscription(mApiService.bills(BaseParams.getParams(request.params())), new SubscriberCallBack<BillHuankuanResponse>() {

            @Override
            protected void onSuccess(BillHuankuanResponse response) {

                mView.onHuanKuanSuccess(response);
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
