package com.demo.jiapu.presenter;

import android.util.Log;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.entity.JpsjAddRequest;
import com.demo.jiapu.entity.ListRequest;
import com.demo.jiapu.entity.SelSjjpRequest;
import com.demo.jiapu.modle.HomeRigView;
import com.demo.jiapu.response.JpsjListResponse;

public class HomeRigPresenter extends BasePresenter<HomeRigView> {
    public HomeRigPresenter(HomeRigView view) {
        super(view);
    }

    public void getList(ListRequest mListRequest) {

        addSubscription(mApiService.selList(mListRequest.params()), new SubscriberCallBack<JpsjListResponse>() {

            @Override
            protected void onSuccess(JpsjListResponse response) {

                mView.onSuccess(response.getData());
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

    public void getHomeList(ListRequest mListRequest) {

        addSubscription(mApiService.selMylist(mListRequest.params()), new SubscriberCallBack<JpsjListResponse>() {

            @Override
            protected void onSuccess(JpsjListResponse response) {

                mView.onHomeSuccess(response.getData());
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

    public void editZhiding(long id) {

        SelSjjpRequest mListRequest = new SelSjjpRequest();
        mListRequest.id = id;
        addSubscription(mApiService.editZhiding(mListRequest.params()), new SubscriberCallBack<String>() {

            @Override
            protected void onSuccess(String response) {

                mView.onEditZhidingSuccess(response);
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
