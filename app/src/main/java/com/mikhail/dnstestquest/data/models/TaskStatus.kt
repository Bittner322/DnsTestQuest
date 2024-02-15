package com.mikhail.dnstestquest.data.models

import com.mikhail.dnstestquest.R

enum class TaskStatus(val value: Int) {
    NEW(R.string.home_task_status_new),
    IN_PROGRESS(R.string.home_task_status_in_progress),
    DONE(R.string.home_task_status_done)
}