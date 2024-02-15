package com.mikhail.dnstestquest.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreRepository @Inject constructor(
    private val remoteDB: FirebaseFirestore,
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
                                    title = (it.data?.get("title") as? String).orEmpty(),
                                    description = (it.data?.get("description") as? String).orEmpty(),
                                    status = when (it.data?.get("status") as? String) {
                                        "done" -> TaskStatus.DONE
                                        "in_progress" -> TaskStatus.IN_PROGRESS
                                        else -> TaskStatus.NEW
                                    },
                                    created = (it.data?.get("created") as? LocalDateTime)
                                        ?: LocalDateTime.now()
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
                            1 -> LoginResult.StatusSuccess(result.first().id)
                            else -> LoginResult.StatusException
                        }
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}

sealed class LoginResult {
    data class StatusSuccess(val userId: String): LoginResult()
    data object StatusFailure: LoginResult()
    data object StatusException: LoginResult()
}