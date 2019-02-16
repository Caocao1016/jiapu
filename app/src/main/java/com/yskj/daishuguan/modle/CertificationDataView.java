package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.CertificationResponse;

import java.util.List;

/**
 * CaoPengFei
 * 2019/2/13
 *
 * @ClassName: CertificationDataView
 * @Description:
 */

public interface CertificationDataView {


    void onAuthiteminfoSuccess(CertificationResponse response);
    void onSuccess(BaseResponse response);
    void onError();
    void onFailure(BaseResponse response);
}
