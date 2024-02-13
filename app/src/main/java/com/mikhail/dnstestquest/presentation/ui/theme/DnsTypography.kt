package com.mikhail.dnstestquest.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class DnsTypography(
    val largeTitle1: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val title4: TextStyle,
    val text1: TextStyle,
    val caption1: TextStyle,
    val buttonText1: TextStyle,
    val buttonText2: TextStyle,
    val elementText: TextStyle,
    val priceText: TextStyle,
    val placeholderText: TextStyle
)

@Composable
fun ProvideEffectiveTypography(): DnsTypography {

    return DnsTypography(
        largeTitle1 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        title1 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        title2 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        title3 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        title4 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        ),
        text1 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        ),
        caption1 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
        ),
        buttonText1 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        buttonText2 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        elementText = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 9.sp,
        ),
        priceText = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        ),
        placeholderText = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
    )
}

val LocalDnsTypography = staticCompositionLocalOf<DnsTypography> {
    error("LocalEffectiveTypography not initialized")
}