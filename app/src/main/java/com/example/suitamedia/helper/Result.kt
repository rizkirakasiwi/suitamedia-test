package com.example.suitamedia.helper

sealed class Result<T>{
    class SUCCESS<T>(val data:T):Result<T>()
    class ERROR<T>(val message:String):Result<T>()
    class EXCEPTION<T>(val error: Throwable):Result<T>()
    class Nothing<T>():Result<T>()
}
