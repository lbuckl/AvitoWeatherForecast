package com.avito.avitoweatherforecast.model.dto.yandex


import com.google.gson.annotations.SerializedName

data class YandexWeatherDTO(
    @SerializedName("fact")
    val fact: Fact, // Данные погоды за сегодня
    @SerializedName("forecasts")
    val forecasts: List<Forecast>, // Данные погоды прогноз
    @SerializedName("geo_object")
    val geoObject: GeoObject, // Данные об объекте
    @SerializedName("now_dt")
    val nowDt: String, // Дата прогноза
)