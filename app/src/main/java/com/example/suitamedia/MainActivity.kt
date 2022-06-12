package com.example.suitamedia

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Surface
import com.example.suitamedia.helper.getWindowDpSize
import com.example.suitamedia.helper.rememberWindowSizeClass
import com.example.suitamedia.navigation.MainNavigation
import com.example.suitamedia.screens.screenOne.ScreenOne
import com.example.suitamedia.ui.theme.SuitaMediaTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuitaMediaTheme {
                val windowSize = rememberWindowSizeClass(getWindowDpSize())
                MainNavigation(windowSize = windowSize)
            }
        }
    }
}

