package com.avito.avitoweatherforecast.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avito.avitoweatherforecast.BuildConfig
import com.avito.avitoweatherforecast.model.request.geocoder.GeocoderRequest
import com.avito.avitoweatherforecast.model.request.retrofit.YandexWeatherRequestImpl
import com.avito.avitoweatherforecast.model.yandexdto.YandexWeatherDTO
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

    fun getWeatherByCity(cityName: String){
        liveData.postValue(WeatherAppState.Loading)
        val city = GeocoderRequest.getCoordinatesFromName(cityName)
        Log.v("@@@","${city.name} ${city.lat} ${city.lon}")

        YandexWeatherRequestImpl.getRetrofitImpl().getWeather(
            BuildConfig.YANDEX_WEATHER_TRIAL_API_KEY, city.lat, city.lon,7,false,"ru_RU").enqueue(object :
            Callback<YandexWeatherDTO>{
            override fun onResponse(call: Call<YandexWeatherDTO>, response: Response<YandexWeatherDTO>) {
                Log.v("okhttp@@@","Success VM")
                if (response.body() != null){
                    liveData.postValue(WeatherAppState.Success(
                        collectWeatherFromRequestData(city,response.body()!!)))
                }
                else liveData.postValue(WeatherAppState.Error("Error"))
            }

            override fun onFailure(call: Call<YandexWeatherDTO>, t: Throwable) {
                Log.v("okhttp@@@","Error VM")
                liveData.postValue(WeatherAppState.Error("Request Error"))
            }
        })
    }
}