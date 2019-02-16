package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.response.AppVersionResponse;
import com.yskj.daishuguan.response.BlacklistResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface BlacklistView {

    void onSuccess(BlacklistResponse response);

    void onError();
}
