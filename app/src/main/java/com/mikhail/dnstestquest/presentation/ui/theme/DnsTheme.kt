package com.mikhail.dnstestquest.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp

@Composable
fun DnsTheme(
    content: @Composable () -> Unit
) {
    val effectiveTypography = ProvideEffectiveTypography()
    val effectiveColors = ProvideEffectiveColors()

    MaterialTheme {
        CompositionLocalProvider(
            LocalDnsColors provides effectiveColors,
            LocalDnsTypography provides effectiveTypography,
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