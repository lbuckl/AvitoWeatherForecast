package com.avito.avitoweatherforecast.model.dto

data class Forecast(
    val date: String,
    val date_ts: Int,
    val moon_code: Int,
    val moon_text: String,
    val parts: List<Part>,
    val sunrise: String,
    val sunset: String,
    val week: Int
)