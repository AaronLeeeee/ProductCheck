package com.check.gf.gfapplication.network;


import com.check.gf.gfapplication.BuildConfig;
import com.check.gf.gfapplication.config.StaticConfig;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit和OkHttpClient实例化
 *
 * @author nEdAy
 */
class RxService<T> {

    private HttpLoggingInterceptor httpLoggingInterceptor = createHttpLoggingInterceptor();
    private T mService;

    RxService(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticConfig.SERVER_HOST_URL)
                .client(genericClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        mService = retrofit.create(clazz);
    }

    T getService() {
        return mService;
    }


    private static HttpLoggingInterceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    private OkHttpClient genericClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.LOG_DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder
//                .addInterceptor(provideOfflineCacheInterceptor())
//                .addNetworkInterceptor(provideCacheInterceptor())
//                .cache(provideCache())
                .retryOnConnectionFailure(true)
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
//                            .addHeader("Content-Type", "application/json")
//                            .addHeader("Pragma", "no-cache")
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

//    /**
//     * 未联网获取缓存数据
//     */
//    private static Interceptor provideOfflineCacheInterceptor() {
//        return chain -> {
//            Request request = chain.request();
//            if (!CommonUtils.isNetworkAvailable()) {
//                //在12小时内缓存有效，此处测试用，实际根据需求设置具体缓存有效时间
//                CacheControl cacheControl = new CacheControl.Builder()
//                        .maxStale(12, TimeUnit.HOURS)
//                        .build();
//                request = request.newBuilder()
//                        .cacheControl(cacheControl)
//                        .build();
//            }
//            return chain.proceed(request);
//        };
//    }
//
//    private static Interceptor provideCacheInterceptor() {
//        return chain -> {
//            Response response = chain.proceed(chain.request());
//            // re-write response header to force use of cache
//            // 正常访问同一请求接口（多次访问同一接口），给10秒缓存，超过时间重新发送请求，否则取缓存数据
//            CacheControl cacheControl = new CacheControl.Builder()
//                    .maxAge(10, TimeUnit.SECONDS)
//                    .build();
//            return response.newBuilder().removeHeader("Pragma")
//                    .header("Cache-Control", cacheControl.toString())
//                    .build();
//        };
//    }
//
//    /**
//     * 设置缓存目录和缓存空间大小
//     *
//     * @return 缓存
//     */
//    private static Cache provideCache() {
//        Cache cache = null;
//        try {
//            cache = new Cache(new File(CustomApplication.getInstance().getCacheDir(), "http-cache"),
//                    50 * 1024 * 1024); // 50 MB
//        } catch (Exception e) {
//            Log.e("cache", "Could not create Cache!");
//        }
//        return cache;
//    }
}
