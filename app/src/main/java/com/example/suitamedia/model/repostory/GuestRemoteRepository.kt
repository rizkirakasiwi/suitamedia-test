package com.example.suitamedia.model.repostory

import com.example.suitamedia.model.entity.GuestData
import com.example.suitamedia.model.network.GuestRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class GuestRemoteRepository @Inject constructor(
    retrofit: Retrofit
):GuestRemote {
    private val guest = retrofit.create(GuestRemote::class.java)
    override suspend fun getGuest(page: Int, per_page: Int): Response<GuestData> {
        return withContext(Dispatchers.IO){
            guest.getGuest(page, per_page)
        }
    }
}