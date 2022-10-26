package com.avito.avitoweatherforecast.model.weatherdto


import com.google.gson.annotations.SerializedName

data class OpenWeatherDTO(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<WeatherFC>,
)