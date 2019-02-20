package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.CardResponse;
import com.yskj.daishuguan.response.CardSmsResponse;
import com.yskj.daishuguan.response.ManagementListResponse;
import com.yskj.daishuguan.response.ManagementResponse;
import com.yskj.daishuguan.response.ShareContentResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface ManagementMoneyView {

    void onSuccess(ManagementResponse response);
    void onCouponUseSuccess(ManagementListResponse response);
    void onShareSuccess(ShareContentResponse response);
    void onFailure(BaseResponse response);
    void onError();
}
