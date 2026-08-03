package com.zumo.app.data.api

import com.zumo.app.data.model.*
import retrofit2.http.*

interface ConversationsApi {
    @GET("v1/conversations")
    suspend fun getConversations(): ConversationsResponse

    @POST("v1/conversations")
    suspend fun createConversation(@Body request: CreateConversationRequest): CreateConversationResponse

    @GET("v1/conversations/{id}/messages")
    suspend fun getMessages(
        @Path("id") conversationId: String,
        @Query("limit") limit: Int = 50
    ): MessagesResponse

    @POST("v1/conversations/{id}/messages")
    suspend fun sendMessage(
        @Path("id") conversationId: String,
        @Body request: SendMessageRequest
    ): SendMessageResponse
}
