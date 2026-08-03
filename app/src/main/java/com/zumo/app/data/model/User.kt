package com.zumo.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,
    val username: String,
    @SerialName("display_name") val displayName: String? = null,
    val email: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    val tier: String? = null,
    @SerialName("email_verified") val emailVerified: Int? = null,
    @SerialName("created_at") val createdAt: Long? = null
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class SignupRequest(
    val email: String,
    val username: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val token: String? = null,
    val user: User? = null,
    val success: Boolean? = null,
    val error: String? = null
)

@Serializable
data class VerifyResponse(
    val valid: Boolean,
    val user: User? = null
)

@Serializable
data class UserResponse(
    val user: User? = null,
    val users: List<User>? = null,
    val query: String? = null
)

@Serializable
data class ProfileResponse(
    val profile: FullProfile? = null,
    val success: Boolean? = null,
    val error: String? = null
)

@Serializable
data class FullProfile(
    val uid: String,
    val username: String,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("backdrop_url") val backdropUrl: String? = null,
    val bio: String? = null,
    val phone: String? = null,
    val tier: String? = null,
    @SerialName("email_verified") val emailVerified: Int? = null,
    @SerialName("created_at") val createdAt: Long? = null,
    @SerialName("last_active_at") val lastActiveAt: Long? = null,
    @SerialName("location_country") val locationCountry: String? = null,
    @SerialName("location_city") val locationCity: String? = null,
    @SerialName("website_url") val websiteUrl: String? = null,
    @SerialName("github_username") val githubUsername: String? = null,
    @SerialName("twitter_username") val twitterUsername: String? = null,
    @SerialName("discord_username") val discordUsername: String? = null,
    @SerialName("friend_count") val friendCount: Int? = null,
    @SerialName("profile_url") val profileUrl: String? = null,
    @SerialName("qr_code") val qrCode: String? = null
)

@Serializable
data class SessionInfo(
    val token: String,
    @SerialName("device_name") val deviceName: String? = null,
    val ip: String? = null,
    @SerialName("last_active") val lastActive: Long? = null,
    @SerialName("created_at") val createdAt: Long? = null
)

@Serializable
data class SessionsResponse(
    val sessions: List<SessionInfo>? = null,
    val current: String? = null
)

@Serializable
data class ForgotPasswordRequest(
    val email: String
)

@Serializable
data class SimpleResponse(
    val success: Boolean? = null,
    val message: String? = null,
    val error: String? = null
)
