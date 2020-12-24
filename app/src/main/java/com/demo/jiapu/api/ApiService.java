package com.demo.jiapu.api;


import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.response.LoginResponse;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
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
     * 请求验证码接口
     */
    @GET("common/requestSmsCode")
    Observable<BaseResponse> getLoginCode(@QueryMap Map<String, Object> params);

}

