package com.avito.avitoweatherforecast.ui.weather

import com.avito.avitoweatherforecast.domain.City
import com.avito.avitoweatherforecast.domain.Weather

sealed class WeatherAppState {
    data class Loading(val city: City) : WeatherAppState() // загрузка
    data class Success(val weather: Weather) : WeatherAppState() //Действие при удачной загрузке
    data class Error(val error: Exception) : WeatherAppState() // Действие при ошибке
    object Empty: WeatherAppState()
}