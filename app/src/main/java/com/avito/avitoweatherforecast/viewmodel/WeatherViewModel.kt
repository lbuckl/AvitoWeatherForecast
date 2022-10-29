package com.avito.avitoweatherforecast.viewmodel

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avito.avitoweatherforecast.BuildConfig
import com.avito.avitoweatherforecast.MyApp
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.model.dto.yandex.YandexWeatherDTO
import com.avito.avitoweatherforecast.model.request.geoposition.GeocoderRequest
import com.avito.avitoweatherforecast.model.request.retrofit.YandexWeatherRequestImpl
import com.avito.avitoweatherforecast.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Модель для фрагментов прогноза погоды
 */
class WeatherViewModel(
    private val liveData: MutableLiveData<WeatherAppState> = MutableLiveData<WeatherAppState>()
) :ViewModel() {

    val SharedPreference =
        MyApp.getMyApp().baseContext.getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)
    private val resources: Resources = MyApp.getMyResources()

    init {
        //Получение имени любимой локации
        val lastCity =
            SharedPreference.getString(PREF_SETTINGS_FAVORITE_CITY, PREF_SETTINGS_DEFAULT_CITY)
        getWeatherByCity(lastCity!!)
    }

    val getLiveData = {
        liveData
    }

    //Функция получения данных погоды. Тригерит состояния WeatherAppState
    fun getWeatherByCity(cityName: String) {
        liveData.postValue(WeatherAppState.Loading)
        //получаем координаты по наименованию локации
        val city = GeocoderRequest.getCoordinatesFromName(cityName)
        city?.let {
            //Получаем данные и формируем состояния WeatherAppState
            YandexWeatherRequestImpl.getRetrofitImpl().getWeather(
                BuildConfig.YANDEX_WEATHER_TRIAL_API_KEY, it.lat, it.lon, FORECAST_DAYS_NUM, false, "ru_RU")
                .enqueue(object : Callback<YandexWeatherDTO> {
                    //Удачное получение данных
                    override fun onResponse(
                        call: Call<YandexWeatherDTO>,
                        response: Response<YandexWeatherDTO>
                    ) {
                        if (response.body() != null) {
                            liveData.postValue(
                                WeatherAppState.Success(
                                    collectWeatherFromRequestData(it, response.body()!!)
                                )
                            )
                            //Загружаем в память последний город
                            rememberLastCity(response.body()!!.geoObject.locality.name)
                        } else liveData.postValue(WeatherAppState.Error(resources.getString(R.string.error_weather_loading)))
                    }

                    //Ошибка получения данных
                    override fun onFailure(call: Call<YandexWeatherDTO>, t: Throwable) {
                        liveData.postValue(WeatherAppState.Error(resources.getString(R.string.error_weather_loading)))
                    }
                })
        }

    }

    //Функция загрузки в память последнего запрошенного города
    private fun rememberLastCity(cityName: String) {
        val editor = SharedPreference.edit()
        editor.putString(PREF_SETTINGS_LAST_CITY, cityName).apply()
    }
}