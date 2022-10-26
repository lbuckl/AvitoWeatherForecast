package com.avito.avitoweatherforecast.model.yandexdto


import com.google.gson.annotations.SerializedName

data class GeoObject(
    @SerializedName("country")
    val country: Country,
    @SerializedName("locality")
    val locality: Locality,
)