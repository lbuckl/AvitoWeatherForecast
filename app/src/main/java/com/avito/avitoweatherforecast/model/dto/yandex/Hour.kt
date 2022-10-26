package com.avito.avitoweatherforecast.model.dto.yandex


import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("cloudness")
    val cloudness: Double,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("feels_like")
    val feelsLike: Int,
    @SerializedName("hour")
    val hour: String,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("prec_mm")
    val precMm: Double,
    @SerializedName("pressure_mm")
    val pressureMm: Double,
    @SerializedName("temp")
    val temp: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double
)