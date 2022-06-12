package com.example.suitamedia.screens.screenOne

import com.example.suitamedia.helper.Result
import com.example.suitamedia.model.entity.MyData
import java.util.*

class FakeScreenOneContract:ScreenOneContract {
    override fun isSentencePalindrome(sintence: String): Boolean {
        return sintence.uppercase(Locale.getDefault()) == sintence.uppercase().reversed()
    }

    override suspend fun insertData(data: MyData): Result<MyData> {
        return Result.SUCCESS(data)
    }
}