package com.example.suitamedia.screens.guest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.suitamedia.helper.Result
import com.example.suitamedia.model.entity.Data
import com.example.suitamedia.model.entity.GuestData
import com.example.suitamedia.model.network.GuestRemote
import javax.inject.Inject

class GuestPagination @Inject constructor (private val guestRemote: GuestRemote):PagingSource<Int,Data>(){
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val page = params.key ?: 1
            val guestData  = guestRemote.getGuest(page = page, per_page = 10)
            if (guestData.code() == 200) {
                LoadResult.Page(
                    data = guestData.body()!!.data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = page.plus(1)
                )
            }else{
                LoadResult.Error(Throwable("Load failed"))
            }
        }catch (e : Throwable){
            LoadResult.Error(e)
        }

    }

}