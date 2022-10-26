package com.avito.avitoweatherforecast.utils

import com.avito.avitoweatherforecast.domain.City
import com.avito.avitoweatherforecast.domain.Weather
import com.avito.avitoweatherforecast.model.dto.yandex.YandexWeatherDTO


fun collectWeatherFromRequestData(city: City, weatherDTO: YandexWeatherDTO): Weather {
    return Weather(
        City(
            weatherDTO.geoObject.locality.name,
            city.lat,
            city.lon
        ),
        weatherDTO.fact.temp,
        weatherDTO.fact.feelsLike,
        weatherDTO.fact.icon,
        weatherDTO.fact.pressureMm,
        weatherDTO.fact.windSpeed,
        weatherDTO.fact.windDir
    )
}
