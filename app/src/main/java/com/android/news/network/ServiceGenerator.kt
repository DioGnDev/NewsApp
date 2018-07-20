package com.android.news.network

import com.android.news.util.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {

    fun createService(): ServiceApi {
        val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            var original: Request = chain.request()
            var requestBuilder = original.newBuilder()
                    .header(Constants.API_KEY, "5471cbaf50044e4da3ea4016983105df")
                    .method(original.method(), original.body())
            var request = requestBuilder.build()
            chain.proceed(request)
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(logging).connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
        val retrofit = builder.client(httpClient.build()).build()
        val apiInterface = retrofit.create(ServiceApi::class.java)
        return apiInterface
    }

}