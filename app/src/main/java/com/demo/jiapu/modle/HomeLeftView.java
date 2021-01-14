package com.demo.jiapu.modle;

import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.BuilderBean;
import com.demo.jiapu.bean.FamilyBean;

import java.util.List;


public interface HomeLeftView {

    void onSuccess(List<FamilyBean> response);

    void onError();
    void onFailure(BaseResponse response);

    void onBuilderSuccess(BuilderBean builderBean);
}
