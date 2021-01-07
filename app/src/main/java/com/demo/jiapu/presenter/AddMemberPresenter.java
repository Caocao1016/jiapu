package com.demo.jiapu.presenter;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.entity.AddGrjpRequest;
import com.demo.jiapu.modle.AddMemberView;

public class AddMemberPresenter extends BasePresenter<AddMemberView> {
    public AddMemberPresenter(AddMemberView view) {
        super(view);
    }

    public void addMember(AddGrjpRequest addGrjpRequest) {
        addSubscription(mApiService.addGrjp(addGrjpRequest.params()), new SubscriberCallBack<BaseResponse>() {
            @Override
            protected void onSuccess(BaseResponse response) {

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
