package com.mikeni.randomuser

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RandomUserApplication:  MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
    }
}