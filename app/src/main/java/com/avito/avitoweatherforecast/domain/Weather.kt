package com.avito.avitoweatherforecast.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Дата класс погоды
 * @param city - данные локации
 * @param data - данные погоды на текущий момент
 * @param dataDay - данные погоды за день
 * @param dataWeek - данные погоды за неделю
 */
@Parcelize
data class Weather(
    val city: City,
    val data: WeatherData,
    val dataDay: List<WeatherData>,
    val dataWeek: List<WeatherFCData>
): Parcelable

/**
 * Дата класс локации
 * @param country - страна
 * @param name - локация
 * @param lat - широта
 * @param lon - долгота
 */
@Parcelize
data class City(
    val country: String,
    val name: String,
    val lat: Double,
    val lon: Double
): Parcelable

/**
 * Дата класс локации
 * @param part - часть дня (день, вечер...)
 * @param temperature - температура
 * @param icon - ссылка на иконку
 * @param pressure - давление
 * @param windSpeed - скорость ветра
 * @param windDirection - направление ветра
 */
@Parcelize
data class WeatherData(
    val part: String,
    val temperature: Int,
    val icon: String?,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: String
): Parcelable

/**
 * Дата класс локации
 * @param date - дата прогноза
 * @param weatherData - массив данных погоды
 */
@Parcelize
data class WeatherFCData(
    val date: String,
    val weatherData: List<WeatherData>
): Parcelable