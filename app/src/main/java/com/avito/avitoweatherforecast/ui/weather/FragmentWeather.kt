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
import androidx.recyclerview.widget.LinearLayoutManager
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.databinding.FragmentWeatherFcBinding
import com.avito.avitoweatherforecast.utils.loadIconFromYandex
import com.avito.avitoweatherforecast.utils.setWindDirection
import com.avito.avitoweatherforecast.utils.toast
import com.avito.avitoweatherforecast.viewmodel.WeatherAppState
import com.avito.avitoweatherforecast.viewmodel.WeatherViewModel
import com.gb.weather.view.weatherlist.FragmentWeatherDayRecyclerAdapter
import com.gb.weather.view.weatherlist.FragmentWeatherWeekRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Фрагмент отображения погоды
 */
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

        binding.weatherFcWeekRecyclerview.addItemDecoration(
            DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
        )
    }

    override fun onResume() {
        super.onResume()
        //Показываем кнопку, когда фрагмент активен
        requireActivity().findViewById<FloatingActionButton>(R.id.fab_menu).visibility = View.VISIBLE
    }

    private fun renderData(weatherAppState: WeatherAppState){
        when (weatherAppState){
            is WeatherAppState.Success -> {
                with(weatherAppState.weather){
                    binding.progressBar.isVisible = false
                    binding.textViewCityName.text = "${city.country}, ${city.name}"
                    binding.weatherNowDataLayout.textViewTempValue.text =
                        "${data.temperature}${requireContext().resources.getString(R.string.dim_g_c)}"
                    binding.weatherNowDataLayout.textViewPressureValue.text = data.pressure.toString()
                    binding.weatherNowDataLayout.textViewWindValue.text =
                        "${data.windSpeed} ${requireContext().resources.getString(R.string.dim_mc)}"
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
                binding.root.toast(requireContext().resources.getString(R.string.error_weather_loading))
            }
            is WeatherAppState.Empty ->{

            }
        }
    }

    //Поиск погоды по введённым данным
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