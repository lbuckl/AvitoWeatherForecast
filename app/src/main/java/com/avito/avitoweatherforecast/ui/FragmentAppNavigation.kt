package com.avito.avitoweatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.avito.avitoweatherforecast.R
import com.avito.avitoweatherforecast.databinding.FragmentAppNavigationViewBinding
import com.avito.avitoweatherforecast.ui.weather.FragmentWeather
import kotlinx.coroutines.*

class FragmentAppNavigation : Fragment() {
    private var _binding: FragmentAppNavigationViewBinding? = null
    private val binding: FragmentAppNavigationViewBinding
        get() {
            return _binding!!
        }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val FAB_ANIMATION_DURATION = 600L

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonUpFlag = true

        //для скрытия появления
        val fade = Fade().setDuration(FAB_ANIMATION_DURATION)

        TransitionManager.beginDelayedTransition(binding.root, fade)

        binding.fabMenu.setOnClickListener {
            if (buttonUpFlag) {
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

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        _binding = null
    }
}