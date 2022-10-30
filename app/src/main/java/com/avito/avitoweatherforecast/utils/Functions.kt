package com.avito.avitoweatherforecast.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.avito.avitoweatherforecast.MyApp
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.domain.*
import com.avito.avitoweatherforecast.model.dto.yandex.YandexWeatherDTO

/**
 * Функция отображения сообщения по вью
 */
fun View.toast(message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}

/**
 * Функция отображения сообщения в активити
 */
fun showToast(message: String?) {
    Toast.makeText(MyApp.getMyApp(), message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}

//region преобразование данных с API WeatherDTO в данные погоды Weather
/**
 * Функция преобразования запрошенных из API яндекс погоды данных
 * в данные текущей погоды
 */
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

/**
 * Функция преобразования запрошенных из API яндекс погоды данных
 * в данные погоды за день
 */
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

/**
 * Функция преобразования запрошенных из API яндекс погоды данных
 * в данные погоды за неделю
 */
fun listDTOtoWeatherWeek(weatherDTO: YandexWeatherDTO):List<WeatherFCData>{
    val map = mutableListOf<WeatherFCData>()
    val deteFormat = FormatDate()
    for (index in 0 until weatherDTO.forecasts.size){
        map.add(
            WeatherFCData(
            deteFormat.getCustomDateFormat(weatherDTO.forecasts[index].date),
            listDTOtoWeatherDay(weatherDTO, index)
            )
        )
    }
    return map.toList()
}
//endregion


/**
 * Функция загрузки SVG изображения из сайта ресурсов яндекс погоды
 */
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

/**
 * Функция сопоставляет данные о направления ветра и
 * загружает в ImageView соответствующую иконку
 */
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

//region функция для выдачи разрешения
/**
 * на вход подаются:
 * - имя разрешения permissionName например Manifest.permission.CALL_PHONE
 * - активити и контекст (можно передавать requireActivity(),requireContext() из фрагмента)
 * - уникальный код под которым будет зарегистрировано разрешение
 *
 */
fun getSinglePermission(permissionName: String,
                        activity: Activity,
                        context: Context,
                        requereCode:Int):Boolean {
    val resources = MyApp.getMyResources()
    var textPermission = "\"${resources.getString(R.string.geolocation)}\""


    if (checkSinglePermission(permissionName)) {
        return true
        //Проверяем а не 2 ли это попытка запросить разрешение
    } else if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)){
        AlertDialog.Builder(context)
            .setTitle("${resources.getString(R.string.access_to)} $textPermission")
            .setMessage("${resources.getString(R.string.to_use_the_function)} $textPermission ${resources.getString(R.string.need_give_permission)}")
            .setPositiveButton("${resources.getString(R.string.agree)}") { _, _ ->
                permissionRequest(
                    arrayOf(permissionName),activity,requereCode)
            }
            .setNegativeButton("${resources.getString(R.string.no)}") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
        return checkSinglePermission(permissionName)
    } else {
        permissionRequest(arrayOf(permissionName),activity,requereCode)
        return checkSinglePermission(permissionName)
    }
}

//Функция, которая непосредственно отображает окно с подтверждением
private fun permissionRequest(permissions: Array<String>, activity: Activity, requestCode:Int) {
    ActivityCompat.requestPermissions(activity, permissions, requestCode)
}

/**
 * Функция для проверки "активно или отозвано разрешение (permission)"
 * ___________________________________________________________________________
 * на вход падаётся имя permissionName например Manifest.permission.CALL_PHONE
 * функция возвращает ответ о состоянии permission: true - активно, false - отозвано
 */
fun checkSinglePermission(permissionName: String):Boolean{
    val context = MyApp.getMyApp()
    val permission = ContextCompat.checkSelfPermission(context, permissionName)
    return permission == PackageManager.PERMISSION_GRANTED
}
//endregion
