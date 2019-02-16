package com.yskj.daishuguan.modle;


import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface SettingAuthorizaView {

    void onSuccess(BaseResponse response);
    void onremoveSuccess(BaseResponse response);
    void onListSuccess(AuthorizeRecordResponse response);
    void onRightSuccess(AuthorizeRecordResponse response);
    void onFailure(BaseResponse response);
    void onSFailure(BaseResponse response);
    void onremoveFailure(BaseResponse response);
    void onAuthorFailure(BaseResponse response);
    void onError();
}
