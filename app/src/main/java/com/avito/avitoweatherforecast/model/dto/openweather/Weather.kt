package com.avito.avitoweatherforecast.model.dto.openweather


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("description")
    val description: String, // погодные условия
    @SerializedName("icon")
    val icon: String, // ссылка на иконку
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String // Группа погодных параметров (Дождь, Снег, Экстрим
)