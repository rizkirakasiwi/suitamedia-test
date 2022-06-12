package com.example.suitamedia.model.network

import com.example.suitamedia.model.entity.GuestData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GuestRemote {
    @GET("users")
    suspend fun getGuest(@Query("page") page:Int, @Query("per_page")per_page:Int):Response<GuestData>
}