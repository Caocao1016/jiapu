package com.yskj.daishuguan.modle;


import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;
import com.yskj.daishuguan.response.BillHuankuanResponse;
import com.yskj.daishuguan.response.BillResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface BillView {

    void onSuccess(BillResponse response);
    void onHuanKuanSuccess(BillHuankuanResponse response);
    void onFailure(BaseResponse response);
    void onError();
}
