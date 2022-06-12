package com.example.suitamedia.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimen (
    val extraSmall: Dp = 4.dp,
    val small:Dp = 8.dp,
    val medium:Dp = 16.dp,
    val large:Dp = 24.dp,
    val extraLarge:Dp = 32.dp
)

val LocalDimen = compositionLocalOf { Dimen()}

val MaterialTheme.dimen: Dimen
    @Composable
    @ReadOnlyComposable
    get() = LocalDimen.current