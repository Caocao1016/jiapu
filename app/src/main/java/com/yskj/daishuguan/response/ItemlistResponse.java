package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: ItemlistResponse
 * @Description:
 */

public class ItemlistResponse {

//     "itemName": "活体识别",
//             "statusDesc": "认证失效",
//             "itemcode": "FACE",
//             "desc": "",
//             "status": 3

    private String  itemName ;
    private String  statusDesc ;
    private String  itemCode ;
    private String  desc ;
    private String  authTime ;
    private int  status ;  //0:未认证;1:认证成功;2:认证中;3:认证失效;4:认证失败;

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
