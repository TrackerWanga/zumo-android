package com.zumo.app.di

import com.zumo.app.data.api.*
import com.zumo.app.data.local.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(tokenManager: TokenManager): Retrofit {
        return ApiClient.create(tokenManager)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConversationsApi(retrofit: Retrofit): ConversationsApi {
        return retrofit.create(ConversationsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFriendsApi(retrofit: Retrofit): FriendsApi {
        return retrofit.create(FriendsApi::class.java)
    }
}
