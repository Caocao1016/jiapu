package com.demo.jiapu.presenter;

import android.util.Log;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.entity.JpsjAddRequest;
import com.demo.jiapu.modle.HomeRigView;
import com.demo.jiapu.response.JpsjListResponse;

public class HomeRigPresenter extends BasePresenter<HomeRigView> {
    public HomeRigPresenter(HomeRigView view) {
        super(view);
    }

    public void getList() {

        addSubscription(mApiService.selList(), new SubscriberCallBack<JpsjListResponse>() {

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

    public void addJpsj() {

        JpsjAddRequest request = new JpsjAddRequest();
        request.create_time = System.currentTimeMillis();
        request.title = "111";
        request.jp_img = "111";
        addSubscription(mApiService.addJpsj(request.params()), new SubscriberCallBack<BaseResponse>() {

            @Override
            protected void onSuccess(BaseResponse response) {

                Log.e("---",response.getMsg());
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
