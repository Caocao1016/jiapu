package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.modle.CertificationDataView;
import com.yskj.daishuguan.modle.CommonDataView;
import com.yskj.daishuguan.response.BannerResponse;
import com.yskj.daishuguan.response.CertificationResponse;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class CertificationPresenter extends BasePresenter<CertificationDataView> {
    public CertificationPresenter(CertificationDataView view) {
        super(view);
    }

    public void authiteminfo(BannerRequest request) {


        addSubscription(mApiService.authiteminfo(BaseParams.getParams(request.params())), new SubscriberCallBack<CertificationResponse>() {

            @Override
            protected void onSuccess(CertificationResponse response) {

                mView.onAuthiteminfoSuccess(response);
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
