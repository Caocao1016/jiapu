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
    private String versionContent;  //版本升级提示说明
    private String disVersion;   //显示版本。如：v1.0.0
    private int appVersion;    //app数字版本号
    private String appType;    //app客户端类型:ios,android,wap
    private int appForce;  //当前版本是否需要强制更新。0：否,1：是
    private String appDownloadUrl;   //app包下载地址
    private String createTime;    //当前版本更新时间

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionContent() {
        return versionContent;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent;
    }

    public String getDisVersion() {
        return disVersion;
    }

    public void setDisVersion(String disVersion) {
        this.disVersion = disVersion;
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

    public int getAppForce() {
        return appForce;
    }

    public void setAppForce(int appForce) {
        this.appForce = appForce;
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
