package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.CardResponse;
import com.yskj.daishuguan.response.CardSmsResponse;
import com.yskj.daishuguan.response.ManagementResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface MembersView {

    void onSuccess(BaseResponse response);
    void onNumberSuccess(ManagementResponse response);
    void onFailure(BaseResponse response);

    void onError();
}
