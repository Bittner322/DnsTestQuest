package com.mikhail.dnstestquest.data.repositories

import android.content.SharedPreferences
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import java.time.Instant
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val USER_ID_SHARED_PREF = "userId"

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
                                    title = (it.data?.get("title") as? String).orEmpty(),
                                    description = (it.data?.get("description") as? String).orEmpty(),
                                    status = when (it.data?.get("status") as? String) {
                                        "done" -> TaskStatus.DONE
                                        "in_progress" -> TaskStatus.IN_PROGRESS
                                        else -> TaskStatus.NEW
                                    },
                                    created = (it.data?.get("created") as? Timestamp)
                                        ?: Timestamp(Date.from(Instant.now()))
                                )
                            }
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
                .whereEqualTo("login", login)
                .whereEqualTo("password", password)
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

        taskData["title"] = task.title
        taskData["description"] = task.description
        taskData["status"] = task.status.name
        taskData["create"] = Timestamp(Date.from(Instant.now()))

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