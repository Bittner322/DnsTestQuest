package com.mikhail.dnstestquest.presentation.ui.screens.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mikhail.dnstestquest.R
import com.mikhail.dnstestquest.presentation.ui.main_activity.nav_graphs.NavRoutes
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsCenterAlignedTopBar
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsPlaceholder
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsSingleLineButton
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsTextField

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiAction.collect {
            when (it) {
                SignInAction.NavToMainScreen -> {
                    navController.navigate(NavRoutes.home)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = DnsTheme.color.white
            )
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DnsCenterAlignedTopBar(
            text = stringResource(R.string.sign_in_topbar_title)
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DnsTextField(
                value = uiState.login,
                onValueChange = viewModel::onNameChange,
                singleLine = true,
                placeholder = {
                    DnsPlaceholder(
                        text = stringResource(R.string.sign_in_login_placeholder)
                    )
                },
                trailingIcon = {
                    if (uiState.login.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    viewModel.onNameChange(login = "")
                                },
                            painter = painterResource(R.drawable.ic_cross),
                            contentDescription = null
                        )
                    }
                }
            )

            DnsTextField(
                value = uiState.password,
                onValueChange = viewModel::onSurnameChange,
                singleLine = true,
                placeholder = {
                    DnsPlaceholder(
                        text = stringResource(R.string.sign_in_password_placeholder)
                    )
                },
                trailingIcon = {
                    if (uiState.password.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    viewModel.onSurnameChange(password = "")
                                },
                            painter = painterResource(R.drawable.ic_cross),
                            contentDescription = null
                        )
                    }
                }
            )
        }

        DnsSingleLineButton(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(R.string.sign_in_button_title),
            enabled = !uiState.isError,
            onClick = {
                viewModel.onSignInClick(
                    login = uiState.login,
                    password = uiState.password
                )
            }
        )

        Spacer(modifier = Modifier.weight(2f))
    }
}