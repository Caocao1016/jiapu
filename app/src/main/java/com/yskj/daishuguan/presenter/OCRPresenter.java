package com.yskj.daishuguan.presenter;

import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.PostRequest;
import com.vondear.rxtool.RxLogTool;
import com.yskj.daishuguan.api.ApiConstant;
import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.FaceRequest;
import com.yskj.daishuguan.entity.request.FileRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.modle.OCRView;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description: 认证
 */

public class OCRPresenter extends BasePresenter<OCRView> {

    public OCRPresenter(OCRView view) {
        super(view);
    }

    public void postIDCard(OCRRequest request) {

        addSubscription(mApiService.confirmIdCardInfo(BaseParams.getParams(request.params())), new SubscriberCallBack<BaseRequest>() {

            @Override
            protected void onSuccess(BaseRequest response) {

                mView.onIDCardSuccess(response);
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
