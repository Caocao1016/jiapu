package com.yskj.daishuguan.response;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class AuthitemInfoResponse {

    private List<ItemlistResponse> itemlist;

    public List<ItemlistResponse> getItemlist() {
        return itemlist;
    }

    public void setItemlist(List<ItemlistResponse> itemlist) {
        this.itemlist = itemlist;
    }
}
