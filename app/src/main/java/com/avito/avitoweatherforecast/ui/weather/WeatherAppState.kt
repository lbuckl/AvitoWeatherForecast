package com.avito.avitoweatherforecast.ui.weather

import com.avito.avitoweatherforecast.domain.City
import com.avito.avitoweatherforecast.domain.Weather

sealed class WeatherAppState {
    object Loading: WeatherAppState() // загрузка
    data class Success(val weather: Weather) : WeatherAppState() //Действие при удачной загрузке
    data class Error(val error: String) : WeatherAppState() // Действие при ошибке
    object Empty: WeatherAppState()
}