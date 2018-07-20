package com.android.news.network

import com.android.news.model.ArticlesResponse
import com.android.news.model.NewsResponse
import com.android.news.util.Constants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET(Constants.NEWS)
    fun getNews(@Query("language") lang: String): Observable<Response<NewsResponse>>

    @GET(Constants.NEWS_EVERYTHING)
    fun getArticles(@Query("sources") sources: String,
                    @Query("language") lang: String,
                    @Query("page") page: Int): Observable<Response<ArticlesResponse>>

    @GET(Constants.NEWS_EVERYTHING)
    fun searchArticles(@Query("sources") sources: String,
                       @Query("language") lang: String,
                       @Query("page") page: Int,
                       @Query("q") searchText: String): Observable<Response<ArticlesResponse>>
}