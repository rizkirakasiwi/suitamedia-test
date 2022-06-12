package com.example.suitamedia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.suitamedia.model.entity.MyData

@Dao
interface MyDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(data:MyData)

    @Query("SELECT * FROM my_data ORDER BY id DESC LIMIT 1")
    suspend fun getData():MyData?
}