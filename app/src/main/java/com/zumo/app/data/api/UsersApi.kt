package com.zumo.app.data.api

import com.zumo.app.data.model.*
import retrofit2.http.*

interface UsersApi {
    @GET("v1/users/me")
    suspend fun getMe(): UserResponse

    @GET("v1/users/search")
    suspend fun searchUsers(@Query("q") query: String): UserResponse

    @GET("v1/profile/{username}")
    suspend fun getProfile(@Path("username") username: String): ProfileResponse
}
