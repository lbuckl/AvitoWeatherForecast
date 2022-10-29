package com.avito.avitoweatherforecast.model.dto.yandex
import com.google.gson.annotations.SerializedName
/**
 * Дата класс для парсинга информации из API Яндекс погоды
 * Структура:
 * Fact
 * |
 * Forecast___date (дата прогноза)
 * |        |_Part (часть дня)
 * |             |_Day (погодные данные День)
 * |             |_Evening (погодные данные Вечер)
 * |             |_Night (погодные данные Ночь)
 * |             |_Morning (погодные данные Утро)
 * |
 * GeoObject___country (страна)
 * |         |_location (локация)
 * |
 * nowDt
 */
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