package com.yskj.daishuguan.api;


import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.modle.CardView;
import com.yskj.daishuguan.response.AppVersionResponse;
import com.yskj.daishuguan.response.AuthitemInfoResponse;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;
import com.yskj.daishuguan.response.BannerResponse;
import com.yskj.daishuguan.response.BillResponse;
import com.yskj.daishuguan.response.BlacklistResponse;
import com.yskj.daishuguan.response.CaptchaCodeResponse;
import com.yskj.daishuguan.response.CardResponse;
import com.yskj.daishuguan.response.CardSmsResponse;
import com.yskj.daishuguan.response.CertificationResponse;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;
import com.yskj.daishuguan.response.LoginResponse;
import com.yskj.daishuguan.response.ManagementListResponse;
import com.yskj.daishuguan.response.ManagementResponse;
import com.yskj.daishuguan.response.OCRResponse;
import com.yskj.daishuguan.response.RegisterResponse;
import com.yskj.daishuguan.response.ShareContentResponse;
import com.yskj.daishuguan.response.ShareListResponse;
import com.yskj.daishuguan.response.ShareResponse;
import com.yskj.daishuguan.response.UserInfoResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author ChayChan
 * @description: 网络请求的service
 * @date 2017/6/18  19:28
 */

public interface ApiService {


    /**
     * 用户登陆
     */
    @POST("user/signin")
    Observable<BaseResponse<LoginResponse>> login(@QueryMap Map<String, Object> params);

    /**
     * 获取当前用户的基本信息
     */
    @POST("user/userinfo")
    Observable<BaseResponse<UserInfoResponse>> userInfo(@QueryMap Map<String, Object> params);

    /**
     * 请求验证码接口
     */
    @GET("common/requestSmsCode")
    Observable<BaseResponse> getLoginCode(@QueryMap Map<String, Object> params);

    /**
     * 图形验证码
     */
    @GET("common/captchaCode")
    Observable<BaseResponse<CaptchaCodeResponse>> captchaCode(@QueryMap Map<String, Object> params);


    /**
     * 用户注册
     */
    @POST("user/signup")
    Observable<BaseResponse<RegisterResponse>> register(@QueryMap Map<String, Object> params);


    /**
     * banner图
     */
    @POST("common/loan/banner")
    Observable<BaseResponse<List<BannerResponse>>> banner(@QueryMap Map<String, Object> params);


    /**
     * 通用数据接口
     */
    @GET("common/myCommonData")
    Observable<BaseResponse<CommonDataResponse>> geMyCommonData(@QueryMap Map<String, Object> params);


    /**
     * 授信 认证
     */
    @GET("common/loan/rate")
    Observable<BaseResponse<HomeInfoResponse>> homeInfo(@QueryMap Map<String, Object> params);

    /**
     * 获取当前用户的认证信息
     */
    @POST("auth/myauth/authiteminfo")
    Observable<BaseResponse<CertificationResponse>> authiteminfo(@QueryMap Map<String, Object> params);

    /**
     * 运营商判断呢
     */
    @POST("promotion/operatorChannel")
    Observable<BaseResponse> operatorChannel(@QueryMap Map<String, Object> params);


    /**
     * 身份证识别确认
     */
    @POST("auth/livingbody/confirmIdCardInfo")
    Observable<BaseResponse> confirmIdCardInfo(@QueryMap Map<String, Object> params);

    /**
     * 获取银行名称
     */
    @POST("vyb/auth/realname/qrybaseinfo")
    Observable<BaseResponse<CardResponse>> qrybaseinfo(@QueryMap Map<String, Object> params);


    /**
     * 实名认证
     */
    @POST("vyb/auth/realname/authrealnamerequeset")
    Observable<BaseResponse> authrealnamerequeset(@QueryMap Map<String, Object> params);


    /**
     * 实名认证完获取短信验证码
     */
    @POST("vfy/auth/realname/sendMessage")
    Observable<BaseResponse<CardSmsResponse>> sendMessage(@QueryMap Map<String, Object> params);


    /**
     * 借款
     */
    @POST("loan/order/submit")
    Observable<BaseResponse> submit(@QueryMap Map<String, Object> params);

    /**
     * 优惠劵数量
     */
    @POST("coupon/couponTotal")
    Observable<BaseResponse<ManagementResponse>> couponTotal(@QueryMap Map<String, Object> params);

    /**
     * 优惠劵列表
     */
    @POST("coupon/couponUse")
    Observable<BaseResponse<ManagementListResponse>> couponUse(@QueryMap Map<String, Object> params);

    /**
     * 进入我的邀请码入口
     */
    @GET("invite/inviteHomePage")
    Observable<BaseResponse<ShareResponse>> inviteHomePage(@QueryMap Map<String, Object> params);

    /**
     * 记录
     */
    @GET("invite/inviteRecord")
    Observable<BaseResponse<ShareListResponse>> inviteRecord(@QueryMap Map<String, Object> params);

    /**
     * 分享
     */
    @GET("invite/share")
    Observable<BaseResponse<ShareContentResponse>> share(@QueryMap Map<String, Object> params);


    /**
     * 登出（退出登录）
     */
    @POST("user/logout")
    Observable<BaseResponse> logout(@QueryMap Map<String, Object> params);

    /**
     * 申请中
     */
    @POST("credit/creditList")
    Observable<BaseResponse<BillResponse>> creditList(@QueryMap Map<String, Object> params);


    /**
     * 购买会员费
     */
    @POST("vyp/initiativeRepayment/member")
    Observable<BaseResponse> member(@QueryMap Map<String, Object> params);

//------------------------------------------------

    /**
     * 请求发送短信验证码
     */
    @POST("common/requestSmsCaptcha")
    Observable<BaseResponse> requestSmsCaptcha(@QueryMap Map<String, Object> params);


    /**
     * app版本更新查询
     */
    @POST("app/appVersion")
    Observable<BaseResponse<AppVersionResponse>> appVersion(@QueryMap Map<String, Object> params);


    /**
     * 修改登录密码
     */
    @POST("user/changeLoginPasswd")
    Observable<BaseResponse> changeLoginPasswd(@QueryMap Map<String, Object> params);

    /**
     * 授权准备
     */
    @GET("authorization/readyForAuthorize")
    Observable<BaseResponse> readyForAuthorize(@QueryMap Map<String, Object> params);

    /**
     * 授权
     */
    @POST("authorization/doAuthorize")
    Observable<BaseResponse> doAuthorize(@QueryMap Map<String, Object> params);

    /**
     * 授权
     */
    @GET("authorization/myAuthorizeRecord")
    Observable<BaseResponse<AuthorizeRecordResponse>> myAuthorizeRecord(@QueryMap Map<String, Object> params);


    /**
     * 人脸图片上传
     */
    @POST("auth/livingbody/faceRecognise")
    Observable<BaseResponse> faceRecognise(@QueryMap Map<String, Object> params, @Query("fileFace") String fileFace);


    /**
     * 授权
     */
    @POST("auth/myauth/authitemInfo")
    Observable<BaseResponse<AuthitemInfoResponse>> authitemInfo(@QueryMap Map<String, Object> params);


    /**
     * 个人信用风险报告
     */
    @POST("report/queryBlackListInfo")
    Observable<BaseResponse<BlacklistResponse>> queryBlackListInfo(@QueryMap Map<String, Object> params);


}

