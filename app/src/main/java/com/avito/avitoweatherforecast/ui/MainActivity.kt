package com.avito.avitoweatherforecast.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.ui.greetings.GreetingsActivity
import com.avito.avitoweatherforecast.utils.FIRST_ACTIVE
import com.avito.avitoweatherforecast.utils.INITIALIZATION

/**
 * Основной класс активити приложения
 */
class MainActivity : AppCompatActivity() {
    init {
        //Запрет поворота экрана
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Устанавливаем навигационный тулбар
        setSupportActionBar(findViewById(R.id.toolbar))

        //Проверяем показывали ли мы приветственное окно
        val isFirstActive = getSharedPreferences(INITIALIZATION, Context.MODE_PRIVATE)
        if (isFirstActive.getBoolean(FIRST_ACTIVE, true)) {
            startActivity(Intent(this, GreetingsActivity::class.java))
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_layout, FragmentAppNavigation())
                .commit()
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_layout, FragmentAppNavigation())
                    .commit()
            }
        }
    }
}