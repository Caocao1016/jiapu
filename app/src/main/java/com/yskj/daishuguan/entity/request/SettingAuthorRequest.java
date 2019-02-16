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

public class SettingAuthorRequest extends MapParamsRequest {

    public String userId;   //客户端设备ID
    public String authId;   //客户端设备ID
    public String type;   //客户端设备ID

    @Override
    protected void putParams() {
        params.put("userId", userId);
        params.put("authId", authId);
        if (!StringUtil.isEmpty(type)){
            params.put("type", type);
        }
    }
}
