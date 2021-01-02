package com.demo.jiapu.api;


import com.google.gson.GsonBuilder;
import com.socks.library.KLog;
import com.demo.jiapu.base.MyApp;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author ChayChan
 * @date 2017/6/10  10:54
 */

public class ApiRetrofit {

    private static ApiRetrofit mApiRetrofit;
    private final Retrofit mRetrofit;
    private OkHttpClient mClient;
    private ApiService mApiService;


    //缓存配置
//    private Interceptor mCacheInterceptor = chain -> {

//        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
//        cacheBuilder.maxStale(365, TimeUnit.DAYS);
//        CacheControl cacheControl = cacheBuilder.build();
//
//        Request request = chain.request();
//        if (!NetWorkUtils.isNetworkAvailable(MyApp.getContext())) {
//            request = request.newBuilder()
//                    .cacheControl(cacheControl)
//                    .build();
//        }
//        Response originalResponse = chain.proceed(request);
//        if (NetWorkUtils.isNetworkAvailable(MyApp.getContext())) {
//            int maxAge = 0; // read from cache
//            return originalResponse.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public ,max-age=" + maxAge)
//                    .build();
//        } else {
//            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//            return originalResponse.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }
//    };

    /**请求访问quest和response拦截器*/
    private Interceptor mLogInterceptor = chain -> {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        KLog.e("----------Request Start----------------");
        KLog.e("| " + request.toString());
        KLog.json("| Response:" + content);
        KLog.e("----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    };




    public ApiRetrofit() {
        //cache url
        File httpCacheDirectory = new File(MyApp.getContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        mClient = new OkHttpClient.Builder()
                .addInterceptor(mHeaderInterceptor)//添加头部信息拦截器
                .addInterceptor(mLogInterceptor)//添加log拦截器
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .client(mClient)
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }


    /**增加头部信息的拦截器*/
    private Interceptor mHeaderInterceptor = chain -> {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpc3MiLCJhdWQiOiJhdWQiLCJqdGkiOiI5NGNhZTYwMDI1NGFiMTlmNmE1ZjAxMTM4MzZkZmJjMiIsImlhdCI6MTYwOTIxOTAzNSwibmJmIjoxNjA5MjE5MDM1LCJleHAiOjE2ODEyMTkwMzUsInVpZCI6NX0.6ETFpOfArpSSakQRZnJQ9y4o-SXIyaPH8Dgi3gwA7zI");
        return chain.proceed(builder.build());
    };

    public static ApiRetrofit getInstance() {
        if (mApiRetrofit == null) {
            synchronized (Object.class) {
                if (mApiRetrofit == null) {
                    mApiRetrofit = new ApiRetrofit();
                }
            }
        }
        return mApiRetrofit;
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
