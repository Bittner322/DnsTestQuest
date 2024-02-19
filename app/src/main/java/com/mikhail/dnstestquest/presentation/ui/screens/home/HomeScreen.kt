package com.mikhail.dnstestquest.presentation.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mikhail.dnstestquest.R
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.presentation.ui.main_activity.nav_graphs.NavRoutes
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsCenterAlignedTopBar
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsSingleLineButton
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsTaskCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onLogout: () -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onScreenComposed()
        viewModel.uiAction.collect {
            when (it) {
                HomeScreenAction.NavToCreateTaskScreen -> {
                    navController.navigate(NavRoutes.create_task)
                }

                HomeScreenAction.NavToLoginScreen -> {
                    onLogout()
                }

                HomeScreenAction.ShowRemovingFailureMessage -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.home_task_removing_failure),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                HomeScreenAction.ShowTaskUpdatingFailureMessage -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.home_task_status_changing_failure),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is HomeScreenAction.NavToDetalizationScreen -> {
                    navController.navigate("${NavRoutes.task}/${it.taskId}")
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
            text = stringResource(R.string.home_topbar_title),
            actions = {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .clickable { viewModel.onAddTaskClick() }
                        .align(Alignment.CenterVertically),
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = null,
                    tint = DnsTheme.color.pink
                )
            }
        )

        val tasks by viewModel.tasksFlow.collectAsState()
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.home_tasks_count_title) + " ${
                        tasks.filter { it.statusState.value == TaskStatus.NEW }.size
                    }",
                    style = DnsTheme.typography.title2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
            items(
                items = tasks,
                key = { item: Task -> item.id }
            ) {task ->
                DnsTaskCard(
                    modifier = Modifier.animateItemPlacement(),
                    task = task,
                    onTaskCardClick = { viewModel.onTaskClick(task) },
                    onTaskStatusChangeClick = {
                        viewModel.onTaskStatusChangeClick(
                            task = task,
                            taskStatus = it
                        )
                    },
                    onTaskRemoveClick = {
                        viewModel.onRemoveTaskClick(task)
                    }
                )
            }
            item {
                DnsSingleLineButton(
                    enabled = true,
                    text = stringResource(R.string.home_logout),
                    onClick = onLogout
                )
            }
        }
    }
}