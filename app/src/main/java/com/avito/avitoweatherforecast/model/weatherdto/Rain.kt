package com.avito.avitoweatherforecast.model.weatherdto


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)