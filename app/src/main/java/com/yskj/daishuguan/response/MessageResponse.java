package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class MessageResponse {


    private String megName;   //app名称
    private String time;  //版本升级提示说明
    private String megTitle;  //版本升级提示说明

    public String getMegName() {
        return megName;
    }

    public void setMegName(String megName) {
        this.megName = megName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMegTitle() {
        return megTitle;
    }

    public void setMegTitle(String megTitle) {
        this.megTitle = megTitle;
    }
}
