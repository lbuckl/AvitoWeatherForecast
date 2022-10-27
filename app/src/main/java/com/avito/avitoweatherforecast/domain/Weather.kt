package com.avito.avitoweatherforecast.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val city: City,
    val data: WeatherData,
    val dataDay: List<WeatherData>,
    val dataWeek: List<WeatherFCData>
): Parcelable

@Parcelize
data class City(
    val country: String,
    val name: String,
    val lat: Double,
    val lon: Double
): Parcelable

@Parcelize
data class WeatherData(
    val part: String,
    val temperature: Int,
    val icon: String?,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: String
): Parcelable

@Parcelize
data class WeatherFCData(
    val date: String,
    val weatherData: List<WeatherData>
): Parcelable