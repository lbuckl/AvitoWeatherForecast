package com.avito.avitoweatherforecast.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherViewModel(
    private val liveData: MutableLiveData<WeatherAppState> = MutableLiveData<WeatherAppState>()) :
    ViewModel() {

    val getLiveData = {
        liveData
    }

    fun getWeatherFromCityName(cityName: String){

    }



}