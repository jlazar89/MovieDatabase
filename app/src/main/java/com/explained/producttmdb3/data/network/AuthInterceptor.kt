package com.explained.producttmdb3.data.network

import com.explained.producttmdb3.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN_KEY}")
            .build()
        return chain.proceed(request)
    }
}
