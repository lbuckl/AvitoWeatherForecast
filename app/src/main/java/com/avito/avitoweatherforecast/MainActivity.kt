package com.avito.avitoweatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avito.avitoweatherforecast.ui.Fragment_app_navigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.included_navigation, Fragment_app_navigation())
                .commit()
        }
    }
}