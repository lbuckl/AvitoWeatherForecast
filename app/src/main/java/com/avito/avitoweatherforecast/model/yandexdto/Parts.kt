package com.avito.avitoweatherforecast.model.yandexdto


import com.google.gson.annotations.SerializedName

data class Parts(
    @SerializedName("day")
    val day: Day,
    //@SerializedName("evening")
    //val evening: Evening,
    //@SerializedName("morning")
    //val morning: Morning,
    @SerializedName("night")
    val night: Night
)