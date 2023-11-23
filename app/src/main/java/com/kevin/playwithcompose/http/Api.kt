package com.kevin.composestudy.http

import com.kevin.composestudy.bean.BannerBean
import com.kevin.composestudy.bean.LoginBean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @POST("/user/login")
    suspend fun requestLogin(@Query("username") username:String, @Query("password") password:String,): LoginBean

    @GET("/banner/json")
    suspend fun requestBanner(): BannerBean
}