package com.avito.avitoweatherforecast

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avito.avitoweatherforecast.ui.FragmentAppNavigation
import com.avito.avitoweatherforecast.ui.greetings.GreetingsActivity
import com.avito.avitoweatherforecast.ui.weather.FragmentWeather
import com.avito.avitoweatherforecast.utils.FIRST_ACTIVE
import com.avito.avitoweatherforecast.utils.INITIALIZATION
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    init {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Проверяем показывали ли мы приветственное окно
        val isFirstActive = getSharedPreferences(INITIALIZATION, Context.MODE_PRIVATE)
        if (isFirstActive.getBoolean(FIRST_ACTIVE, true)){
            startActivity(Intent(this, GreetingsActivity::class.java))
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_layout, FragmentAppNavigation())
                .commit()
        }
        else{
            if(savedInstanceState==null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_layout, FragmentAppNavigation())
                .commit()
            }
        }


    }
}