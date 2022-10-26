package com.avito.avitoweatherforecast.model.openweatherdto

import com.google.gson.annotations.SerializedName

data class WeatherFC(
    @SerializedName("dt_txt")
    val dtTxt: String, // Дата прогноза
    @SerializedName("main")
    val main: Main, // Основная погода
    @SerializedName("pop")
    val pop: Double, // вероятность осадков
    //@SerializedName("rain")
    //val rain: Rain,
    @SerializedName("sys")
    val sys: Sys, //время дня n - night, d - day
    //@SerializedName("visibility")
    //val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)
