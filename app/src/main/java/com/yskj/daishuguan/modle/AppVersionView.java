package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.response.AppVersionResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface AppVersionView {

    void onSuccess(AppVersionResponse response);

    void onError();
}
