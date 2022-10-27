package com.avito.avitoweatherforecast.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.databinding.FragmentWeatherFcBinding
import com.avito.avitoweatherforecast.utils.*
import com.gb.weather.view.weatherlist.FragmentWeatherDayRecyclerAdapter
import com.gb.weather.view.weatherlist.FragmentWeatherWeekRecyclerAdapter

class FragmentWeather:Fragment() {

    companion object {
        lateinit var viewModel: WeatherViewModel
    }

    private var _binding: FragmentWeatherFcBinding? = null
    private val binding: FragmentWeatherFcBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentWeatherFcBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
    }

    private fun renderData(weatherAppState: WeatherAppState){
        when (weatherAppState){
            is WeatherAppState.Success -> {
                with(weatherAppState.weather){
                    binding.progressBar.isVisible = false
                    binding.textViewCityName.text = city.name
                    binding.weatherNowDataLayout.textViewTempValue.text = data.temperature.toString()
                    binding.weatherNowDataLayout.textViewPressureValue.text = data.pressure.toString()
                    binding.weatherNowDataLayout.textViewWindValue.text = data.windSpeed.toString()
                    binding.weatherNowDataLayout.imageViewWind.setWindDirection(data.windDirection)
                    binding.weatherNowDataLayout.imageView.loadIconFromYandex(data.icon)
                    binding.weatherDayRecyclerview.adapter = FragmentWeatherDayRecyclerAdapter(dataDay)
                    binding.weatherFcWeekRecyclerview.adapter = FragmentWeatherWeekRecyclerAdapter(dataDay)
                }
            }
            is WeatherAppState.Loading -> {
                binding.progressBar.isVisible = true
            }
            is WeatherAppState.Error -> {

            }
            else -> {}
        }
    }

    private fun initialization(){
        binding.inputLayout.setEndIconOnClickListener {
            binding.inputEditText.text.toString().also { text ->
                if (text != ""){
                    //viewModel.getWeatherFromCityName(text)
                    viewModel.getWeatherByCity(text)
                }
            }
        }
    }

    /*private fun setWindDirection(direction: String){
        when (direction){
            DIRECTION_NORTH -> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_n)
            DIRECTION_SOUTH -> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_s)
            DIRECTION_WEST -> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_w)
            DIRECTION_EAST-> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_e)
            DIRECTION_NORTH_WEST -> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_nw)
            DIRECTION_NORTH_EAST -> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_ne)
            DIRECTION_SOUTH_WEST -> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_sw)
            DIRECTION_SOUTH_EAST -> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_se)
            else -> binding.weatherNowDataLayout.imageViewWind.load(R.drawable.ic_wind_default)
        }
    }*/
}