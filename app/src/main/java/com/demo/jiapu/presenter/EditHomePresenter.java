package com.demo.jiapu.presenter;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.entity.selSjjpRequest;
import com.demo.jiapu.modle.EditHomeView;

import java.util.List;

public class EditHomePresenter extends BasePresenter<EditHomeView> {
    public EditHomePresenter(EditHomeView view) {
        super(view);
    }

    public void getList(selSjjpRequest request) {

        addSubscription(mApiService.selSjjp(request.params()), new SubscriberCallBack<List<FamilyBean>>() {

            @Override
            protected void onSuccess(List<FamilyBean> response) {
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
