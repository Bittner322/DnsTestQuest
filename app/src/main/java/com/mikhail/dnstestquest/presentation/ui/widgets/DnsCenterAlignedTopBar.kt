package com.mikhail.dnstestquest.presentation.ui.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme
import com.mikhail.dnstestquest.presentation.ui.theme.defaults.DnsTopBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DnsCenterAlignedTopBar(
    text: String,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {}
) {
   CenterAlignedTopAppBar(
       modifier = modifier,
       title = {
           Text(
               text = text,
               color = DnsTheme.color.black,
               style = DnsTheme.typography.title1,
               overflow = TextOverflow.Ellipsis
           )
       },
       actions = actions,
       colors = DnsTopBarDefaults.topBarColors()
   )
}