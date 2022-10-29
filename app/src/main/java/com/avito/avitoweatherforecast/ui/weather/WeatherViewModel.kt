package com.avito.avitoweatherforecast.ui.weather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avito.avitoweatherforecast.BuildConfig
import com.avito.avitoweatherforecast.MyApp
import com.avito.avitoweatherforecast.model.request.geocoder.GeocoderRequest
import com.avito.avitoweatherforecast.model.request.retrofit.YandexWeatherRequestImpl
import com.avito.avitoweatherforecast.model.dto.yandex.YandexWeatherDTO
import com.avito.avitoweatherforecast.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(
    private val liveData: MutableLiveData<WeatherAppState> = MutableLiveData<WeatherAppState>()) :
    ViewModel() {

    val sharedPrefer = MyApp.getMyApp().baseContext.getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)

    init {
        val lastCity = sharedPrefer.getString(PREF_SETTINGS_FAVORITE_CITY, PREF_SETTINGS_DEFAULT_CITY)
        getWeatherByCity(lastCity!!)
    }

    val getLiveData = {
        liveData
    }

    fun getWeatherByCity(cityName: String){
        liveData.postValue(WeatherAppState.Loading)
        val city = GeocoderRequest.getCoordinatesFromName(cityName)

        YandexWeatherRequestImpl.getRetrofitImpl().getWeather(
            BuildConfig.YANDEX_WEATHER_TRIAL_API_KEY, city.lat, city.lon,7,false,"ru_RU").enqueue(object :
            Callback<YandexWeatherDTO>{
            override fun onResponse(call: Call<YandexWeatherDTO>, response: Response<YandexWeatherDTO>) {
                if (response.body() != null){
                    liveData.postValue(WeatherAppState.Success(
                        collectWeatherFromRequestData(city,response.body()!!)))
                    //Загружаем в память последний город
                    rememberLastCity(response.body()!!.geoObject.locality.name)
                }
                else liveData.postValue(WeatherAppState.Error("Error"))
            }

            override fun onFailure(call: Call<YandexWeatherDTO>, t: Throwable) {
                liveData.postValue(WeatherAppState.Error("Request Error"))
            }
        })
    }

    private fun rememberLastCity(cityName: String){

        val editor = sharedPrefer.edit()
        editor.putString(PREF_SETTINGS_LAST_CITY, cityName).apply()
    }
}