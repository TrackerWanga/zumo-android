package com.zumo.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zumo.app.data.api.AuthApi
import com.zumo.app.data.local.TokenManager
import com.zumo.app.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
            val token = tokenManager.getToken()
            if (token != null) {
                try {
                    val response = authApi.verify("Bearer $token")
                    if (response.valid && response.user != null) {
                        _uiState.value = AuthUiState(
                            isLoggedIn = true,
                            currentUser = response.user
                        )
                    } else {
                        tokenManager.clearToken()
                    }
                } catch (e: Exception) {
                    tokenManager.clearToken()
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = authApi.login(LoginRequest(email, password))
                if (response.token != null && response.user != null) {
                    tokenManager.saveToken(response.token)
                    tokenManager.saveUserId(response.user.uid)
                    _uiState.value = AuthUiState(
                        isLoggedIn = true,
                        currentUser = response.user
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = response.error ?: "Login failed"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Network error"
                )
            }
        }
    }

    fun signup(email: String, username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = authApi.signup(SignupRequest(email, username, password))
                if (response.token != null && response.user != null) {
                    tokenManager.saveToken(response.token)
                    tokenManager.saveUserId(response.user.uid)
                    _uiState.value = AuthUiState(
                        isLoggedIn = true,
                        currentUser = response.user
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = response.error ?: "Signup failed"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Network error"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authApi.logout("Bearer ${tokenManager.getToken()}")
            } catch (_: Exception) {}
            tokenManager.clear()
            _uiState.value = AuthUiState()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
