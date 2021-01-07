package com.demo.jiapu.presenter;

import android.util.Log;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.MemberBean;
import com.demo.jiapu.entity.SelGrjpRequest;
import com.demo.jiapu.modle.HomeLeftView;

import java.util.List;

public class HomeLeftPresenter extends BasePresenter<HomeLeftView> {
    public HomeLeftPresenter(HomeLeftView view) {
        super(view);
    }

    public void getLogin() {
        SelGrjpRequest request = new SelGrjpRequest();

        request.userId = "5";

        addSubscription(mApiService.selGrjp(request.params()), new SubscriberCallBack<List<MemberBean>>() {

            @Override
            protected void onSuccess(List<MemberBean> response) {
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


}
