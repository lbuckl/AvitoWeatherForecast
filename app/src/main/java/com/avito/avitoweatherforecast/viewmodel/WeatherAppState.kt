package com.avito.avitoweatherforecast.viewmodel
import com.avito.avitoweatherforecast.domain.Weather

/**
 * Класс состояний WeatherViewModel
 */
sealed class WeatherAppState {
    object Loading: WeatherAppState() // Загрузка
    data class Success(val weather: Weather) : WeatherAppState() //Действие при удачной загрузке
    data class Error(val error: String) : WeatherAppState() // Действие при ошибке
    object Empty: WeatherAppState() //Отсутствие состояний
}