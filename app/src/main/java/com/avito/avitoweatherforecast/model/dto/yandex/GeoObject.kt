package com.avito.avitoweatherforecast.model.dto.yandex


import com.google.gson.annotations.SerializedName

data class GeoObject(
    @SerializedName("country")
    val country: Country,
    @SerializedName("locality")
    val locality: Locality,
)