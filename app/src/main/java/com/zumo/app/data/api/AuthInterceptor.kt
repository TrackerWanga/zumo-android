package com.zumo.app.data.api

import com.zumo.app.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        
        val path = original.url.encodedPath
        if (path.contains("/v1/auth/signup") || 
            path.contains("/v1/auth/login") ||
            path.contains("/v1/auth/forgot-password") ||
            path == "/v1/health") {
            return chain.proceed(original)
        }

        val token = tokenManager.getTokenSync()
        
        return if (token != null) {
            val request = original.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        } else {
            chain.proceed(original)
        }
    }
}
