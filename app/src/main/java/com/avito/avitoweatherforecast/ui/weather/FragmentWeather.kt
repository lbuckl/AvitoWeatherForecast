package com.avito.avitoweatherforecast.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.avito.avitoweatherforecast.databinding.FragmentWeatherFcBinding
import com.avito.avitoweatherforecast.utils.loadIconFromYandex
import com.avito.avitoweatherforecast.utils.setWindDirection
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

        binding.weatherDayRecyclerview.addItemDecoration(
            DividerItemDecoration(requireContext(),GridLayoutManager.HORIZONTAL)
        )
    }

    private fun renderData(weatherAppState: WeatherAppState){
        when (weatherAppState){
            is WeatherAppState.Success -> {
                with(weatherAppState.weather){
                    binding.progressBar.isVisible = false
                    binding.textViewCityName.text = "${city.country}, ${city.name}"
                    binding.weatherNowDataLayout.textViewTempValue.text = data.temperature.toString()
                    binding.weatherNowDataLayout.textViewPressureValue.text = data.pressure.toString()
                    binding.weatherNowDataLayout.textViewWindValue.text = data.windSpeed.toString() + " м/с"
                    binding.weatherNowDataLayout.imageViewWind.setWindDirection(data.windDirection)
                    binding.weatherNowDataLayout.imageView.loadIconFromYandex(data.icon)
                    binding.weatherDayRecyclerview.adapter = FragmentWeatherDayRecyclerAdapter(dataDay)
                    binding.weatherFcWeekRecyclerview.adapter = FragmentWeatherWeekRecyclerAdapter(dataWeek)
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
                    getWeather(text)
                }
            }
        }
    }

    //Не приватная, потому что необходимо
    fun getWeather(text:String){
        viewModel.getWeatherByCity(text)
    }
}