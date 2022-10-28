package com.avito.avitoweatherforecast.ui

import android.Manifest
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
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
import com.avito.avitoweatherforecast.ui.weather.FragmentWeather
import com.avito.avitoweatherforecast.utils.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.system.measureTimeMillis

class FragmentAppNavigation : Fragment() {
    private var _binding: FragmentAppNavigationViewBinding? = null
    private val binding: FragmentAppNavigationViewBinding
        get() {
            return _binding!!
        }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val FAB_ANIMATION_DURATION = 600L
    private val TAG_WEATHER_FRAGMENT = "weather_fragment"
    lateinit var locationManager: LocationManager
    var fabMyLocationLoading = false
    val fragmentWeather = FragmentWeather()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentAppNavigationViewBinding.inflate(inflater)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.navigationContainer.id, fragmentWeather,TAG_WEATHER_FRAGMENT)
            .commit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFabMenu()
        initFabMyLocation()
        inintMenu()
    }

    private fun initFabMenu() {
        var buttonUpFlag = true

        //для скрытия появления
        val fade = Fade().setDuration(FAB_ANIMATION_DURATION)
        TransitionManager.beginDelayedTransition(binding.root, fade)

        binding.fabMenu.setOnClickListener {
            if (!binding.fabMyLocation.isVisible) {
                binding.fabFavorite.visibility = View.VISIBLE
                changeSetFAB(true)

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
                buttonUpFlag = !buttonUpFlag
            } else {
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
                buttonUpFlag = !buttonUpFlag
            }
        }
    }

    private fun changeSetFAB(isUp: Boolean) {
        //для изменения положение
        val changeBounds = ChangeBounds()
        changeBounds.duration = FAB_ANIMATION_DURATION
        ConstraintSet().apply {
            clone(binding.containLayout)
            binding.fabFavorite.id.let {
                if (isUp) {
                    this.clear(it, ConstraintSet.BOTTOM)
                    this.connect(it, ConstraintSet.BOTTOM, binding.fabMenu.id, ConstraintSet.TOP, 8)
                } else {
                    this.clear(it, ConstraintSet.BOTTOM)
                    this.connect(
                        it,
                        ConstraintSet.BOTTOM,
                        binding.containLayout.id,
                        ConstraintSet.BOTTOM,
                        8
                    )
                }
            }

            binding.fabMyLocation.id.let {
                if (isUp) {
                    this.clear(it, ConstraintSet.BOTTOM)
                    this.connect(
                        it,
                        ConstraintSet.BOTTOM,
                        binding.fabFavorite.id,
                        ConstraintSet.TOP,
                        8
                    )
                } else {
                    this.clear(it, ConstraintSet.BOTTOM)
                    this.connect(
                        it,
                        ConstraintSet.BOTTOM,
                        binding.containLayout.id,
                        ConstraintSet.BOTTOM,
                        8
                    )
                }
            }

            TransitionManager.beginDelayedTransition(
                binding.containLayout,
                TransitionSet().addTransition(changeBounds)
            )
            applyTo(binding.containLayout)
        }
    }

    private fun initFabMyLocation(){
        binding.fabMyLocation.setOnClickListener {
            if (checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d("@@@","ACCESS_FINE_LOCATION")
                locationManager =
                    requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.d("@@@","GPS_PROVIDER")
                    //locationManager теперь имеет доступ ко всем провайдерам по умолчанию
                    //locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    fabMyLocationLoading = true
                    animateLoadingGeolocation()
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        2000L,
                        0F, locationListener
                    )
                }else{
                    Log.d("@@@","checkPermission")
                }
            }
            else {
                getSinglePermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    requireActivity(),requireContext(),REQUEST_CODE_GEOLOCATION)
            }
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("@@@", "${location.latitude} ${location.longitude}")
            getAddress(location)
        }

        override fun onProviderDisabled(provider: String) {
            Log.d("@@@", "onProviderDisabled")
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            Log.d("@@@", "onProviderEnabled")
            super.onProviderEnabled(provider)
        }
    }

    fun getAddress(location: Location) {
        Log.d("@@@", "getAddress")
        locationManager.removeUpdates(locationListener)
        val geocoder = Geocoder(context, Locale("ru_RU"))
        val time = measureTimeMillis {
            coroutineScope.launch {
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                Log.v("@@@","${address.first().locality},${location.latitude}, ${location.longitude}")
                fabMyLocationLoading = false
                fragmentWeather.getWeather(address.first().locality)

            }.start()
        }
        Log.d("@@@", "$time")
    }

    private fun animateLoadingGeolocation(){
        binding.fabMyLocation.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                (R.drawable.ic_loading)
            )
        )
        coroutineScope.launch{
            while (fabMyLocationLoading){
                ObjectAnimator
                    .ofFloat(binding.fabMyLocation,View.ROTATION,0f,360F)
                    .setDuration(FAB_ANIMATION_DURATION*2)
                    .start()
                delay(FAB_ANIMATION_DURATION*2)
            }
            binding.fabMyLocation.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    (R.drawable.ic_location)
                )
            )
        }

    }

    private fun inintMenu() {
        binding.navigationView.setNavigationItemSelectedListener {
            Log.v("@@@","navigationView")
            when (it.itemId){
                R.id.Drawer_About -> {
                    Log.v("@@@","Drawer_About")
                    val findFragment = requireActivity().supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_ABOUT)
                    //replaceFragment(findFragment,AbautFragment(),TAG_FRAGMENT_ABOUT)
                    return@setNavigationItemSelectedListener true
                }
                R.id.Drawer_Settings -> {
                    Log.v("@@@","Drawer_Settings")
                    val findFragment = requireActivity().supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_SETTINGS)
                    replaceFragment(findFragment,SettingsFragment(),TAG_FRAGMENT_SETTINGS)
                    return@setNavigationItemSelectedListener true
                }
                R.id.Drawer_exit -> {
                    Log.v("@@@","Drawer_exit")
                    AlertDialog.Builder(requireActivity()).also {
                        it.setTitle("Выход")
                            .setPositiveButton("Да"
                            ) { dialog, which -> requireActivity().finishActivity(0) }
                            .setNegativeButton("Нет",
                            ){ dialog, which -> binding.drawerLayout.close()}
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
    private fun replaceFragment(findFragment: Fragment?, newFragment: Fragment, flag:String){
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


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        locationManager.removeUpdates(locationListener)
        _binding = null
    }
}