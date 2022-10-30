package com.avito.avitoweatherforecast.model.request.geoposition
import android.content.res.Resources
import android.location.Geocoder
import android.util.Log
import com.avito.avitoweatherforecast.MyApp
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.domain.City
import com.avito.avitoweatherforecast.utils.showToast
import java.io.IOException
import java.util.*

/**
 * Класс рабоот с геолокацией
 * Основная функция getCoordinatesFromName возвращает данные о местоположении
 */
object GeocoderRequest {
    private val resources: Resources = MyApp.getMyResources()
    private val context = MyApp.getMyApp()
    private val geocoder = Geocoder(context, Locale("ru_RU"))
    //Функция получения координат по наименовании локации
    fun getCoordinatesFromName(name:String):City?{
        return try {
            val result = geocoder.getFromLocationName(name, 1)
            City("No_data",name,result.first().latitude,result.first().longitude)
        }catch (e: IOException){
            e.printStackTrace()
            Log.e("Developer_massage_error","${resources.getString(R.string.error_find_geolocation)}")
            showToast("${resources.getString(R.string.error_find_geolocation)}")
            null
        }
    }
}