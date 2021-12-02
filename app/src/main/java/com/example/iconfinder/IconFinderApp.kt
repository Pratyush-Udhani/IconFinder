package com.example.iconfinder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IconFinderApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: IconFinderApp
        private set
    }
}