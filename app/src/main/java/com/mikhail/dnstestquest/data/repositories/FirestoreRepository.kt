package com.mikhail.dnstestquest.data.repositories

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.data.repositories.constants.CollectionsNames
import java.time.Instant
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val USER_ID_SHARED_PREF = "userId"

private const val TITLE_FIRESTORE_FIELD = "title"
private const val DESCRIPTION_FIRESOTORE_FIELD = "description"
private const val STATUS_FIRESTORE_FIELD = "status"
private const val CREATED_FIRESTORE_FIELD = "created"
private const val LOGIN_FIRESTORE_FIELD = "login"
private const val PASSWORD_FIRESTORE_FIELD = "password"

private const val TASK_STATUS_DONE = "DONE"
private const val TASK_STATUS_IN_PROGRESS = "IN_PROGRESS"

class FirestoreRepository @Inject constructor(
    private val remoteDB: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun getUserTasks(userId: String): List<Task> {
        return suspendCoroutine { continuation ->
            remoteDB.collection(CollectionsNames.TASKS)
                .document(userId)
                .collection(CollectionsNames.ALL_TASK)
                .get()
                .addOnSuccessListener { result ->
                    continuation.resume(
                        result
                            .documents
                            .map {
                                Task(
                                    id = it.id,
                                    title = (it.data?.get(TITLE_FIRESTORE_FIELD) as? String)
                                        .orEmpty(),
                                    description = (it.data?.get(DESCRIPTION_FIRESOTORE_FIELD) as? String)
                                        .orEmpty(),
                                    statusState = when (it.data?.get(STATUS_FIRESTORE_FIELD) as? String) {
                                        TASK_STATUS_DONE -> mutableStateOf(TaskStatus.DONE)
                                        TASK_STATUS_IN_PROGRESS -> mutableStateOf(TaskStatus.IN_PROGRESS)
                                        else -> mutableStateOf(TaskStatus.NEW)
                                    },
                                    created = (it.data?.get(CREATED_FIRESTORE_FIELD) as? Timestamp)
                                        ?: Timestamp(Date.from(Instant.now()))
                                )
                            }
                            .sortedByDescending { it.created }
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun login(
        login: String,
        password: String,
    ): LoginResult {
        return suspendCoroutine { continuation ->
            remoteDB.collection(CollectionsNames.USERS)
                .whereEqualTo(LOGIN_FIRESTORE_FIELD, login)
                .whereEqualTo(PASSWORD_FIRESTORE_FIELD, password)
                .get()
                .addOnSuccessListener { result ->
                    continuation.resume(
                        when (result.size()) {
                            0 -> LoginResult.StatusFailure
                            1 -> {
                                with(sharedPreferences.edit()) {
                                    putString(USER_ID_SHARED_PREF, result.first().id)
                                    commit()
                                }
                                LoginResult.StatusSuccess
                            }
                            else -> LoginResult.StatusException
                        }
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun addUserTask(
        userId: String,
        task: Task
    ): AddTaskResult {
        val taskData = HashMap<String, Any>()

        taskData[TITLE_FIRESTORE_FIELD] = task.title
        taskData[DESCRIPTION_FIRESOTORE_FIELD] = task.description
        taskData[STATUS_FIRESTORE_FIELD] = task.statusState.value.name
        taskData[CREATED_FIRESTORE_FIELD] = Timestamp(Date.from(Instant.now()))

        return suspendCoroutine { continuation ->
            remoteDB.collection(CollectionsNames.TASKS)
                .document(userId)
                .collection(CollectionsNames.ALL_TASK)
                .add(taskData)
                .addOnSuccessListener {
                    continuation.resume(AddTaskResult.AddSuccess(it.id))
                }
                .addOnFailureListener {
                    continuation.resume(AddTaskResult.AddFailure)
                }
        }
    }

    suspend fun removeUserTask(
        userId: String,
        taskId: String
    ): RemoveTaskResult {
        return suspendCoroutine { contituation ->
            remoteDB.collection(CollectionsNames.TASKS)
                .document(userId)
                .collection(CollectionsNames.ALL_TASK)
                .document(taskId)
                .delete()
                .addOnSuccessListener {
                    contituation.resume(RemoveTaskResult.RemoveSuccess)
                }
                .addOnFailureListener {
                    contituation.resume(RemoveTaskResult.RemoveFailure)
                }
        }
    }

    suspend fun updateUserTaskStatus(
        userId: String,
        taskId: String,
        newStatus: TaskStatus
    ): UpdateTaskResult {
        return suspendCoroutine { continuation ->
            remoteDB.collection(CollectionsNames.TASKS)
                .document(userId)
                .collection(CollectionsNames.ALL_TASK)
                .document(taskId)
                .update(
                    STATUS_FIRESTORE_FIELD,
                    newStatus.name
                )
                .addOnSuccessListener {
                    continuation.resume(UpdateTaskResult.UpdateSuccess)
                }
                .addOnFailureListener {
                    continuation.resume(UpdateTaskResult.UpdateFailure)
                }
        }
    }

    suspend fun getUserTaskById(
        userId: String,
        taskId: String,
    ): Task {
        return suspendCoroutine { continuation ->
            remoteDB.collection(CollectionsNames.TASKS)
                .document(userId)
                .collection(CollectionsNames.ALL_TASK)
                .document(taskId)
                .get()
                .addOnSuccessListener {
                    continuation.resume(
                        Task(
                            id = it.id,
                            title = (it.data?.get(TITLE_FIRESTORE_FIELD) as? String)
                                .orEmpty(),
                            description = (it.data?.get(DESCRIPTION_FIRESOTORE_FIELD) as? String)
                                .orEmpty(),
                            statusState = when (it.data?.get(STATUS_FIRESTORE_FIELD) as? String) {
                                TASK_STATUS_DONE -> mutableStateOf(TaskStatus.DONE)
                                TASK_STATUS_IN_PROGRESS -> mutableStateOf(TaskStatus.IN_PROGRESS)
                                else -> mutableStateOf(TaskStatus.NEW)
                            },
                            created = (it.data?.get(CREATED_FIRESTORE_FIELD) as? Timestamp)
                                ?: Timestamp(Date.from(Instant.now()))
                        )
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}

sealed class LoginResult {
    data object StatusSuccess: LoginResult()
    data object StatusFailure: LoginResult()
    data object StatusException: LoginResult()
}

sealed class AddTaskResult {
    data class AddSuccess(val taskId: String): AddTaskResult()
    data object AddFailure: AddTaskResult()
}

sealed class RemoveTaskResult {
    data object RemoveSuccess: RemoveTaskResult()
    data object RemoveFailure: RemoveTaskResult()
}

sealed class UpdateTaskResult {
    data object UpdateSuccess: UpdateTaskResult()
    data object UpdateFailure: UpdateTaskResult()
}