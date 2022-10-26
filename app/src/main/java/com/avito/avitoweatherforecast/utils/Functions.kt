package com.avito.avitoweatherforecast.utils

import com.avito.avitoweatherforecast.domain.City
import com.avito.avitoweatherforecast.domain.Weather
import com.avito.avitoweatherforecast.model.yandexdto.YandexWeatherDTO


fun collectWeatherFromRequestData(city: City, weatherDTO: YandexWeatherDTO):Weather{
    return Weather(
        city,
        weatherDTO.fact.temp,
        weatherDTO.fact.feelsLike,
        weatherDTO.fact.icon)
}
