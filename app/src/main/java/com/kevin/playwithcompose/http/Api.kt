package com.kevin.composestudy.http

import com.kevin.composestudy.bean.BannerBean
import com.kevin.composestudy.bean.LoginBean
import com.kevin.playwithcompose.bean.ArticleListBean
import com.kevin.playwithcompose.bean.NavListBean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @POST("/user/login")
    suspend fun requestLogin(
        @Query("username") username: String,
        @Query("password") password: String,
    ): LoginBean

    @GET("/banner/json")
    suspend fun requestBanner(): BannerBean

    ///获取首页的文章列表
    @GET("/article/list/{pageNum}/json")
    suspend fun requestArticleList(@Path("pageNum") pageNumber: Int): ArticleListBean

    @GET("/navi/json")
    suspend fun requestNav(): NavListBean
}