package com.zumo.app.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zumo.app.data.api.AuthApi
import com.zumo.app.data.local.TokenManager
import com.zumo.app.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val currentUser: User? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken()
                if (token != null) {
                    val response = withContext(Dispatchers.IO) {
                        authApi.verify("Bearer $token")
                    }
                    if (response.valid && response.user != null) {
                        _uiState.value = AuthUiState(
                            isLoggedIn = true,
                            currentUser = response.user
                        )
                    } else {
                        tokenManager.clearToken()
                    }
                }
            } catch (e: Exception) {
                Log.e("ZumoAuth", "Session check failed", e)
                tokenManager.clearToken()
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = withContext(Dispatchers.IO) {
                    authApi.login(LoginRequest(email, password))
                }
                Log.d("ZumoAuth", "Login response: token=${response.token != null}, user=${response.user != null}, error=${response.error}")
                
                if (response.token != null) {
                    tokenManager.saveToken(response.token)
                    if (response.user != null) {
                        tokenManager.saveUserId(response.user.uid)
                        _uiState.value = AuthUiState(
                            isLoggedIn = true,
                            currentUser = response.user
                        )
                    } else {
                        _uiState.value = AuthUiState(isLoggedIn = true)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = response.error ?: "Login failed - no token received"
                    )
                }
            } catch (e: Exception) {
                Log.e("ZumoAuth", "Login error", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Network error: ${e.message}"
                )
            }
        }
    }

    fun signup(email: String, username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = withContext(Dispatchers.IO) {
                    authApi.signup(SignupRequest(email, username, password))
                }
                Log.d("ZumoAuth", "Signup response: token=${response.token != null}, error=${response.error}")
                
                if (response.token != null) {
                    tokenManager.saveToken(response.token)
                    if (response.user != null) {
                        tokenManager.saveUserId(response.user.uid)
                        _uiState.value = AuthUiState(
                            isLoggedIn = true,
                            currentUser = response.user
                        )
                    } else {
                        _uiState.value = AuthUiState(isLoggedIn = true)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = response.error ?: "Signup failed"
                    )
                }
            } catch (e: Exception) {
                Log.e("ZumoAuth", "Signup error", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Network error: ${e.message}"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    authApi.logout("Bearer ${tokenManager.getToken()}")
                }
            } catch (_: Exception) {}
            tokenManager.clear()
            _uiState.value = AuthUiState()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
