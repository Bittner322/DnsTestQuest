package com.mikhail.dnstestquest.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun DnsTheme(
    content: @Composable () -> Unit
) {
    val dnsTypography = ProvideDnsTypography()
    val dnsColors = ProvideDnsColors()

    MaterialTheme {
        CompositionLocalProvider(
            LocalDnsColors provides dnsColors,
            LocalDnsTypography provides dnsTypography,
            content = content
        )
    }
}

object DnsTheme {
    val color: DnsColors
        @Composable
        get() = LocalDnsColors.current
    val typography: DnsTypography
        @Composable
        get() = LocalDnsTypography.current
}