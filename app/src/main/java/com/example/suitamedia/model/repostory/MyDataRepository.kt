package com.example.suitamedia.model.repostory

import com.example.suitamedia.model.Databases
import com.example.suitamedia.model.dao.MyDataDao
import com.example.suitamedia.model.entity.MyData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject



class MyDataRepository @Inject constructor(db:Databases): MyDataDao {
    private val mydatadao = db.mydataDao()
    override suspend fun insertData(data: MyData) {
        mydatadao.insertData(data)
    }

    override suspend fun getData(): MyData? {
        return withContext(Dispatchers.IO){
            mydatadao.getData()
        }
    }

}