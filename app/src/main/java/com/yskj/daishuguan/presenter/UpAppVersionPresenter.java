package com.yskj.daishuguan.presenter;

import com.yskj.daishuguan.api.SubscriberCallBack;
import com.yskj.daishuguan.base.BaseParams;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.LoginRequest;
import com.yskj.daishuguan.modle.AppVersionView;
import com.yskj.daishuguan.modle.LoginView;
import com.yskj.daishuguan.response.AppVersionResponse;
import com.yskj.daishuguan.response.BannerResponse;
import com.yskj.daishuguan.response.LoginResponse;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataPresenter
 * @Description:
 */

public class UpAppVersionPresenter extends BasePresenter<AppVersionView> {
    public UpAppVersionPresenter(AppVersionView view) {
        super(view);
    }
}
