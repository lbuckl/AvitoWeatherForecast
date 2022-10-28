package com.avito.avitoweatherforecast.ui

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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
import com.avito.avitoweatherforecast.ui.weather.FragmentWeather
import com.avito.avitoweatherforecast.utils.REQUEST_CODE_GEOLOCATION
import com.avito.avitoweatherforecast.utils.checkSinglePermission
import com.avito.avitoweatherforecast.utils.getSinglePermission
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

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        locationManager.removeUpdates(locationListener)
        _binding = null
    }
}