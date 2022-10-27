package com.avito.avitoweatherforecast.utils

import android.widget.ImageView
import coil.load
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.domain.City
import com.avito.avitoweatherforecast.domain.Weather
import com.avito.avitoweatherforecast.domain.WeatherData
import com.avito.avitoweatherforecast.model.dto.yandex.YandexWeatherDTO


fun collectWeatherFromRequestData(city: City, weatherDTO: YandexWeatherDTO): Weather {
    return Weather(
        City(
            weatherDTO.geoObject.country.name,
            weatherDTO.geoObject.locality.name,
            city.lat,
            city.lon
        ),
        WeatherData(
            weatherDTO.nowDt,
            weatherDTO.fact.temp,
            weatherDTO.fact.feelsLike,
            weatherDTO.fact.icon,
            weatherDTO.fact.pressureMm,
            weatherDTO.fact.windSpeed,
            weatherDTO.fact.windDir  
        ),
        listDTOtoWeatherDay(weatherDTO),
        listDTOtoWeatherDay(weatherDTO)
    )
}

private fun listDTOtoWeatherDay(weatherDTO: YandexWeatherDTO):List<WeatherData>{
    return weatherDTO.forecasts[0].parts.let {
        listOf(
            WeatherData(
                it.night.daytime,
                it.night.tempAvg,
                it.night.feelsLike,
                it.night.icon,
                it.night.pressureMm,
                it.night.windSpeed,
                it.night.windDir
            ),
            WeatherData(
                it.morning.daytime,
                it.morning.tempAvg,
                it.morning.feelsLike,
                it.morning.icon,
                it.morning.pressureMm,
                it.morning.windSpeed,
                it.morning.windDir
            ),
            WeatherData(
                it.day.daytime,
                it.day.tempAvg,
                it.day.feelsLike,
                it.day.icon,
                it.day.pressureMm,
                it.day.windSpeed,
                it.day.windDir
            ),
            WeatherData(
                it.evening.daytime,
                it.evening.tempAvg,
                it.evening.feelsLike,
                it.evening.icon,
                it.evening.pressureMm,
                it.evening.windSpeed,
                it.evening.windDir
            ),
        )    
        
    }
}


fun ImageView.loadIconFromYandex(link: String?){
    link?.let {load("https://yastatic.net/weather/i/icons/funky/dark/$link.svg")  }
}

fun ImageView.setWindDirection(direction: String){
    when (direction){
        DIRECTION_NORTH -> load(R.drawable.ic_wind_n)
        DIRECTION_SOUTH -> load(R.drawable.ic_wind_s)
        DIRECTION_WEST -> load(R.drawable.ic_wind_w)
        DIRECTION_EAST-> load(R.drawable.ic_wind_e)
        DIRECTION_NORTH_WEST -> load(R.drawable.ic_wind_nw)
        DIRECTION_NORTH_EAST -> load(R.drawable.ic_wind_ne)
        DIRECTION_SOUTH_WEST -> load(R.drawable.ic_wind_sw)
        DIRECTION_SOUTH_EAST -> load(R.drawable.ic_wind_se)
        else -> load(R.drawable.ic_wind_default)
    }
}
