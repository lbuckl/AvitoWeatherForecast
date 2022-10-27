package com.gb.weather.view.weatherlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avito.avitoweatherforecast.databinding.FragmentWeatherFcDayItem2Binding
import com.avito.avitoweatherforecast.domain.Weather

/**
 * Кастомный адаптер для вывода списка элементов меню в recyclerview
 */
class FragmentWeatherDayRecyclerAdapter (private val weatherListCity:List<Weather>):
    RecyclerView.Adapter<FragmentWeatherDayRecyclerAdapter.WeatherNowViewHolder>() {

    //Создаёт ViewHolder объект опираясь на их количество, но с запасом, чтобы можно было скролить
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherNowViewHolder {
        val binding = FragmentWeatherFcDayItem2Binding.inflate(LayoutInflater.from(parent.context))
        return WeatherNowViewHolder(binding.root)
    }

    //Связываем используемые текстовые метки с данными
    override fun onBindViewHolder(holder: WeatherNowViewHolder, position: Int) {
        holder.bind(weatherListCity[position])
    }

    //Возвращает количество элементов списка
    override fun getItemCount(): Int {
        return weatherListCity.size
    }

    //Класс который непосредственно отображает данные в каждом элементе recyclerview
    inner class WeatherNowViewHolder(view: View): RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(weatherItem: Weather){
        }
    }
}
