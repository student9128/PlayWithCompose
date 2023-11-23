package com.kevin.composestudy.http

import com.kevin.playwithcompose.http.SSLSocketFactoryUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit


object HttpUtils{
    private const val BASE_URL = "https://www.wanandroid.com"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(initBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
    private fun initBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(
            SSLSocketFactoryUtils.createSSLSocketFactory(),
            SSLSocketFactoryUtils.createTrustAllManager()
        )
        builder.hostnameVerifier(SSLSocketFactoryUtils.TrustAllHostnameVerifier())
//            builder.hostnameVerifier = SSLSocketFactoryUtils.TrustAllHostnameVerifier()
        builder.proxy(Proxy.NO_PROXY)
//        builder.addInterceptor(object : Interceptor() {
//            @Throws(IOException::class)
//            fun intercept(chain: Chain): Response {
//                val request: Request = chain.request()
//                val oldHttpUrl: HttpUrl = request.url()
//                val requestBuilder: Request.Builder = request.newBuilder()
//                requestBuilder.addHeader("appId", BaseApplication.getContext().getPackageName())
//                requestBuilder.addHeader("osType", "1") //设备类型（1 android 2 ios）
//                requestBuilder.addHeader(
//                    "macId",
//                    AppUtils.getMac(BaseApplication.getContext())
//                ) //设备唯一标识符
//                requestBuilder.addHeader(
//                    "version",
//                    java.lang.String.valueOf(AppUtils.getVersionName(BaseApplication.getContext()))
//                )
//                requestBuilder.addHeader("Content-Type", "application/json")
//                val token: String = SPUtils.getInstance().getString(Constant.KEY_TOKEN, "")
//                LogUtils.dTag("AppRetrofit", "token=$token")
//                requestBuilder.addHeader("token", token)
//                requestBuilder.addHeader(
//                    "market",
//                    ChannelInfoUtils.getChannel(BaseApplication.getContext())
//                )
//                requestBuilder.addHeader(
//                    "MID",
//                    SPUtils.getInstance().getString(Constant.KEY_USERID, "")
//                )
//                requestBuilder.addHeader("deviceId", DeviceUtils.getUniqueDeviceId())
//                requestBuilder.addHeader("isEncrypt", "yes")
//                //                List<String> headerValues = request.headers("url_name");
////                if (headerValues != null && headerValues.size() > 0) {
////                    requestBuilder.removeHeader("url_name");
////                    String headerValue = headerValues.get(0);
////                    HttpUrl newBaseUrl = oldHttpUrl;
////                    HttpUrl newFullUrl = oldHttpUrl.newBuilder()
////                            .scheme(newBaseUrl.scheme())
////                            .host(newBaseUrl.host())
////                            .port(newBaseUrl.port())
////                            .build();
////                    return chain.proceed(requestBuilder.url(newFullUrl).build());
////                } else {
////                    return chain.proceed(request);
////                }
//                return chain.proceed(requestBuilder.build())
//            }
//        })
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(interceptor)
        builder.retryOnConnectionFailure(true)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
        return builder
    }
}