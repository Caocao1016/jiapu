package com.demo.jiapu.presenter;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.entity.ZdppRequest;
import com.demo.jiapu.modle.AutoMateView;
import com.demo.jiapu.response.ZdppResponse;

import java.util.concurrent.TimeUnit;

public class AutoMatePresenter extends BasePresenter<AutoMateView> {
    public AutoMatePresenter(AutoMateView view) {
        super(view);
    }

    public void getList(ZdppRequest zdppRequest) {
        addSubscription(mApiService.zdpp(zdppRequest.params()).delay(1,TimeUnit.SECONDS), new SubscriberCallBack<ZdppResponse>() {
            @Override
            protected void onSuccess(ZdppResponse response) {
                mView.onSuccess(response.getId(), response.getData());
            }

            @Override
            protected void onError() {
                mView.onError();
            }

            @Override
            protected void onFailure(BaseResponse response) {
                super.onFailure(response);
            }
        });
    }
}
