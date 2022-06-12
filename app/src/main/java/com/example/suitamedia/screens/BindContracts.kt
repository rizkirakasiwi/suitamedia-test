package com.example.suitamedia.screens

import com.example.suitamedia.screens.screenOne.ScreenOneContract
import com.example.suitamedia.screens.screenOne.ScreenOneContractImpl
import com.example.suitamedia.screens.screenTwo.ScreenTwoContract
import com.example.suitamedia.screens.screenTwo.ScreenTwoContractImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindContracts {
    @Binds
    @Singleton
    abstract fun screenOneContract(screenOneContractImpl: ScreenOneContractImpl): ScreenOneContract

    @Binds
    @Singleton
    abstract fun screenTwoContract(screenTwoContractImpl: ScreenTwoContractImpl):ScreenTwoContract

}