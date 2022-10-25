package com.avito.avitoweatherforecast.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avito.avitoweatherforecast.databinding.FragmentWeatherFcBinding

class FragmentWeather:Fragment() {
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
}