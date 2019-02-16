package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.AuthitemInfoResponse;
import com.yskj.daishuguan.response.BannerResponse;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface CommonDataView {

    void onGetCommonDataSuccess(CommonDataResponse response);

    void onGetBannerSuccess(List<BannerResponse> response);

    void onHomeInfoSuccess(HomeInfoResponse response);
    void onSubmitSuccess(BaseResponse response);

    void onFailure(BaseResponse response);

    void onError();
}
