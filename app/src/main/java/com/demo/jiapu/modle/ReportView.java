package com.demo.jiapu.modle;

import com.demo.jiapu.base.BaseResponse;

public interface ReportView {
    void onSuccess(String response);
    void onPhotoSuccess(String response);

    void onError();
    void onFailure(BaseResponse response);
}
