package com.zumo.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Conversation(
    val id: String,
    val type: String = "dm",
    val title: String? = null,
    @SerialName("last_msg") val lastMsg: String? = null,
    @SerialName("last_message_at") val lastMessageAt: Long? = null
)

@Serializable
data class CreateConversationRequest(
    val type: String = "dm",
    val members: List<String>
)

@Serializable
data class CreateConversationResponse(
    val success: Boolean? = null,
    val conversation: Conversation? = null,
    @SerialName("already_exists") val alreadyExists: Boolean? = null,
    val error: String? = null
)

@Serializable
data class ConversationsResponse(
    val conversations: List<Conversation>? = null
)
