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
    val buttonText: TextStyle,
    val placeholderText: TextStyle
)

@Composable
fun ProvideDnsTypography(): DnsTypography {

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
        buttonText = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        placeholderText = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
    )
}

val LocalDnsTypography = staticCompositionLocalOf<DnsTypography> {
    error("LocalDnsTypography not initialized")
}