package com.avito.avitoweatherforecast.model.weatherdto


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String
)