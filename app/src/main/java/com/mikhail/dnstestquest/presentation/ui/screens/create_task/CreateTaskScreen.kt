package com.mikhail.dnstestquest.presentation.ui.screens.create_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mikhail.dnstestquest.R
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsCenterAlignedTopBar
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsPlaceholder
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsSingleLineButton
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsTextField

@Composable
fun CreateTaskScreen(
    navController: NavController,
    viewModel: CreateTaskScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiAction.collect {
            when (it) {
                CreateTaskScreenAction.NavToHome -> {
                    navController.popBackStack()
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
            .systemBarsPadding()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DnsCenterAlignedTopBar(
            text = stringResource(R.string.create_task_topbar_title)
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DnsTextField(
                value = uiState.taskTitle,
                onValueChange = viewModel::onTaskTitleChange,
                singleLine = true,
                placeholder = {
                    DnsPlaceholder(
                        text = stringResource(R.string.create_task_title_placeholder)
                    )
                }
            )
            DnsTextField(
                value = uiState.taskDescription,
                onValueChange = viewModel::onTaskDescriptionChange,
                singleLine = true,
                placeholder = {
                    DnsPlaceholder(
                        text = stringResource(R.string.create_task_description_placeholder)
                    )
                }
            )
            DnsSingleLineButton(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.create_task_add_task),
                enabled = !uiState.isError,
                onClick = {
                    viewModel.onAddTask(
                        taskTitle = uiState.taskTitle,
                        taskDescription = uiState.taskDescription
                    )
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}