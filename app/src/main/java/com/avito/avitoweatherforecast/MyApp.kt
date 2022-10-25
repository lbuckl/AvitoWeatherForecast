package com.avito.avitoweatherforecast

import android.app.Application
import android.util.Log

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        fun getMyApp() = myApp!!
    }
}