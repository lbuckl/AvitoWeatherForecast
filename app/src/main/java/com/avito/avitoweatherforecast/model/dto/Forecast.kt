package com.avito.avitoweatherforecast.model.dto

data class Forecast(
    val date: String, //Дата прогноза в формате ГГГГ-ММ-ДД
    val parts: List<Part>,
    val week: Int
)