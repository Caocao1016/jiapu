package com.demo.jiapu.modle;

import com.demo.jiapu.base.BaseResponse;

public interface CreateHomeView {

    void onSuccess(String url);
    void onAddSuccess();

    void onError();
    void onFailure(BaseResponse response);

}
