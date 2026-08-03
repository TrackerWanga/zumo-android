package com.zumo.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: String,
    @SerialName("conversation_id") val conversationId: String,
    @SerialName("sender_id") val senderId: String,
    val text: String? = null,
    val type: String = "text",
    val metadata: String? = "{}",
    @SerialName("created_at") val createdAt: Long,
    val username: String? = null,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null
)

@Serializable
data class SendMessageRequest(
    val text: String,
    val type: String = "text"
)

@Serializable
data class SendMessageResponse(
    val success: Boolean? = null,
    val message: Message? = null,
    val error: String? = null
)

@Serializable
data class MessagesResponse(
    val messages: List<Message>? = null
)

// Entity for Room DB offline storage
@Serializable
data class PendingMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val conversationId: String,
    val text: String,
    val type: String = "text",
    val createdAt: Long = System.currentTimeMillis(),
    val status: String = "pending" // pending, sent, failed
)
