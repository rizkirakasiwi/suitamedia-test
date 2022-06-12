package com.example.suitamedia.screens.screenTwo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suitamedia.helper.Result
import com.example.suitamedia.model.entity.GuestData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenTwoViewModel @Inject constructor(
    private val screenTwoContract: ScreenTwoContract
):ViewModel(){
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _currenScreen = MutableStateFlow<ScreenTwoGroup>(ScreenTwoGroup.Parent)
    val currentScreen = _currenScreen.asStateFlow()

    val guest = screenTwoContract.getGuest(scop = viewModelScope)

    init {
        loadName()
    }

    private fun loadName(){
        viewModelScope.launch {
            when(val result = screenTwoContract.getName()){
                is Result.SUCCESS -> _name.emit(result.data)
                is Result.ERROR -> Log.e("ScreenTwoViewModel", "Failed ${result.message}")
                is Result.EXCEPTION -> Log.e("ScreenTwoViewModel", "exception ${result.error.message}")
            }
        }
    }

    fun changeCurrentScreent(screen:ScreenTwoGroup){
        viewModelScope.launch {
            _currenScreen.emit(screen)
        }
    }
}