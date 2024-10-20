package com.example.mvi_plus_compose_plus_clean.data.framework

import com.example.mvi_plus_compose_plus_clean.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            //.addHeader("x-rapidapi-host", BuildConfig.API_HOST)
            .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
            .build()

        return chain.proceed(request)
    }

}
