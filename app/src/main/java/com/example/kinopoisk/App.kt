package com.example.kinopoisk

import android.app.Application
import com.example.data.data.AppDataBase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {

    private val scope = CoroutineScope(SupervisorJob())

    val db by lazy { AppDataBase.getDatabase(this, scope) }

    override fun onCreate() {
        super.onCreate()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}