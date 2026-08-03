package com.zumo.app.data.api

import com.zumo.app.data.model.*
import retrofit2.http.*

interface FriendsApi {
    @GET("v1/friends")
    suspend fun getFriends(): FriendsResponse

    @GET("v1/friends/requests")
    suspend fun getRequests(): FriendRequestsResponse

    @POST("v1/friends/request")
    suspend fun sendRequest(@Body request: FriendRequest): SimpleResponse

    @POST("v1/friends/accept")
    suspend fun acceptRequest(@Body body: Map<String, String>): SimpleResponse

    @POST("v1/friends/block")
    suspend fun blockUser(@Body body: Map<String, String>): SimpleResponse
}
