package com.avito.avitoweatherforecast

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avito.avitoweatherforecast.ui.FragmentAppNavigation
import com.avito.avitoweatherforecast.ui.weather.FragmentWeather

class MainActivity : AppCompatActivity() {
    init {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_layout, FragmentAppNavigation())
                .commit()
        }
    }
}