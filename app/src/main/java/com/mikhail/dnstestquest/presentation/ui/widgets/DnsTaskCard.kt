package com.mikhail.dnstestquest.presentation.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.mikhail.dnstestquest.R
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme
import com.mikhail.dnstestquest.presentation.ui.theme.extensions.noRippleClickable
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

@Composable
fun DnsTaskCard(
    task: Task,
    onTaskCardClick: () -> Unit,
    onTaskStatusChangeClick: (TaskStatus) -> Unit,
    onTaskRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val status by task.statusState

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DnsTheme.color.white,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = DnsTheme.color.darkGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable { onTaskCardClick() }
    ) {
        DnsTaskCardDropdown(
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.End),
            taskStatus = status,
            onChangeTaskStatusClick = onTaskStatusChangeClick,
            onTaskRemoveClick = onTaskRemoveClick
        )
        Text(
            modifier = Modifier
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
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = task.title,
            style = DnsTheme.typography.largeTitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        val createdAsLong = task.created.seconds
        val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm")
            .format(createdAsLong * 1000)
        Text(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 4.dp,
                    bottom = 8.dp,
                    end = 8.dp
                )
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.home_task_created_at) + " $formattedDate",
            style = DnsTheme.typography.title2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun DnsTaskCardPreview() {
    DnsTheme {
        DnsTaskCard(
            task = Task(
                title = "Убрать комнату",
                description = "Мама попросила убраться перед приходом гостей",
                statusState = remember { mutableStateOf(TaskStatus.NEW) },
                created = Timestamp(Date.from(Instant.now()))
            ),
            onTaskCardClick = { },
            onTaskStatusChangeClick = { },
            onTaskRemoveClick = {  }
        )
    }
}