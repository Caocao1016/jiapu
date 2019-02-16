package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class AppVersionResponse {


    private String appName;   //app名称
    private String versionTips;  //版本升级提示说明
    private String displayVersion;   //显示版本。如：v1.0.0
    private int appVersion;    //app数字版本号
    private String appType;    //app客户端类型:ios,android,wap
    private String forcedUpdate;  //当前版本是否需要强制更新。0：否,1：是
    private String appDownloadUrl;   //app包下载地址
    private String createTime;    //当前版本更新时间

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionTips() {
        return versionTips;
    }

    public void setVersionTips(String versionTips) {
        this.versionTips = versionTips;
    }

    public String getDisplayVersion() {
        return displayVersion;
    }

    public void setDisplayVersion(String displayVersion) {
        this.displayVersion = displayVersion;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getForcedUpdate() {
        return forcedUpdate;
    }

    public void setForcedUpdate(String forcedUpdate) {
        this.forcedUpdate = forcedUpdate;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
