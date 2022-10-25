package com.avito.avitoweatherforecast.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avito.avitoweatherforecast.databinding.FragmentWeatherFcBinding

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

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
    }

    private fun renderData(weatherAppState: WeatherAppState){

    }
}