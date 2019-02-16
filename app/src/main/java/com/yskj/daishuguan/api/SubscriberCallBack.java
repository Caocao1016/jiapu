package com.yskj.daishuguan.api;

import android.content.Intent;

import com.socks.library.KLog;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.activity.LoginActivity;
import com.yskj.daishuguan.base.BaseApp;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.util.UIUtils;

import rx.Subscriber;


/**
 * @author ChayChan
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class SubscriberCallBack<T> extends Subscriber<BaseResponse<T>> {

    @Override
    public void onNext(BaseResponse response) {
        boolean isSuccess = (response.getRetcode() == 1000);
        if (isSuccess) {
            onSuccess((T) response.getData());
        } else {
            UIUtils.showToast(response.getRetmsg());
            if (response.getRetmsg().contains("未登录")) {
                RxSPTool.clearPreference(BaseApp.getContext(), Constant.TOKEN, Constant.TOKEN);
                RxSPTool.clearPreference(BaseApp.getContext(), Constant.USER_HEAD, Constant.USER_HEAD);
                RxSPTool.clearPreference(BaseApp.getContext(), Constant.USER_MOBILENO, Constant.USER_MOBILENO);
                RxSPTool.clearPreference(BaseApp.getContext(), Constant.USER_NAME, Constant.USER_NAME);
                RxSPTool.clearPreference(BaseApp.getContext(), Constant.USER_ID, Constant.USER_ID);
                RxSPTool.putString(BaseApp.getContext(), Constant.IS_LOGIN, "0");
                RxSPTool.clearPreference(BaseApp.getContext(), Constant.INVITATION_CODE, Constant.INVITATION_CODE);
                BaseApp.getContext().startActivity(new Intent(BaseApp.getContext(), LoginActivity.class)) ;
            }else {
                UIUtils.showToast(response.getRetmsg());
            }
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
    }

}
