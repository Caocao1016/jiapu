package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;
import com.yskj.daishuguan.response.RegisterResponse;
import com.yskj.daishuguan.response.UserInfoResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface UserInfoView {

    void onSuccess(UserInfoResponse response);
    void onCommonDataSuccess(CommonDataResponse response);
    void onHomeInfoSuccess(HomeInfoResponse response);
    void onFailure(BaseResponse response);

    void onError();
}
