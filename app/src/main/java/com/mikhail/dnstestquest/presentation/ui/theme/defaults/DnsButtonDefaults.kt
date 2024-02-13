package com.mikhail.dnstestquest.presentation.ui.theme.defaults

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme

object DnsButtonDefaults {

    @Composable
    fun buttonDefaults(
        enabledContainerColor: Color = DnsTheme.color.pink,
        disabledContainerColor: Color = DnsTheme.color.lightPink,
        contentColor: Color = Color.White
    ): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = enabledContainerColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = contentColor,
            contentColor = contentColor
        )
    }
}