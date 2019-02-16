package com.yskj.daishuguan.entity.request;

import com.yskj.daishuguan.base.MapParamsRequest;
import com.yskj.daishuguan.util.StringUtil;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataRequest
 * @Description:
 */

public class SettingPwRequest extends MapParamsRequest {

    public String password;
    public String newPassword;
    public String confirmPassword;
    public String userId;

    @Override
    protected void putParams() {
        params.put("password", password);
        params.put("newPassword", newPassword);
        params.put("confirmPassword", confirmPassword);
        params.put("userId", userId);

    }
}
