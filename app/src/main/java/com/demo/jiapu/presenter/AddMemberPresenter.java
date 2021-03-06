package com.demo.jiapu.presenter;

import android.util.Log;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.entity.AddGrjpRequest;
import com.demo.jiapu.entity.EditGrjpRequest;
import com.demo.jiapu.modle.AddMemberView;

public class AddMemberPresenter extends BasePresenter<AddMemberView> {
    public AddMemberPresenter(AddMemberView view) {
        super(view);
    }

    public void addMember(AddGrjpRequest addGrjpRequest) {
        addSubscription(mApiService.addGrjp(addGrjpRequest.params()), new SubscriberCallBack<String>() {
            @Override
            protected void onSuccess(String response) {

                mView.onSuccess("添加成功");
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
    public void editMember(EditGrjpRequest editGrjpRequest) {
        addSubscription(mApiService.editGrjp(editGrjpRequest.params()), new SubscriberCallBack<String>() {
            @Override
            protected void onSuccess(String response) {
                mView.onSuccess("编辑成功");
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
