package com.zumo.app.data.api

import com.zumo.app.data.model.*
import retrofit2.http.*

interface AuthApi {
    @POST("v1/auth/signup")
    suspend fun signup(@Body request: SignupRequest): AuthResponse

    @POST("v1/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("v1/auth/verify")
    suspend fun verify(@Header("Authorization") token: String): VerifyResponse

    @POST("v1/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): SimpleResponse

    @GET("v1/auth/sessions")
    suspend fun sessions(@Header("Authorization") token: String): SessionsResponse

    @POST("v1/auth/sessions/revoke")
    suspend fun revokeSession(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): SimpleResponse

    @POST("v1/auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): SimpleResponse
}
