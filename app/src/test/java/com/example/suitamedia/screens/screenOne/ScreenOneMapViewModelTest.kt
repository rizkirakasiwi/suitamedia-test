package com.example.suitamedia.screens.screenOne

import com.example.suitamedia.helper.Result
import com.example.suitamedia.model.entity.MyData
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class ScreenOneMapViewModelTest{
    lateinit var viewModel: ScreenOneViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    fun before(){
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = ScreenOneViewModel(FakeScreenOneContract())
    }

    @AfterEach
    fun after(){
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun palindromTest(){
        val text = "ada"
        viewModel.checkIsSintancePalindrome(text){isPalindrome->
            assertTrue(isPalindrome)
        }
    }

    @Test
    fun test_insertData() = runTest{
        val dummy = MyData(0, "avatar", "Rizki")
        viewModel.onNext(data = dummy) {
            assertEquals(Result.SUCCESS(dummy), it)
        }
    }
}