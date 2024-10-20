package com.example.mvi_plus_compose_plus_clean.di

import com.example.mvi_plus_compose_plus_clean.MainActivity
import org.koin.core.qualifier.named
import org.koin.dsl.module

//Here is some DI for the activity, lives as long as the activity is alive
val activityModule = module {
    scope<MainActivity> {
        scoped(qualifier = named("hello")) { "Hello" }
        scope(qualifier = named("buy")) { "Buy"  }
    }
}
