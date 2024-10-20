package com.example.mvi_plus_compose_plus_clean.data.framework

import com.example.mvi_plus_compose_plus_clean.data.framework.ApiKeyInterceptor
import okhttp3.OkHttpClient

class OkHttpProvider {
    companion object {
        fun provideClient(): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build()
        }
    }
}
