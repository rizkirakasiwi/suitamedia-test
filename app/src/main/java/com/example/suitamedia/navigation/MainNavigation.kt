package com.example.suitamedia.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.suitamedia.helper.WindowSize
import com.example.suitamedia.screens.screenOne.ScreenOne
import com.example.suitamedia.screens.screenTwo.ScreenTwo
import com.example.suitamedia.screens.event.Event
import com.example.suitamedia.ui.theme.dimen
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    windowSize : WindowSize,
){
    NavHost(navController = navController, startDestination = Destination.SCREEN_TWO){
        composable(Destination.SCREEN_ONE){
            ScreenOne(windowSize = windowSize, navController)
        }
        composable(Destination.SCREEN_TWO){
            ScreenTwo(modifier = Modifier.padding(MaterialTheme.dimen.extraLarge))
        }
    }
}

object Destination{
    const val SCREEN_ONE = "screen_one"
    const val SCREEN_TWO = "screen_two"
}