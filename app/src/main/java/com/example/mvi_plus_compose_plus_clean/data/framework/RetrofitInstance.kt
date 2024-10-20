package com.example.mvi_plus_compose_plus_clean.data.framework

import com.example.mvi_plus_compose_plus_clean.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private lateinit var sRetrofit: Retrofit

        lateinit var httpClient: OkHttpClient

        fun getRetrofitInstance(): Retrofit {
            if (!Companion::sRetrofit.isInitialized) {
                sRetrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .client(provideClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return sRetrofit
        }

        private fun provideClient(): OkHttpClient {
            if (!Companion::httpClient.isInitialized) {
                httpClient = OkHttpProvider.provideClient()
            }
            return httpClient
        }
    }
}
