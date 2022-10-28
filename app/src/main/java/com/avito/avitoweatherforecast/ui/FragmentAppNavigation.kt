package com.avito.avitoweatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avito.avitoweatherforecast.databinding.FragmentAppNavigationViewBinding
import com.avito.avitoweatherforecast.ui.weather.FragmentWeather

class FragmentAppNavigation:Fragment() {
    private var _binding: FragmentAppNavigationViewBinding? = null
    private val binding: FragmentAppNavigationViewBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentAppNavigationViewBinding.inflate(inflater)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.navigationContainer.id, FragmentWeather())
            .commit()
        return binding.root
    }
}