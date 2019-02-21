package com.yskj.daishuguan.presenter;


import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.DetailRequest;
import com.yskj.daishuguan.entity.request.HuanKuanRequest;
import com.yskj.daishuguan.entity.request.ManagementListRequest;
import com.yskj.daishuguan.modle.BillView;
import com.yskj.daishuguan.modle.DetailView;
import com.yskj.daishuguan.response.BillHuankuanResponse;
import com.yskj.daishuguan.response.BillResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class DetailPresenter extends BasePresenter<DetailView> {
    public DetailPresenter(DetailView view) {
        super(view);
    }

    public void initiativeExtend(DetailRequest request) {

        addSubscription(mApiService.initiativeExtend(BaseParams.getParams(request.params())), new SubscriberCallBack<String>() {

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
                mView.onSMsFailure(response);
            }
        });
    }


}
