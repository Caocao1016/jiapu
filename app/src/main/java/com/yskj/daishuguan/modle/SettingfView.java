package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface SettingfView {

    void onLogOutSuccess(BaseResponse response);
    void onPassWordSuccess(BaseResponse response);
    void onPhoneSuccess(BaseResponse response);
    void onError();
}
