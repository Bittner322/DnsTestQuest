package com.mikhail.dnstestquest.presentation.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mikhail.dnstestquest.presentation.ui.theme.defaults.DnsTextFieldDefaults

@Composable
fun DnsTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    singleLine: Boolean = false,
    charsLimit: Int = Int.MAX_VALUE,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            if (it.length <= charsLimit) {
                onValueChange(it)
            }
        },
        singleLine = singleLine,
        placeholder = placeholder,
        shape = RoundedCornerShape(8.dp),
        trailingIcon = trailingIcon,
        colors = DnsTextFieldDefaults.textFieldColors(),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation
    )
}