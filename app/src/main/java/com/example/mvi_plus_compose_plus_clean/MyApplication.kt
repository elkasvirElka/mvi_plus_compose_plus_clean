package com.example.mvi_plus_compose_plus_clean

import android.app.Application
import com.example.mvi_plus_compose_plus_clean.di.activityModule
import com.example.mvi_plus_compose_plus_clean.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule, activityModule)
        }
    }
}
