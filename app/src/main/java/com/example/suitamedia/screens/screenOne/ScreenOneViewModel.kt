package com.example.suitamedia.screens.screenOne

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suitamedia.helper.Result
import com.example.suitamedia.model.entity.MyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenOneViewModel @Inject constructor(
    private val contract: ScreenOneContract
):ViewModel() {

    fun checkIsSintancePalindrome(text:String, isPalindrom:(Boolean) -> Unit){
        val result = contract.isSentencePalindrome(text)
        isPalindrom(result)
    }

    fun onNext(data:MyData, onResult:(Result<MyData>)->Unit){
        viewModelScope.launch {
            val result = contract.insertData(data)
            onResult(result)
        }
    }
}