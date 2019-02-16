package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.CardResponse;
import com.yskj.daishuguan.response.CardSmsResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface CardView {

    void onSuccess(CardResponse response);
    void onSmsSuccess(CardSmsResponse response);
    void onFailure(BaseResponse response);
    void onCodeSuccess(BaseResponse response);
    void onError();
}
