package com.mikhail.dnstestquest.presentation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mikhail.dnstestquest.R
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsCenterAlignedTopBar
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsTaskCard
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
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
            text = stringResource(R.string.home_topbar_title),
            actions = {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .align(Alignment.CenterVertically),
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = null,
                    tint = DnsTheme.color.pink
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DnsTaskCard(
                    task = Task(
                        id = "123",
                        title = "Убрать комнату",
                        description = "Мама попросила убраться перед приходом гостей",
                        status = TaskStatus.NEW,
                        created = LocalDateTime.now()
                    ),
                    onTaskCardClick = { /*TODO*/ },
                    onTaskStatusChange = { /*TODO*/ }
                )
            }
            item {
                DnsTaskCard(
                    task = Task(
                        id = "123",
                        title = "Убрать комнату",
                        description = "Мама попросила убраться перед приходом гостей",
                        status = TaskStatus.NEW,
                        created = LocalDateTime.now()
                    ),
                    onTaskCardClick = { /*TODO*/ },
                    onTaskStatusChange = { /*TODO*/ }
                )
            }
            item {
                DnsTaskCard(
                    task = Task(
                        id = "123",
                        title = "Убрать комнату",
                        description = "Мама попросила убраться перед приходом гостей",
                        status = TaskStatus.NEW,
                        created = LocalDateTime.now()
                    ),
                    onTaskCardClick = { /*TODO*/ },
                    onTaskStatusChange = { /*TODO*/ }
                )
            }
            item {
                DnsTaskCard(
                    task = Task(
                        id = "123",
                        title = "Убрать комнату",
                        description = "Мама попросила убраться перед приходом гостей",
                        status = TaskStatus.NEW,
                        created = LocalDateTime.now()
                    ),
                    onTaskCardClick = { /*TODO*/ },
                    onTaskStatusChange = { /*TODO*/ }
                )
            }
            item {
                DnsTaskCard(
                    task = Task(
                        id = "123",
                        title = "Убрать комнату",
                        description = "Мама попросила убраться перед приходом гостей",
                        status = TaskStatus.NEW,
                        created = LocalDateTime.now()
                    ),
                    onTaskCardClick = { /*TODO*/ },
                    onTaskStatusChange = { /*TODO*/ }
                )
            }
        }
    }
}