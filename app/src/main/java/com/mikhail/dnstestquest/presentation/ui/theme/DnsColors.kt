package com.mikhail.dnstestquest.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class DnsColors(
    val white: Color,
    val grey: Color,
    val lightGrey: Color,
    val darkGrey: Color,
    val pink: Color,
    val lightPink: Color,
    val orange: Color,
    val black: Color,
    val lightGreen: Color
)

fun ProvideDnsColors(): DnsColors {
    return DnsColors(
        white = Color(0xFFFFFFFF),
        grey = Color(0xFFA0A1A3),
        lightGrey = Color(0xFFF8F8F8),
        darkGrey = Color(0xFF3E3E3E),
        pink = Color(0xFFD62F89),
        lightPink = Color(0xFFFF8AC9),
        orange = Color(0xFFF9A249),
        black = Color(0xFF000000),
        lightGreen = Color(0xFFD1FFBD)
    )
}

val LocalDnsColors = staticCompositionLocalOf<DnsColors> {
    error { "LocalDnsColors not provided" }
}