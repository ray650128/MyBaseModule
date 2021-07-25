package com.ray650128.mybasemoduledemo.retrofit

import com.ray650128.mybasemodule.retrofit.NullOnEmptyConverterFactory
import com.ray650128.mybasemoduledemo.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private val retrofitClient: Retrofit.Builder by lazy {
        val levelType = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val header = Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(header)
        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com")
            .client(okhttpClient.build())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

    fun getApi(): ApiService = retrofitClient.build().create(ApiService::class.java)
}