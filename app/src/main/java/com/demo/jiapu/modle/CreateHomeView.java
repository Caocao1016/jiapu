package com.demo.jiapu.modle;

import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.MemberBean;

import java.util.List;

public interface CreateHomeView {

    void onSuccess(String url);
    void onAddSuccess();

    void onError();
    void onFailure(BaseResponse response);

}
