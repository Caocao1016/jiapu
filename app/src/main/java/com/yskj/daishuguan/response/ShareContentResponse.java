package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2019/2/16
 *
 * @ClassName: ShareContentResponse
 * @Description:
 */

public class ShareContentResponse {


    //标题
    private String tittle;
    //内容
    private String content;
    //活动ID
    private String activityId;
    //图标
    private String icon;
    //链接地址
    private String shareUrl;


    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
