package com.zumo.app.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "zumo_prefs",
        Context.MODE_PRIVATE
    )

    fun saveTokenSync(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    fun getTokenSync(): String? {
        return prefs.getString("auth_token", null)
    }

    fun saveUserIdSync(uid: String) {
        prefs.edit().putString("user_uid", uid).apply()
    }

    fun getUserIdSync(): String? {
        return prefs.getString("user_uid", null)
    }

    suspend fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    suspend fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    suspend fun clearToken() {
        prefs.edit().remove("auth_token").apply()
    }

    suspend fun saveUserId(uid: String) {
        prefs.edit().putString("user_uid", uid).apply()
    }

    suspend fun getUserId(): String? {
        return prefs.getString("user_uid", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
