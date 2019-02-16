package com.yskj.daishuguan.presenter;

import com.lzy.okgo.request.BaseRequest;
import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.FaceRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.entity.request.SendSmsRequest;
import com.yskj.daishuguan.entity.request.SmsCaptchaRequest;
import com.yskj.daishuguan.modle.CardView;
import com.yskj.daishuguan.modle.OCRView;
import com.yskj.daishuguan.response.CardResponse;
import com.yskj.daishuguan.response.CardSmsResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class CardPresenter extends BasePresenter<CardView> {

    public CardPresenter(CardView view) {
        super(view);
    }

    public void postIDCard(OCRRequest request) {

        addSubscription(mApiService.qrybaseinfo(BaseParams.getParams(request.params())), new SubscriberCallBack<CardResponse>() {

            @Override
            protected void onSuccess(CardResponse response) {

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

    public void getAuthrealnamerequeset(SendSmsRequest request) {

        addSubscription(mApiService.authrealnamerequeset(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                mView.onCodeSuccess(response);
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

    public void sendMessage(SendSmsRequest request) {

        addSubscription(mApiService.sendMessage(BaseParams.getParams(request.params())), new SubscriberCallBack<CardSmsResponse>() {

            @Override
            protected void onSuccess(CardSmsResponse response) {

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
