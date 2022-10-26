package com.avito.avitoweatherforecast.model.yandexdto


import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("cloudness")
    val cloudness: Double,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("feels_like")
    val feelsLike: Int,
    @SerializedName("hour")
    val hour: String,
    @SerializedName("hour_ts")
    val hourTs: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("is_thunder")
    val isThunder: Boolean,
    @SerializedName("prec_mm")
    val precMm: Double,
    @SerializedName("prec_period")
    val precPeriod: Int,
    @SerializedName("prec_prob")
    val precProb: Int,
    @SerializedName("prec_strength")
    val precStrength: Double,
    @SerializedName("prec_type")
    val precType: Int,
    @SerializedName("pressure_mm")
    val pressureMm: Int,
    @SerializedName("pressure_pa")
    val pressurePa: Int,
    @SerializedName("soil_moisture")
    val soilMoisture: Double,
    @SerializedName("soil_temp")
    val soilTemp: Int,
    @SerializedName("temp")
    val temp: Int,
    @SerializedName("uv_index")
    val uvIndex: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)