package com.avito.avitoweatherforecast.model.request.retrofit
import com.avito.avitoweatherforecast.model.dto.WeatherDTO
import com.avito.avitoweatherforecast.utils.YANDEX_API_KEY_NAME
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherRequestInterface {
    @GET("/v2/informers")
    fun getWeather(
        @Header(YANDEX_API_KEY_NAME) keyValue: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}
