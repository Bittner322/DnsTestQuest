package com.mikhail.dnstestquest.presentation.ui.screens.task_detalisation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mikhail.dnstestquest.R
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme
import com.mikhail.dnstestquest.presentation.ui.widgets.DnsCenterAlignedTopBar
import java.text.SimpleDateFormat

@Composable
fun TaskDetalizationScreen(
    navController: NavController,
    viewModel: TaskDetalizationScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiActions.collect {
            when (it) {
                TaskDetalizationScreenActions.NavToHomeScreen -> {
                    navController.popBackStack()
                }
                TaskDetalizationScreenActions.ShowGettingTaskFailureMessage -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.detalization_getting_task_failure),
                        Toast.LENGTH_SHORT
                    ).show()
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
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { viewModel.onBackClick() },
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            },
            text = stringResource(R.string.detalization_topbar_title)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val task by viewModel.taskFlow.collectAsState()
            val status by task.statusState

            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(
                        color = when (status) {
                            TaskStatus.NEW -> DnsTheme.color.lightGreen
                            TaskStatus.IN_PROGRESS -> DnsTheme.color.orange
                            TaskStatus.DONE -> DnsTheme.color.lightPink
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(status.value),
                style = DnsTheme.typography.title1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = task.title,
                style = DnsTheme.typography.largeTitle1,
                color = DnsTheme.color.black,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = task.description,
                style = DnsTheme.typography.title1,
                color = DnsTheme.color.black,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

            val createdAsLong = task.created.seconds
            val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm")
                .format(createdAsLong * 1000)

            Text(
                text = formattedDate,
                style = DnsTheme.typography.title1,
                color = DnsTheme.color.black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}