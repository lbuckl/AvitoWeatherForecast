package com.avito.avitoweatherforecast

import android.app.Application

/**
 * Основной класс приложения
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        fun getMyApp() = myApp!!
        fun getMyResources() = myApp!!.resources
    }
}