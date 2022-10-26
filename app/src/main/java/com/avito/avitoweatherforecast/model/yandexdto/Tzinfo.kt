package com.avito.avitoweatherforecast.model.yandexdto


import com.google.gson.annotations.SerializedName

data class Tzinfo(
    @SerializedName("abbr")
    val abbr: String,
    @SerializedName("dst")
    val dst: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("offset")
    val offset: Int
)