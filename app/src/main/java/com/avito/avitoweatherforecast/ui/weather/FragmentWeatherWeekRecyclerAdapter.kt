package com.gb.weather.view.weatherlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avito.avitoweatherforecast.databinding.FragmentWeatherFcWeekItemBinding
import com.avito.avitoweatherforecast.domain.WeatherFCData
import com.avito.avitoweatherforecast.utils.loadIconFromYandex
import com.avito.avitoweatherforecast.utils.setWindDirection

/**
 * Кастомный адаптер для вывода списка элементов меню в recyclerview
 */
class FragmentWeatherWeekRecyclerAdapter (private val weatherListCity:List<WeatherFCData>):
    RecyclerView.Adapter<FragmentWeatherWeekRecyclerAdapter.WeatherWeekViewHolder>() {

    //Создаёт ViewHolder объект опираясь на их количество, но с запасом, чтобы можно было скролить
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherWeekViewHolder {
        val binding = FragmentWeatherFcWeekItemBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherWeekViewHolder(binding.root)
    }

    //Связываем используемые текстовые метки с данными
    override fun onBindViewHolder(holder: WeatherWeekViewHolder, position: Int) {
        holder.bind(weatherListCity[position])
    }

    //Возвращает количество элементов списка
    override fun getItemCount(): Int {
        return weatherListCity.size
    }

    //Класс который непосредственно отображает данные в каждом элементе recyclerview
    inner class WeatherWeekViewHolder(view: View): RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(weatherItem: WeatherFCData){
            FragmentWeatherFcWeekItemBinding.bind(itemView).let {
                with(weatherItem.weatherData[2]){
                    it.imageView.loadIconFromYandex(icon)
                    it.imageView2.setWindDirection(windDirection)
                    val temperatureRange = "${weatherItem.weatherData[0].temperature} : ${weatherItem.weatherData[2].temperature}"
                    it.textView.text = temperatureRange
                    it.textView3.text = windSpeed.toString()
                }

                val splitDate = weatherItem.date.split("-")
                it.textViewPart.text = splitDate[0].toUpperCase()
                it.textViewPart2.text = "${splitDate[1]} ${splitDate[2]}"
            }
        }
    }
}
