package com.avito.avitoweatherforecast.utils

import com.avito.avitoweatherforecast.domain.City
import com.avito.avitoweatherforecast.domain.Weather
import com.avito.avitoweatherforecast.model.dto.WeatherDTO

fun collectWeatherFromRequestData(city: City, weatherDTO: WeatherDTO):Weather{
    return Weather(
        city,
        weatherDTO.fact.temp,
        weatherDTO.fact.feels_like,
        weatherDTO.fact.icon)
}
