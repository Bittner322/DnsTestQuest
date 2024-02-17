package com.mikhail.dnstestquest.data.repositories

import android.content.SharedPreferences
import javax.inject.Inject

private const val USER_ID_SHARED_PREF = "userId"

class UserRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun setUserId(userId: String) {
        with(sharedPreferences.edit()) {
            putString(USER_ID_SHARED_PREF, userId)
            commit()
        }
    }

    fun getUserId(): String {
        return sharedPreferences.getString(USER_ID_SHARED_PREF, "") ?: ""
    }

    fun isUserLogged(): Boolean {
        return sharedPreferences.contains(USER_ID_SHARED_PREF)
    }

    fun clearSharedPrefs() {
        with(sharedPreferences.edit()) {
            clear()
            commit()
        }
    }
}