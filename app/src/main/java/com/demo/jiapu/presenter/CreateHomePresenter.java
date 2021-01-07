package com.demo.jiapu.presenter;

import android.util.Log;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.MemberBean;
import com.demo.jiapu.entity.JpsjAddRequest;
import com.demo.jiapu.entity.SelGrjpRequest;
import com.demo.jiapu.modle.CreateHomeView;
import com.demo.jiapu.modle.HomeLeftView;
import com.demo.jiapu.util.FileUtil;
import com.hjq.toast.ToastUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateHomePresenter extends BasePresenter<CreateHomeView> {
    public CreateHomePresenter(CreateHomeView view) {
        super(view);
    }

    public void sendMsgfile(File file) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file_name", file.getName(), requestBody);


        addSubscription(mApiService.sendMsgfile(body), new SubscriberCallBack<String>() {

            @Override
            protected void onSuccess(String response) {
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

    public void addJpsj( JpsjAddRequest request) {
        addSubscription(mApiService.addJpsj(request.params()), new SubscriberCallBack<String>() {

            @Override
            protected void onSuccess(String response) {

                mView.onAddSuccess();
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
