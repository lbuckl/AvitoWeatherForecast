package com.avito.avitoweatherforecast.model.openweatherdto


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String
)