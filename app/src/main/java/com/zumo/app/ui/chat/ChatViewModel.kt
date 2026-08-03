package com.zumo.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zumo.app.data.api.ConversationsApi
import com.zumo.app.data.local.TokenManager
import com.zumo.app.data.model.Message
import com.zumo.app.data.model.PendingMessage
import com.zumo.app.data.model.SendMessageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val pendingMessages: List<PendingMessage> = emptyList(),
    val currentUserId: String? = null,
    val error: String? = null
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val conversationsApi: ConversationsApi,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var currentConversationId: String = ""

    fun loadMessages(conversationId: String) {
        currentConversationId = conversationId
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val uid = tokenManager.getUserId()
            try {
                val response = conversationsApi.getMessages(conversationId)
                _uiState.value = ChatUiState(
                    messages = response.messages ?: emptyList(),
                    currentUserId = uid
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message,
                    currentUserId = uid
                )
            }
        }
    }

    fun sendMessage(text: String) {
        val pendingId = java.util.UUID.randomUUID().toString()
        val pending = PendingMessage(
            id = pendingId,
            conversationId = currentConversationId,
            text = text
        )

        _uiState.value = _uiState.value.copy(
            pendingMessages = _uiState.value.pendingMessages + pending
        )

        viewModelScope.launch {
            try {
                val response = conversationsApi.sendMessage(
                    currentConversationId,
                    SendMessageRequest(text = text)
                )
                if (response.success == true && response.message != null) {
                    _uiState.value = _uiState.value.copy(
                        messages = _uiState.value.messages + response.message,
                        pendingMessages = _uiState.value.pendingMessages.filter { it.id != pendingId }
                    )
                } else {
                    // Mark as failed
                    _uiState.value = _uiState.value.copy(
                        pendingMessages = _uiState.value.pendingMessages.map {
                            if (it.id == pendingId) it.copy(status = "failed") else it
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    pendingMessages = _uiState.value.pendingMessages.map {
                        if (it.id == pendingId) it.copy(status = "failed") else it
                    }
                )
            }
        }
    }

    fun retryMessage(pendingId: String) {
        val pending = _uiState.value.pendingMessages.find { it.id == pendingId } ?: return
        _uiState.value = _uiState.value.copy(
            pendingMessages = _uiState.value.pendingMessages.filter { it.id != pendingId }
        )
        sendMessage(pending.text)
    }
}
