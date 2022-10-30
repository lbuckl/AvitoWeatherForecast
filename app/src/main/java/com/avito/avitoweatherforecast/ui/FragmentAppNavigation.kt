package com.avito.avitoweatherforecast.ui

import android.Manifest
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.databinding.FragmentAppNavigationViewBinding
import com.avito.avitoweatherforecast.model.request.geoposition.GeocoderRequest
import com.avito.avitoweatherforecast.ui.supports.AboutAppFragment
import com.avito.avitoweatherforecast.ui.supports.SettingsFragment
import com.avito.avitoweatherforecast.ui.weather.FragmentWeather
import com.avito.avitoweatherforecast.utils.*
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*

/**
 * Основной фрагмент навигации приложения
 */
class FragmentAppNavigation : Fragment() {
    private var _binding: FragmentAppNavigationViewBinding? = null
    private val binding: FragmentAppNavigationViewBinding
        get() {
            return _binding!!
        }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val FAB_ANIMATION_DURATION = 600L // задержка для анимации
    private val TAG_WEATHER_FRAGMENT = "weather_fragment" // тег фрагмента
    private var fabMyLocationLoading = false // флаг состояния загрузки данных геолокации
    private val fragmentWeather = FragmentWeather()
    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentAppNavigationViewBinding.inflate(inflater)

        //Подгружаем начальный фрагмент
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.navigationContainer.id, fragmentWeather, TAG_WEATHER_FRAGMENT)
            .commit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Инициализация функциональных кнопок и пунктов меню NavigationView
        initFabMenu()
        initFabMyLocation()
        initFabFavorite()
        inintMenu()
        initChipAvito()
    }

    //region работа функциональных кнопок
    //Функция работы всплывания и исчезания функциональных кнопок
    private fun initFabMenu() {
        //формат анимации для скрытия появления
        val fade = Fade().setDuration(FAB_ANIMATION_DURATION)
        TransitionManager.beginDelayedTransition(binding.root, fade)

        //раскрываем и скрываем меню (вылет кнопок)
        binding.fabMenu.setOnClickListener {
            if (!binding.fabMyLocation.isVisible) {
                binding.fabFavorite.visibility = View.VISIBLE
                binding.fabMyLocation.visibility = View.VISIBLE
                changeSetFAB(true)

                coroutineScope.launch {
                    delay(FAB_ANIMATION_DURATION / 2)
                    binding.fabMenu.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            (R.drawable.ic_down)
                        )
                    )
                }
            } else {
                //Измененение позиции кнопок
                changeSetFAB(false)
                coroutineScope.launch {
                    delay(FAB_ANIMATION_DURATION / 3)
                    binding.fabFavorite.visibility = View.INVISIBLE
                    delay(FAB_ANIMATION_DURATION / 3)
                    binding.fabMyLocation.visibility = View.INVISIBLE
                }

                binding.fabMenu.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        (R.drawable.ic_menu)
                    )
                )
            }
        }
    }

    /**
     * Функция изменения положений выпливающих кнопок меню
     * @param show - указатель того, развернуть меню или свернуть
     * функция универсальная, для добавления новой кнопки достаточно
     * вписать её в лист "buttons"
     * ВАЖНО!!! Первой в списке идёт кнопка меню
     * _____________________________________________________________
     * Возвращает результат работы: true - удачно, false - ошибка
     */
    private fun changeSetFAB(show: Boolean) {
        val buttons = listOf(
            binding.fabMenu,
            binding.fabFavorite,
            binding.fabMyLocation
        )
        //для изменения положения
        val changeBounds = ChangeBounds()
        changeBounds.duration = FAB_ANIMATION_DURATION

        ConstraintSet().apply {
            clone(binding.containLayout)
            // Разворачиваем/сворачиваем кнопки меню

            try {
                if (show) {//привязываем каждую последующую кнопку к предыдущей
                    for (i in 1 until buttons.size) {
                        this.clear(buttons[i].id, ConstraintSet.BOTTOM)
                        this.connect(
                            buttons[i].id,
                            ConstraintSet.BOTTOM,
                            buttons[i - 1].id,
                            ConstraintSet.TOP,
                            8
                        )
                    }
                } else { //привязываем все кнопки к низу экрана
                    for (i in 1 until buttons.size) {
                        this.clear(buttons[i].id, ConstraintSet.BOTTOM)
                        this.connect(
                            buttons[i].id,
                            ConstraintSet.BOTTOM,
                            binding.containLayout.id,
                            ConstraintSet.BOTTOM,
                            8
                        )
                    }
                }
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
                Log.e("Developer_error", "fun changeSetFAB: IndexOutOfBounds")
                showToast("${resources.getString(R.string.application_error)}")
            } finally {
                binding.fabFavorite.visibility = View.INVISIBLE
                binding.fabMyLocation.visibility = View.INVISIBLE
            }

            TransitionManager.beginDelayedTransition(
                binding.containLayout,
                TransitionSet().addTransition(changeBounds)
            )
            applyTo(binding.containLayout)
        }
    }
    //endregion

    //region поиск погоды по текущей геолокации
    //Функция поиска погоды по текущей геолокации
    private fun initFabMyLocation() {
        //подписка на получение геоданных
        binding.fabMyLocation.setOnClickListener {
            if (checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                locationManager =
                    requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    fabMyLocationLoading = true
                    it.isClickable = false
                    animateLoadingGeolocation()
                    it.toast(requireContext().resources.getString(R.string.need_some_time))

                    try {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0L,
                            0F, locationListener
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.e("Developer_error", "fun changeSetFAB: IndexOutOfBounds")
                        showToast("${resources.getString(R.string.application_error)}")
                        fabMyLocationLoading = false
                        it.isClickable = true
                    } catch (e: IllegalArgumentException) {
                        Log.e("Developer_error", "fun changeSetFAB: IndexOutOfBounds")
                        showToast("${resources.getString(R.string.application_error)}")
                        fabMyLocationLoading = false
                        it.isClickable = true
                    }
                } else {
                    showToast(requireContext().resources.getString(R.string.no_permission_geolocation))
                }
            } else {
                getSinglePermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    requireActivity(), requireContext(), REQUEST_CODE_GEOLOCATION
                )
            }
        }
    }

    private val locationListener = LocationListener { location -> getAddress(location) }

    //получения адреса по текущей геолокации
    private fun getAddress(location: Location) {
        //Если геоданные получены, то отключаем подписку
        locationManager.removeUpdates(locationListener)
        fabMyLocationLoading = false
        binding.fabMyLocation.isClickable = true

        //Выдаём запрос во ViewModel на поиск погоды в найденной локации
        fragmentWeather.getWeatherByLocation(location.latitude, location.longitude)
    }

    //Функция анимации кнопки (кнопка крутится пока идёт запрос геолокации)
    private fun animateLoadingGeolocation() {
        binding.fabMyLocation.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                (R.drawable.ic_loading)
            )
        )
        coroutineScope.launch {
            while (fabMyLocationLoading) {
                ObjectAnimator
                    .ofFloat(binding.fabMyLocation, View.ROTATION, 0f, 360F)
                    .setDuration(FAB_ANIMATION_DURATION * 2)
                    .start()
                delay(FAB_ANIMATION_DURATION * 2)
            }
            binding.fabMyLocation.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    (R.drawable.ic_location)
                )
            )
        }
    }
    //endregion

    //Функция записи текущего города как "любимого"
    private fun initFabFavorite() {
        binding.fabFavorite.setOnClickListener {
            //получаем последнюю запрошенную локацию
            val sharedPref =
                requireContext().getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)
            val lastCity = sharedPref.getString(PREF_SETTINGS_LAST_CITY, PREF_SETTINGS_DEFAULT_CITY)
            //записываем последнюю запрошенную локацию
            val editor = sharedPref.edit()
            editor.putString(PREF_SETTINGS_FAVORITE_CITY, lastCity).apply()
            it.toast(requireContext().resources.getString(R.string.location_added))
        }
    }

    //Работа бокового меню Drawer+NavigationView
    private fun inintMenu() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Drawer_About -> {
                    val findFragment = requireActivity().supportFragmentManager.findFragmentByTag(
                        TAG_FRAGMENT_ABOUT
                    )
                    replaceFragment(findFragment, AboutAppFragment(), TAG_FRAGMENT_ABOUT)
                    return@setNavigationItemSelectedListener true
                }
                R.id.Drawer_Settings -> {
                    val findFragment = requireActivity().supportFragmentManager.findFragmentByTag(
                        TAG_FRAGMENT_SETTINGS
                    )
                    replaceFragment(findFragment, SettingsFragment(), TAG_FRAGMENT_SETTINGS)
                    return@setNavigationItemSelectedListener true
                }
                R.id.Drawer_exit -> {
                    with(requireContext().resources) {
                        AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.exit))
                            .setMessage(getString(R.string.do_you_want_exit))
                            .setPositiveButton(
                                getString(R.string.yes)
                            ) { dialog, which ->
                                requireActivity().finish()
                            }
                            .setNegativeButton(
                                getString(R.string.no)
                            ) { dialog, which -> }
                            .show()
                    }
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
    }

    /**
     * Функция для замены фрагмента в контейнере
     * если фрагмент ещё живой, то возвращает его в контейнер
     * если фрагмента нет, то создаёт новый
     */
    private fun replaceFragment(findFragment: Fragment?, newFragment: Fragment, flag: String) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            if (findFragment == null) {
                replace(binding.navigationContainer.id, newFragment, flag)
                    .addToBackStack("Contacts")
                    .commitAllowingStateLoss()
            } else {
                replace(binding.navigationContainer.id, findFragment, flag)
                    .commitAllowingStateLoss()
            }
        }
    }

    //Инициализация работы чип кнопки поиска в авито
    private fun initChipAvito() {
        binding.chipAvito.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://www.avito.ru")
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        locationManager.removeUpdates(locationListener)
        _binding = null
    }
}