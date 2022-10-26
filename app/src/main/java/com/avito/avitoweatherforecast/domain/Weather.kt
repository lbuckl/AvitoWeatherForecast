package com.avito.avitoweatherforecast.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val city: City,
    val temperature: Int = 20,
    val feelsLike: Int = 20,
    val icon: String?,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: String
): Parcelable

@Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double
): Parcelable