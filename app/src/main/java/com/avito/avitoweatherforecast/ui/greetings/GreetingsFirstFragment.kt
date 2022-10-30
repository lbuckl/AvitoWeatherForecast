package com.avito.avitoweatherforecast.ui.greetings

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.avito.avitoweatherforecast.databinding.FragmentGreetingsFirstBinding
import com.avito.avitoweatherforecast.utils.VISIBLE_DELAY
import kotlinx.coroutines.*

/**
 * Первый из трёх приветственных фрагментов
 * всплывает только при первом запуске
 */
class GreetingsFirstFragment : Fragment() {

    private var _binding: FragmentGreetingsFirstBinding? = null
    private val binding get() = _binding!!
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsFirstBinding.inflate(inflater)

        //Распределение времени для анимации и скрытия
        val duration = 1000L
        val startDelay = 1000L
        val visionDelay = duration + startDelay + 1000
        coroutineScope.launch {
        delay(startDelay)
        //Анимация появления текста
        val fade = Fade().setDuration(VISIBLE_DELAY)
        TransitionManager.beginDelayedTransition(binding.root,fade)
        binding.textViewGeneral.visibility = View.VISIBLE
            delay(duration)
            //Анимация свайпа
            ViewCompat.animate(binding.imageViewSwipe)
                .translationX(-100.0f)
                .setDuration(duration)
                .setInterpolator(CycleInterpolator(2F))
                .setStartDelay(startDelay).start()
            delay(visionDelay)
            binding.imageViewSwipe.visibility = View.INVISIBLE
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        _binding = null
    }

}