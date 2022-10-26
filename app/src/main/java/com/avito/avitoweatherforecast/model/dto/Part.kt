package com.avito.avitoweatherforecast.model.dto

data class Part(
    val part_name: String,// Название времени суток
    val temp_max: Int,// Максимальная температура для времени суток
    val temp_min: Int,// Минимальная температура для времени суток
    val temp_avg: Int,// Средняя температура для времени суток
    val wind_dir: String, // Направление ветра
    val wind_gust: Double, // Порыв ветра
    val wind_speed: Double, // Скорость ветра
    val pressure_mm: Int,// Давление (в мм рт. ст.)
    val pressure_pa: Int,//Давление (в гектопаскалях).
    val humidity: Int, // Влажность воздуха (в процентах)
    val condition: String,// Формт погоды
    val daytime: String, // «d» — светлое время суток. «n» — темное время суток.
    val feels_like: Int, // Ощущается как
    val icon: String, // ссылка на иконку
    val polar: Boolean, // Признак того, что время суток, указанное в поле daytime, является полярным
    val prec_mm: Double, // Прогнозируемое количество осадков (в мм)
    val prec_period: Int, // Прогнозируемый период осадков (в минутах)
    val prec_prob: Int // Вероятность выпадения осадков
)

/**
part_name
Название времени суток. Возможные значения:
night — ночь.
morning — утро.
day — день.
evening — вечер.
 */

/**
wind_dir
Направление ветра. Возможные значения:
«nw» — северо-западное.
«n» — северное.
«ne» — северо-восточное.
«e» — восточное.
«se» — юго-восточное.
«s» — южное.
«sw» — юго-западное.
«w» — западное.
«c» — штиль.
 */

/**
condition
Код расшифровки погодного описания. Возможные значения:
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