package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.entity.request.ShareRequest;
import com.yskj.daishuguan.modle.CertificationDataView;
import com.yskj.daishuguan.modle.ShareView;
import com.yskj.daishuguan.response.CertificationResponse;
import com.yskj.daishuguan.response.ShareContentResponse;
import com.yskj.daishuguan.response.ShareListResponse;
import com.yskj.daishuguan.response.ShareResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class SharePresenter extends BasePresenter<ShareView> {
    public SharePresenter(ShareView view) {
        super(view);
    }

    public void inviteHomePage(OCRRequest request) {


        addSubscription(mApiService.inviteHomePage(BaseParams.getParams(request.params())), new SubscriberCallBack<ShareResponse>() {

            @Override
            protected void onSuccess(ShareResponse response) {

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

    public void inviteRecord(ShareRequest request) {


        addSubscription(mApiService.inviteRecord(BaseParams.getParams(request.params())), new SubscriberCallBack<ShareListResponse>() {

            @Override
            protected void onSuccess(ShareListResponse response) {

                mView.onRecordSuccess(response);
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

    public void share(OCRRequest request) {


        addSubscription(mApiService.share(BaseParams.getParams(request.params())), new SubscriberCallBack<ShareContentResponse>() {

            @Override
            protected void onSuccess(ShareContentResponse response) {

                mView.onShareSuccess(response);
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
