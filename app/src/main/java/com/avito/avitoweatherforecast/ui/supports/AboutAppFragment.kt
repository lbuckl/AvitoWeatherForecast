package com.avito.avitoweatherforecast.ui.supports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avito.avitoweatherforecast.databinding.FragmentAboutAppBinding
import com.avito.avitoweatherforecast.databinding.FragmentSettingsBinding
import com.avito.avitoweatherforecast.utils.THEME_LIGHT

/**
 * Фрагмент реализующий окно настроек приложения
 */
class AboutAppFragment : Fragment() {

    private var _bindingSettings: FragmentAboutAppBinding? = null
    private val bindingSettings get() = _bindingSettings!!

    companion object {
        fun newInstance() = AboutAppFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingSettings = FragmentAboutAppBinding.inflate(inflater)
        return bindingSettings.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSettings = null
    }
}
