package com.avito.avitoweatherforecast.model.dto.openweather


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String
)