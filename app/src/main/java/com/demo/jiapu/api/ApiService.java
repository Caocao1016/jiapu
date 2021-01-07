package com.demo.jiapu.api;


import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.bean.MemberBean;
import com.demo.jiapu.response.JpsjListResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @GET("Jpsj_List/selList")
    Observable<BaseResponse<JpsjListResponse>> selList(@QueryMap Map<String, Object> params);

    /**
     * 请求验证码接口
     */
    @POST("Jpsj_List/addJpsj")
    Observable<BaseResponse> addJpsj(@QueryMap Map<String, Object> params);

    @GET("Gr_Jp/selGrjp")
    Observable<BaseResponse<List<MemberBean>>> selGrjp(@QueryMap Map<String, Object> params);

    @POST("Gr_Jp/addGrjp")
    Observable<BaseResponse> addGrjp(@QueryMap Map<String, Object> params);


    @POST("Gr_Jp/editGrjp")
    Observable<BaseResponse> editGrjp(@QueryMap Map<String, Object> params);


    @Multipart
    @POST("upload/index")
    Observable<BaseResponse<String>> sendMsgfile(@Part MultipartBody.Part file);


}

