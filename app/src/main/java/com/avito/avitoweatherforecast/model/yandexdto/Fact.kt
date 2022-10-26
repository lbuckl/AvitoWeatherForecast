package com.avito.avitoweatherforecast.model.yandexdto


import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("temp") //температура
    val temp: Int,
    @SerializedName("feels_like") // ощущается как
    val feelsLike: Int,
    @SerializedName("icon") //ссылка на иконку
    val icon: String,
    @SerializedName("cloudness") // облачность
    val cloudness: Int,
    @SerializedName("condition") //описание погоды
    val condition: String,
    @SerializedName("daytime") // «d» — светлое время суток. «n» — темное время суток.
    val daytime: String,
    @SerializedName("humidity") // Влажность воздуха (в процентах).
    val humidity: Int,
    @SerializedName("prec_prob") // Вероятность выпадения осадков
    val precProb: Int,
    @SerializedName("pressure_mm") // Атмосферное давление в мм.
    val pressureMm: Double,
    @SerializedName("season") // Время года в данном населенном пункте
    val season: String,
    @SerializedName("wind_dir") // Направление ветра
    val windDir: String,
    @SerializedName("wind_speed") // скорость ветра
    val windSpeed: Double
)

/**
 * condition
clear — ясно.
partly-cloudy — малооблачно.
cloudy — облачно с прояснениями.
overcast — пасмурно.
drizzle — морось.
light-rain — небольшой дождь.
rain — дождь.
moderate-rain — умеренно сильный дождь.
heavy-rain — сильный дождь.
continuous-heavy-rain — длительный сильный дождь.
showers — ливень.
wet-snow — дождь со снегом.
light-snow — небольшой снег.
snow — снег.
snow-showers — снегопад.
hail — град.
thunderstorm — гроза.
thunderstorm-with-rain — дождь с грозой.
thunderstorm-with-hail — гроза с градом.
 */


/**
 * cloudness
0 — ясно.
0.25 — малооблачно.
0.5 — облачно с прояснениями.
0.75 — облачно с прояснениями.
1 — пасмурно.
 */