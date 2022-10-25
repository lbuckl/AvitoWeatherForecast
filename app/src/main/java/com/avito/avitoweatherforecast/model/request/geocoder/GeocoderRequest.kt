package com.avito.avitoweatherforecast.model.request.geocoder
import android.location.Geocoder
import com.avito.avitoweatherforecast.MyApp
import com.avito.avitoweatherforecast.domain.City

object GeocoderRequest {
    fun getCoordinatesFromName(name:String):City{
        val context = MyApp.getMyApp()
        val geocoder = Geocoder(context)
        val result = geocoder.getFromLocationName(name, 1)
        return City(name,result.first().latitude,result.first().longitude)
    }
}