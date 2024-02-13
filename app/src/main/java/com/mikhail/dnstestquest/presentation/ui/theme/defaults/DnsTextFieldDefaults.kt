package com.mikhail.dnstestquest.presentation.ui.theme.defaults

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme

object DnsTextFieldDefaults {

    @Composable
    fun textFieldColors(
        placeholderColor: Color = DnsTheme.color.grey,
        textColor: Color = DnsTheme.color.black,
        containerColor: Color = DnsTheme.color.lightGrey,
        indicatorColor: Color = Color.Transparent
    ): TextFieldColors {

        return TextFieldDefaults.colors(
            cursorColor = textColor,
            disabledPlaceholderColor = placeholderColor,
            focusedPlaceholderColor = placeholderColor,
            unfocusedPlaceholderColor = placeholderColor,
            errorPlaceholderColor = placeholderColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedIndicatorColor = indicatorColor,
            unfocusedIndicatorColor = indicatorColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor
        )
    }
}