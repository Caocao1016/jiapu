package com.demo.jiapu.modle;

import com.demo.jiapu.base.BaseResponse;

public interface AddMemberView {
    void onSuccess(String response);


    void onError();
    void onFailure(BaseResponse response);
}
