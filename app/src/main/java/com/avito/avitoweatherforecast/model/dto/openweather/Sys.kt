package com.avito.avitoweatherforecast.model.dto.openweather


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String //время дня n - night, d - day
)