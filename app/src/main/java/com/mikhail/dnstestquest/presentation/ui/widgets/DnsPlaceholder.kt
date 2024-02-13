package com.mikhail.dnstestquest.presentation.ui.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme

@Composable
fun DnsPlaceholder(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = DnsTheme.color.grey,
        style = DnsTheme.typography.placeholderText,
        overflow = TextOverflow.Ellipsis
    )
}