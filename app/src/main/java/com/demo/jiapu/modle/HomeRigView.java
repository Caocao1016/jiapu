package com.demo.jiapu.modle;

import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.JpsjListDataBean;

import java.util.List;


public interface HomeRigView {

    void onSuccess(List<JpsjListDataBean> response);


    void onError();
    void onFailure(BaseResponse response);

}
