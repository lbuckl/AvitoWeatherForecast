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
 * класс для запроса фото дня из API NASA
 * основная функция для запроса: getRetrofitImpl()
 */
object WeatherRequestImpl {
    private const val baseUrl = "https://api.weather.yandex.ru/"
    private val podRetrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(createOkHttpClient(PODInterceptor()))
        .build().create(WeatherRequestInterface::class.java)

    fun getRetrofitImpl(): WeatherRequestInterface {
        return podRetrofit
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    class PODInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val response = chain.proceed(chain.request())
            when (response.code){
                in 200..299 -> Log.v("@@@", "Request Success: ${response.code}")
                in 300..399 -> Log.v("@@@", "Request Success: ${response.code}")
                in 400..499 -> Log.v("@@@", "Request Error: ${response.code}")
                in 500..599 -> Log.v("@@@", "Remote server error: ${response.code}")
            }
            return response
        }
    }
}