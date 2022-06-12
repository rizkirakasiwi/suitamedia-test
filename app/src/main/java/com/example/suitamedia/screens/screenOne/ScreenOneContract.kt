package com.example.suitamedia.screens.screenOne

import com.example.suitamedia.helper.Result
import com.example.suitamedia.model.dao.MyDataDao
import com.example.suitamedia.model.entity.MyData
import java.util.*
import javax.inject.Inject

interface ScreenOneContract {
    fun isSentencePalindrome(sintence:String):Boolean
    suspend fun insertData(data:MyData):Result<MyData>
}

class ScreenOneContractImpl @Inject constructor(
    private val myDataDao: MyDataDao
):ScreenOneContract{
    override fun isSentencePalindrome(sintence: String): Boolean {
        return sintence.uppercase(Locale.getDefault()) == sintence.uppercase().reversed()
    }

    override suspend fun insertData(data: MyData): Result<MyData> {
        return try {
            myDataDao.insertData(data)
            val select = myDataDao.getData()

            if(select != null){
                Result.SUCCESS(data)
            }else{
                Result.ERROR("Insert data failed")
            }
        }catch (e:Throwable){
            Result.EXCEPTION(e)
        }
    }

}
