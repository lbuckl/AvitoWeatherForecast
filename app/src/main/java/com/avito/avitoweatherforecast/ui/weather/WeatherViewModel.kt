package com.avito.avitoweatherforecast.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avito.avitoweatherforecast.BuildConfig
import com.avito.avitoweatherforecast.model.dto.WeatherDTO
import com.avito.avitoweatherforecast.model.request.geocoder.GeocoderRequest
import com.avito.avitoweatherforecast.model.request.retrofit.WeatherRequestImpl
import com.avito.avitoweatherforecast.utils.collectWeatherFromRequestData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(
    private val liveData: MutableLiveData<WeatherAppState> = MutableLiveData<WeatherAppState>()) :
    ViewModel() {

    val getLiveData = {
        liveData
    }

    /*init {
        getWeatherFromCityName("Ufa")
    }*/

    fun getWeatherFromCityName(cityName: String){
        liveData.postValue(WeatherAppState.Loading)
        val city = GeocoderRequest.getCoordinatesFromName(cityName)
        Log.v("@@@","${city.name} ${city.lat} ${city.lon}")

        WeatherRequestImpl.getRetrofitImpl().getWeather(
            BuildConfig.WEATHER_API_KEY, city.lat, city.lon).enqueue(object :
        Callback<WeatherDTO>{
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                Log.v("@@@","Success VM")
                if (response.body() != null){
                    liveData.postValue(WeatherAppState.Success(
                        collectWeatherFromRequestData(city,response.body()!!)))
                }
                else liveData.postValue(WeatherAppState.Error("Error"))
            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                Log.v("@@@","Error VM")
                liveData.postValue(WeatherAppState.Error("Request Error"))
            }
        })
    }
}