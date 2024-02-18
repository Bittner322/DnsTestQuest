package com.mikhail.dnstestquest.presentation.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mikhail.dnstestquest.R
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme

@Composable
fun DnsTaskCardDropdown(
    taskStatus: TaskStatus,
    onChangeTaskStatusClick: (TaskStatus) -> Unit,
    onTaskRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    end = 8.dp
                )
                .size(32.dp)
                .clip(CircleShape)
                .clickable { expanded = true },
            painter = painterResource(R.drawable.ic_filter),
            contentDescription = null
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                enabled = taskStatus == TaskStatus.NEW,
                text = {
                    Text(
                        text = stringResource(R.string.home_tasks_start_task),
                        style = DnsTheme.typography.title4,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = {
                    onChangeTaskStatusClick(TaskStatus.IN_PROGRESS)
                    expanded = false
                }
            )
            DropdownMenuItem(
                enabled = taskStatus == TaskStatus.IN_PROGRESS,
                text = {
                    Text(
                        text = stringResource(R.string.home_tasks_finish_task),
                        style = DnsTheme.typography.title4,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = {
                    onChangeTaskStatusClick(TaskStatus.DONE)
                    expanded = false
                }
            )
            DropdownMenuItem(
                enabled = taskStatus == TaskStatus.NEW,
                text = {
                    Text(
                        text = stringResource(R.string.home_tasks_remove_task),
                        style = DnsTheme.typography.title4,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = {
                    onTaskRemoveClick()
                    expanded = false
                }
            )
        }
    }
}