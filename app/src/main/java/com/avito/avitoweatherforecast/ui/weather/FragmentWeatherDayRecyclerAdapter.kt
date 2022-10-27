package com.gb.weather.view.weatherlist

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avito.avitoweatherforecast.databinding.FragmentWeatherFcDayItemBinding
import com.avito.avitoweatherforecast.domain.WeatherData
import com.avito.avitoweatherforecast.utils.loadIconFromYandex
import com.avito.avitoweatherforecast.utils.setWindDirection

/**
 * Кастомный адаптер для вывода списка элементов меню в recyclerview
 */
class FragmentWeatherDayRecyclerAdapter (private val weatherListCity:List<WeatherData>):
    RecyclerView.Adapter<FragmentWeatherDayRecyclerAdapter.WeatherNowViewHolder>() {

    //Создаёт ViewHolder объект опираясь на их количество, но с запасом, чтобы можно было скролить
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherNowViewHolder {
        val binding = FragmentWeatherFcDayItemBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherNowViewHolder(binding.root)
    }

    //Связываем используемые текстовые метки с данными
    override fun onBindViewHolder(holder: WeatherNowViewHolder, position: Int) {
        holder.bind(weatherListCity[position], position)
    }

    //Возвращает количество элементов списка
    override fun getItemCount(): Int {
        return weatherListCity.size
    }

    //Класс который непосредственно отображает данные в каждом элементе recyclerview
    inner class WeatherNowViewHolder(view: View): RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(weatherItem: WeatherData, position: Int){
            FragmentWeatherFcDayItemBinding.bind(itemView).let {
                //it.textViewPart.text = weatherItem.part
                it.imageView.loadIconFromYandex(weatherItem.icon)
                it.imageView2.setWindDirection(weatherItem.windDirection)
                it.textView.text = weatherItem.temperature.toString()
                it.textView3.text = weatherItem.windSpeed.toString()
                when(position){
                    0 -> it.textViewPart.text = "Ночь"
                    1 -> it.textViewPart.text = "Утро"
                    2 -> it.textViewPart.text = "День"
                    3 -> it.textViewPart.text = "Вечер"
                }
            }
        }
    }
}
