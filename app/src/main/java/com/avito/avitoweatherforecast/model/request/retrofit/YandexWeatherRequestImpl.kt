package com.avito.avitoweatherforecast.model.request.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Объект для запроса данных из API Яндекс погоды
 * основная функция для запроса: getRetrofitImpl()
 */
object YandexWeatherRequestImpl {
    private const val baseUrl = "https://api.weather.yandex.ru/"

    private val podRetrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(createOkHttpClient(PODInterceptor()))
        .build().create(YandexWeatherRequestInterface::class.java)

    //Основная функция запроса данных
    fun getRetrofitImpl(): YandexWeatherRequestInterface {
        return podRetrofit
    }

    //httpClient запроса
    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    //Перехватчик запросов, позволяет обрабатывать результаты запроса
    class PODInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val response = chain.proceed(chain.request())
            when (response.code){
                in 200..299 -> Log.v("Develop_message_okhttp", "Request Success: ${response.code}")
                in 300..399 -> Log.v("Develop_message_okhttp", "Request Success: ${response.code}")
                in 400..499 -> Log.v("Develop_error_okhttp", "Request Error: ${response.code}")
                in 500..599 -> Log.v("Develop_error_okhttp", "Remote server error: ${response.code}")
            }
            return response
        }
    }
}