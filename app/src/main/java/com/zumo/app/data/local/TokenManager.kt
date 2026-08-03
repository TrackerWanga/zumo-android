package com.zumo.app.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "zumo_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

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
