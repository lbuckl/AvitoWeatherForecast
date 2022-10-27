package com.avito.avitoweatherforecast.utils

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.domain.City
import com.avito.avitoweatherforecast.domain.Weather
import com.avito.avitoweatherforecast.domain.WeatherData
import com.avito.avitoweatherforecast.domain.WeatherFCData
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
            weatherDTO.fact.icon,
            weatherDTO.fact.pressureMm,
            weatherDTO.fact.windSpeed,
            weatherDTO.fact.windDir  
        ),
        listDTOtoWeatherDay(weatherDTO, 0),
        listDTOtoWeatherWeek(weatherDTO)
    )
}

private fun listDTOtoWeatherDay(weatherDTO: YandexWeatherDTO, day:Int):List<WeatherData>{
    return weatherDTO.forecasts[day].parts.let {
        listOf(
            WeatherData(
                it.night.daytime,
                it.night.tempAvg,
                it.night.icon,
                it.night.pressureMm,
                it.night.windSpeed,
                it.night.windDir
            ),
            WeatherData(
                it.morning.daytime,
                it.morning.tempAvg,
                it.morning.icon,
                it.morning.pressureMm,
                it.morning.windSpeed,
                it.morning.windDir
            ),
            WeatherData(
                it.day.daytime,
                it.day.tempAvg,
                it.day.icon,
                it.day.pressureMm,
                it.day.windSpeed,
                it.day.windDir
            ),
            WeatherData(
                it.evening.daytime,
                it.evening.tempAvg,
                it.evening.icon,
                it.evening.pressureMm,
                it.evening.windSpeed,
                it.evening.windDir
            ),
        )    
        
    }
}

fun listDTOtoWeatherWeek(weatherDTO: YandexWeatherDTO):List<WeatherFCData>{

    val map = mutableListOf<WeatherFCData>()
    for (index in 0 until weatherDTO.forecasts.size){
        map.add(
            WeatherFCData(
            weatherDTO.forecasts[index].date,
            listDTOtoWeatherDay(weatherDTO, index)
            )
        )
    }
    return map.toList()
}


fun ImageView.loadIconFromYandex(link: String?){

    //инцииализирем загрузчик
    val imageLoader = context?.let {
        ImageLoader.Builder(it)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    //формируем запрос
    val request = context?.let {
        ImageRequest.Builder(it)
            .data("https://yastatic.net/weather/i/icons/funky/dark/$link.svg")
            .crossfade(true)
            .target(this)
            .build()
    }
    imageLoader?.enqueue(request!!)
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
