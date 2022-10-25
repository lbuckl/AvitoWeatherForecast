package com.avito.avitoweatherforecast.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avito.avitoweatherforecast.model.request.geocoder.GeocoderRequest

class WeatherViewModel(
    private val liveData: MutableLiveData<WeatherAppState> = MutableLiveData<WeatherAppState>()) :
    ViewModel() {

    val getLiveData = {
        liveData
    }

    init {
        getWeatherFromCityName("Ufa")
    }

    private fun getWeatherFromCityName(cityName: String){
        val city = GeocoderRequest.getCoordinatesFromName(cityName)
        Log.v("@@@","${city.name} ${city.lat} ${city.lon}")
    }
}