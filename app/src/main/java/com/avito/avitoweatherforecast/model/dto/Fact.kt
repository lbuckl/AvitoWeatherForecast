package com.avito.avitoweatherforecast.model.dto

data class Fact(
    val condition: String,
    val daytime: String,
    val feels_like: Int,
    val humidity: Int,
    val icon: String,
    val obs_time: Int,
    val polar: Boolean,
    val pressure_mm: Int,
    val pressure_pa: Int,
    val season: String,
    val temp: Int,
    val wind_dir: String,
    val wind_gust: Double,
    val wind_speed: Double
)