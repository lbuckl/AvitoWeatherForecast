package com.avito.avitoweatherforecast.model.dto

data class WeatherDTO(
    val fact: Fact, // Объект фактической информации о погоде
    val forecast: Forecast,//	Объект прогнозной информации о погоде
    val info: Info, // Объект информации о населенном пункте
    val now_dt: String // Время сервера в UTC
)