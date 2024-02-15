package com.mikhail.dnstestquest.presentation.ui.theme.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.runIf(
    condition: Boolean,
    then: @Composable Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        then()
    } else {
        this
    }
}