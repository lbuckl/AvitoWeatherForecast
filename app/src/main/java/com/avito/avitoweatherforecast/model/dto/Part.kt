package com.avito.avitoweatherforecast.model.dto

data class Part(
    val condition: String,
    val daytime: String,
    val feels_like: Int,
    val humidity: Int,
    val icon: String,
    val part_name: String,
    val polar: Boolean,
    val prec_mm: Int,
    val prec_period: Int,
    val prec_prob: Int,
    val pressure_mm: Int,
    val pressure_pa: Int,
    val temp_avg: Int,
    val temp_max: Int,
    val temp_min: Int,
    val wind_dir: String,
    val wind_gust: Double,
    val wind_speed: Double
)