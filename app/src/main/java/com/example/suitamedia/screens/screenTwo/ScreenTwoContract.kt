package com.example.suitamedia.screens.screenTwo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.suitamedia.helper.Result
import com.example.suitamedia.model.dao.MyDataDao
import com.example.suitamedia.model.entity.Data
import com.example.suitamedia.model.entity.GuestData
import com.example.suitamedia.model.network.GuestRemote
import com.example.suitamedia.screens.guest.GuestPagination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ScreenTwoContract {
    suspend fun getName():Result<String>
    fun getGuest(scop: CoroutineScope): Flow<PagingData<Data>>
}

class ScreenTwoContractImpl @Inject constructor(
    private val dataDao: MyDataDao,
    private val guest: GuestRemote
):ScreenTwoContract{
    override suspend fun getName(): Result<String> {
        return try {
            val result = dataDao.getData()
            if(result == null){
                Result.ERROR("Data empty")
            }else{
                Result.SUCCESS(result.name)
            }
        }catch (e:Throwable){
            Result.EXCEPTION(e)
        }
    }

    override fun getGuest(scop:CoroutineScope): Flow<PagingData<Data>> {
        return Pager(PagingConfig(pageSize = 10)){
            GuestPagination(guest)
        }.flow.cachedIn(scop) //automatically cache in local database
    }

}

