package com.example.suitamedia.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.suitamedia.model.dao.MyDataDao
import com.example.suitamedia.model.entity.MyData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [MyData::class], version = 1)
abstract class Databases : RoomDatabase(){
    abstract fun mydataDao(): MyDataDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    @Singleton
    fun db(@ApplicationContext context:Context):Databases =
        Room.databaseBuilder(
            context,
            Databases::class.java,
            "my_database"
        ).build()
}