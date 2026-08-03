package com.zumo.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val uid: String? = null,
    val username: String? = null,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    val tier: String? = null,
    val id: String? = null,
    @SerialName("from_uid") val fromUid: String? = null,
    @SerialName("created_at") val createdAt: Long? = null
)

@Serializable
data class FriendRequest(
    val username: String
)

@Serializable
data class FriendsResponse(
    val friends: List<Friend>? = null
)

@Serializable
data class FriendRequestsResponse(
    val requests: List<Friend>? = null
)
