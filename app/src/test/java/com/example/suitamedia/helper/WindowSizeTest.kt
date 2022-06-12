package com.example.suitamedia.helper

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WindowSizeTest{

    @Test
    fun test_windowSizeClass() {
        val size = arrayOf(
            WindowSizeTestData(DpSize(width = 599.dp, height = 479.dp), WindowType.COMPACT),
            WindowSizeTestData(DpSize(width = 839.dp, height = 899.dp), WindowType.MEDIUM),
            WindowSizeTestData(DpSize(width = 900.dp, height = 900.dp), WindowType.EXPANDED),
        )

        size.forEach {
            val windoSize = rememberWindowSizeClass(it.value)
            assertEquals(it.windowType, windoSize.widthWindowSizeClass)
            assertEquals(it.windowType, windoSize.heightWindowSizeClass)
        }
    }

    @Test
    fun test_windowSizeClassThrowError(){
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            rememberWindowSizeClass(DpSize((-1).dp, (-1).dp))
        }
    }
}