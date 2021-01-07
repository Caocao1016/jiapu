package com.demo.jiapu.modle;

import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.MemberBean;

import java.util.List;


public interface HomeLeftView {

    void onSuccess(List<MemberBean> response);


    void onError();
    void onFailure(BaseResponse response);

}
