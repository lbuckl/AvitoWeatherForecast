package com.avito.avitoweatherforecast.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val city: City,
    val data: WeatherData,
    val dataDay: List<WeatherData>,
    val dataWeek: List<WeatherData>
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
    val date: String,
    val temperature: Int,
    val feelsLike: Int,
    val icon: String?,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: String
): Parcelable