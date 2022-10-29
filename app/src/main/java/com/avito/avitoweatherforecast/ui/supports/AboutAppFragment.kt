package com.avito.avitoweatherforecast.ui.supports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.databinding.FragmentAboutAppBinding

/**
 * Фрагмент реализующий окно информации о приложении
 */
class AboutAppFragment : Fragment() {

    private var _bindingSettings: FragmentAboutAppBinding? = null
    private val bindingSettings get() = _bindingSettings!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingSettings = FragmentAboutAppBinding.inflate(inflater)
        bindingSettings.textViewInDevelop.text = requireContext().resources.getText(R.string.in_develop)
        return bindingSettings.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSettings = null
    }
}
