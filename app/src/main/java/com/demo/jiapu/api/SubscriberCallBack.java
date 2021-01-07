package com.demo.jiapu.api;

import com.socks.library.KLog;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.util.UIUtils;

import rx.Subscriber;


/**
 * @author ChayChan
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class SubscriberCallBack<T> extends Subscriber<BaseResponse<T>> {

    @Override
    public void onNext(BaseResponse response) {
        boolean isSuccess = (response.getCode() == 200);
        if (isSuccess) {
            onSuccess((T) response.getData());
        } else {
            UIUtils.showToast(response.getMsg());
            onFailure(response);
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        KLog.e("ApiRetrofit",e.getLocalizedMessage());
        KLog.e("ApiRetrofit",e);
        onError();
    }

    protected abstract void onSuccess(T response);

    protected abstract void onError();

    protected void onFailure(BaseResponse response) {
//        KLog.e("ApiRetrofit",response.getMsg());

    }

}
