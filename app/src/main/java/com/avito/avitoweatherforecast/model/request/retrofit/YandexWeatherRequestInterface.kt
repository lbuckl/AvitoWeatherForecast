package com.avito.avitoweatherforecast.model.request.retrofit
import com.avito.avitoweatherforecast.model.dto.yandex.YandexWeatherDTO
import com.avito.avitoweatherforecast.utils.YANDEX_API_KEY_NAME
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YandexWeatherRequestInterface {
    @GET("/v2/forecast")
    fun getWeather(
        @Header(YANDEX_API_KEY_NAME) keyValue: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int,
        @Query("hours") hours: Boolean,
        @Query("lang") lang: String,
    ): Call<YandexWeatherDTO>
}
