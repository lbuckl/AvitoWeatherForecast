package com.avito.avitoweatherforecast.model.yandexdto


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("date")
    val date: String, //Дата
    @SerializedName("hours")
    val hours: List<Hour>,
    @SerializedName("parts") // Прогнозы по времени суток и 12-часовые прогнозы
    val parts: Parts,
)