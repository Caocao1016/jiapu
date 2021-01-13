package com.demo.jiapu.api;


import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.response.JpsjListResponse;
import com.demo.jiapu.response.ZdppResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiService {

    @GET("Jpsj_List/selList")
    Observable<BaseResponse<JpsjListResponse>> selList(@QueryMap Map<String, Object> params);

    @GET("Jpsj_List/selMylist")
    Observable<BaseResponse<JpsjListResponse>> selMylist(@QueryMap Map<String, Object> params);

    @POST("Jpsj_List/addJpsj")
    Observable<BaseResponse> addJpsj(@QueryMap Map<String, Object> params);
    @POST("Jpsj_List/editMsg")
    Observable<BaseResponse<String>> editMsg(@QueryMap Map<String, Object> params);

    @GET("Gr_Jp/selGrjp2")
    Observable<BaseResponse<List<FamilyBean>>> selGrjp(@QueryMap Map<String, Object> params);

    @GET("sj_jp/selSjjp")
    Observable<BaseResponse<List<FamilyBean>>> selSjjp(@QueryMap Map<String, Object> params);

    @GET("Jpsj_List/zdPp")
    Observable<BaseResponse<ZdppResponse>> zdpp(@QueryMap Map<String, Object> params);

    @POST("Gr_Jp/addGrjp")
    Observable<BaseResponse> addGrjp(@QueryMap Map<String, Object> params);


    @POST("Gr_Jp/editGrjp")
    Observable<BaseResponse> editGrjp(@QueryMap Map<String, Object> params);


    @POST("Report/addReport1")
    Observable<BaseResponse<String>> addReport1(@QueryMap Map<String, Object> params);

    @POST("Jpsj_List/editZhiding")
    Observable<BaseResponse<String>> editZhiding(@QueryMap Map<String, Object> params);


    @Multipart
    @POST("upload/index")
    Observable<BaseResponse<String>> sendMsgfile(@Part MultipartBody.Part file);


}

