package com.avito.avitoweatherforecast.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avito.avitoweatherforecast.databinding.FragmentSettingsBinding
import com.avito.avitoweatherforecast.ui.greetings.GreetingsActivity
import com.avito.avitoweatherforecast.utils.PREF_SETTINGS
import com.avito.avitoweatherforecast.utils.PREF_THEME_INT
import com.avito.avitoweatherforecast.utils.THEME_DARK
import com.avito.avitoweatherforecast.utils.THEME_LIGHT
import com.google.android.material.chip.ChipGroup

/**
 * Фрагмент реализующий окно настроек приложения
 */
class SettingsFragment : Fragment() {

    private var _bindingSettings: FragmentSettingsBinding? = null
    private val bindingSettings get() = _bindingSettings!!

    //Переменные по которым вычисляется, была ли изменена тема
    private var oldTheme = THEME_LIGHT
    private var newTheme = THEME_LIGHT

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingSettings = FragmentSettingsBinding.inflate(inflater)
        return bindingSettings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
        //инициализация чип группы для смены темы
        bindingSettings.chipGroupStyles.setOnCheckedStateChangeListener { chipGroup: ChipGroup,
                                                                          mutableList: MutableList<Int> ->
            with(bindingSettings) {
                when (chipGroup.checkedChipId) {
                    chipLight.id -> newTheme = THEME_LIGHT
                    chipDark.id -> newTheme = THEME_DARK
                    else -> {}
                }
            }
        }

        //инициализация кнопки "Применить"
        bindingSettings.buttonApply.setOnClickListener {
            val lastFragment = requireActivity()
                .supportFragmentManager.findFragmentByTag("POTD")

            if (oldTheme != newTheme) {
                setAppTheme(newTheme)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .remove(this)
                    .show(lastFragment!!)
                    .commit()
                requireActivity().recreate()
            } else {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .remove(this)
                    .show(lastFragment!!)
                    .commit()
            }
        }

        //Кнопка открытия активити с приветствием
        bindingSettings.buttonGreetings.setOnClickListener {
            val intent = Intent(requireActivity().intent)
            intent.setClass(requireContext(), GreetingsActivity::class.java)
            requireActivity().startActivity(intent)
        }
    }

    private fun initialization() {
        oldTheme = getCodeTheme()
        newTheme = oldTheme
        when (oldTheme) {
            THEME_LIGHT -> bindingSettings.chipLight.isChecked = true
            THEME_DARK -> bindingSettings.chipDark.isChecked = true
        }
    }

    // Сохранение настроек стиля
    private fun setAppTheme(styleCode: Int) {
        val sharedPrefer =
            requireContext().getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)
        val editor = sharedPrefer.edit()
        editor.putInt(PREF_THEME_INT, styleCode).apply()
    }

    private fun getCodeTheme(): Int {
        val sharedPref = requireContext().getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)
        return sharedPref.getInt(PREF_THEME_INT, THEME_LIGHT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSettings = null
    }
}
