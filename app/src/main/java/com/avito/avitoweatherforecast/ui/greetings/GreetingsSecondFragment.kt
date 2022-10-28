package com.avito.avitoweatherforecast.ui.greetings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.avito.avitoweatherforecast.databinding.FragmentGreetingsSecondBinding
import com.avito.avitoweatherforecast.utils.VISIBLE_DELAY

/**
 * Второй из трёх приветственных фрагментов
 * всплывает только при первом запуске
 */
class GreetingsSecondFragment:Fragment() {
    private var _binding: FragmentGreetingsSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsSecondBinding.inflate(inflater)
        return binding.root
    }

    fun setTextVisible(){
        if (!binding.textView.isVisible){
            val fade = Fade().setDuration(VISIBLE_DELAY)
            TransitionManager.beginDelayedTransition(binding.root,fade)
            binding.textView.visibility = View.VISIBLE
        }
    }
}