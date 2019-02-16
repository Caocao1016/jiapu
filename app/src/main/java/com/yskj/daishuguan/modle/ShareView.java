package com.yskj.daishuguan.modle;

import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.response.AppVersionResponse;
import com.yskj.daishuguan.response.ShareContentResponse;
import com.yskj.daishuguan.response.ShareListResponse;
import com.yskj.daishuguan.response.ShareResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataView
 * @Description:
 */

public interface ShareView {

    void onSuccess(ShareResponse response);

    void onRecordSuccess(ShareListResponse response);
    void onShareSuccess(ShareContentResponse response);

    void onError();

    void onFailure(BaseResponse response);
}
