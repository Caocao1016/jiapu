package com.demo.jiapu.presenter;

import com.demo.jiapu.api.SubscriberCallBack;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.entity.ReportRequest;
import com.demo.jiapu.entity.SelGrjpRequest;
import com.demo.jiapu.modle.HomeLeftView;
import com.demo.jiapu.modle.ReportView;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReportPresenter extends BasePresenter<ReportView> {
    public ReportPresenter(ReportView view) {
        super(view);
    }

    public void getList(ReportRequest mReportRequest) {


        addSubscription(mApiService.addReport1(mReportRequest.params()), new SubscriberCallBack<String>() {

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
    public void sendMsgfile(File file) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file_name", file.getName(), requestBody);


        addSubscription(mApiService.sendMsgfile(body), new SubscriberCallBack<String>() {

            @Override
            protected void onSuccess(String response) {
                mView.onPhotoSuccess(response);
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
