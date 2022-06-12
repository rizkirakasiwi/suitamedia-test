package com.example.suitamedia.model.repostory

import com.example.suitamedia.model.dao.MyDataDao
import com.example.suitamedia.model.network.GuestRemote
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepository {
    @Binds
    @Singleton
    abstract fun mydataRepo(myDataRepository: MyDataRepository): MyDataDao

    @Binds
    @Singleton
    abstract fun guestRemote(guestRemoteRepository: GuestRemoteRepository):GuestRemote
}