package com.chathil.kotlifin.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val spacingXXS: Dp = 2.dp,
    val spacingXS: Dp = 4.dp,
    val spacingSmall: Dp = 8.dp,
    val spacingMedium: Dp = 16.dp,
    val spacingLarge: Dp = 24.dp,
    val spacingXL: Dp = 32.dp,
    val spacingXXL: Dp = 48.dp,
    val spacingXXXL: Dp = 72.dp,

    val iconSmall: Dp = 18.dp,
    val iconMedium: Dp = 24.dp,
    val iconLarge: Dp = 32.dp,
    val iconXL: Dp = 48.dp,
    val iconXXL: Dp = 64.dp,

    val lineWidth: Dp = 1.dp,
)

internal val LocalDimens = staticCompositionLocalOf<Dimens> {
    error("No Dimensions provided")
}
