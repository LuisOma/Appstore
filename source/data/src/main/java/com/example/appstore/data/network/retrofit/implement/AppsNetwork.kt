package com.example.appstore.data.network.retrofit.implement

import com.example.appstore.data.network.retrofit.response.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface AppsNetwork {
    @GET("743051d4-b355-4823-af8c-02609c890f71")
    @Headers("Content-Type: application/json")
    suspend fun getApps(
    ): Response<ResponseData>
}