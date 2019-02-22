package com.yskj.daishuguan.presenter;


import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.AuthorRequest;
import com.yskj.daishuguan.entity.request.SettingAuthorRequest;
import com.yskj.daishuguan.modle.SettingAuthorizaView;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class SettingAuthorizationPresenter extends BasePresenter<SettingAuthorizaView> {
    public SettingAuthorizationPresenter(SettingAuthorizaView view) {
        super(view);
    }

}
