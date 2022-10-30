package com.avito.avitoweatherforecast.ui.greetings

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
        coroutineScope.launch {
        delay(VISIBLE_DELAY/2)
        //Анимация появления текста
        val fade = Fade().setDuration(VISIBLE_DELAY)
        TransitionManager.beginDelayedTransition(binding.root,fade)
        binding.textViewGeneral.visibility = View.VISIBLE
            delay(VISIBLE_DELAY/2)
            //Анимация свайпа
            ViewCompat.animate(binding.imageViewSwipe)
                .translationX(-100.0f)
                .setDuration(VISIBLE_DELAY/2)
                .setInterpolator(CycleInterpolator(2F))
                .setStartDelay(VISIBLE_DELAY/2).start()
            delay(VISIBLE_DELAY)
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