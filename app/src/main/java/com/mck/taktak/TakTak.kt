package com.mck.taktak

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TakTak : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
