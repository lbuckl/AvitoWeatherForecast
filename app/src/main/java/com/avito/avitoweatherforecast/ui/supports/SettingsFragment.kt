package com.avito.avitoweatherforecast.ui.supports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.databinding.FragmentSettingsBinding
import com.avito.avitoweatherforecast.utils.THEME_LIGHT
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Фрагмент реализующий окно настроек приложения
 */
class SettingsFragment : Fragment() {

    private var _bindingSettings: FragmentSettingsBinding? = null
    private val bindingSettings get() = _bindingSettings!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingSettings = FragmentSettingsBinding.inflate(inflater)
        bindingSettings.textViewInDevelop.text = requireContext().resources.getText(R.string.in_develop)

        requireActivity().findViewById<FloatingActionButton>(R.id.fab_menu).visibility = View.INVISIBLE
        return bindingSettings.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSettings = null
    }
}
