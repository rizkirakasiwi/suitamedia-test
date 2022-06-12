package com.example.suitamedia.helper

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetricsCalculator

data class WindowSize(
    val widthWindowSizeClass: WindowType,
    val heightWindowSizeClass: WindowType,
    val widthWindowDpSize: Dp,
    val heightWindowDpSize: Dp
)

sealed class WindowType {
    object COMPACT : WindowType()
    object MEDIUM : WindowType()
    object EXPANDED : WindowType()
}


fun rememberWindowSizeClass(windowDpSize: DpSize): WindowSize {
    return WindowSize(
        widthWindowSizeClass = when {
            windowDpSize.width < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
            windowDpSize.width < 600.dp -> WindowType.COMPACT
            windowDpSize.width < 840.dp -> WindowType.MEDIUM
            else -> WindowType.EXPANDED
        },
        heightWindowSizeClass = when {
            windowDpSize.height < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
            windowDpSize.height < 480.dp -> WindowType.COMPACT
            windowDpSize.height < 900.dp -> WindowType.MEDIUM
            else -> WindowType.EXPANDED
        },
        widthWindowDpSize = windowDpSize.width,
        heightWindowDpSize = windowDpSize.height
    )
}


@Composable
fun Activity.getWindowDpSize(): DpSize {
    val configuration = LocalConfiguration.current
    val windowMetrics = remember(configuration) {
        WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(activity = this)
    }
    return with(LocalDensity.current) {
        windowMetrics.bounds.toComposeRect().size.toDpSize()
    }
}