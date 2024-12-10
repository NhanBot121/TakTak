package com.mck.taktak

import androidx.camera.core.CameraX
import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TakTak : Application(), CameraXConfig.Provider {
    override fun onCreate() {
        super.onCreate()
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}
