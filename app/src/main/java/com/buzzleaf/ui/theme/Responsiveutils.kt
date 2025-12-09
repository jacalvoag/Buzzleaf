package com.buzzleaf.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ScreenSize(
    val width: Dp,
    val height: Dp
) {
    val isSmallScreen: Boolean
        get() = width < 360.dp || height < 640.dp

    val isMediumScreen: Boolean
        get() = width in 360.dp..400.dp && height in 640.dp..800.dp

    val isLargeScreen: Boolean
        get() = width > 400.dp && height > 800.dp
}

@Composable
fun rememberScreenSize(): ScreenSize {
    val configuration = LocalConfiguration.current
    return ScreenSize(
        width = configuration.screenWidthDp.dp,
        height = configuration.screenHeightDp.dp
    )
}

@Composable
fun responsiveHorizontalPadding(): Dp {
    val screenSize = rememberScreenSize()
    return when {
        screenSize.isSmallScreen -> 16.dp
        screenSize.isMediumScreen -> 24.dp
        else -> 32.dp
    }
}

@Composable
fun responsiveVerticalSpacing(multiplier: Float = 1f): Dp {
    val screenSize = rememberScreenSize()
    val base = when {
        screenSize.isSmallScreen -> 12.dp
        screenSize.isMediumScreen -> 16.dp
        else -> 20.dp
    }
    return base * multiplier
}

@Composable
fun responsiveTitleSize(): TextUnit {
    val screenSize = rememberScreenSize()
    return when {
        screenSize.isSmallScreen -> 20.sp
        screenSize.isMediumScreen -> 22.sp
        else -> 24.sp
    }
}

@Composable
fun responsiveLargeTextSize(): TextUnit {
    val screenSize = rememberScreenSize()
    return when {
        screenSize.isSmallScreen -> 14.sp
        screenSize.isMediumScreen -> 15.sp
        else -> 16.sp
    }
}

@Composable
fun responsiveBodyTextSize(): TextUnit {
    val screenSize = rememberScreenSize()
    return when {
        screenSize.isSmallScreen -> 12.sp
        screenSize.isMediumScreen -> 13.sp
        else -> 14.sp
    }
}

@Composable
fun responsiveButtonHeight(): Dp {
    val screenSize = rememberScreenSize()
    return when {
        screenSize.isSmallScreen -> 48.dp
        screenSize.isMediumScreen -> 52.dp
        else -> 56.dp
    }
}