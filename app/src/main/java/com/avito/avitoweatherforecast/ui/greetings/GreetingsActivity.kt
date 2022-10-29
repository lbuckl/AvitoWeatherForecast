package com.avito.avitoweatherforecast.ui.greetings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.avito.avitoweatherforecast.databinding.ActivityGreetingsBinding

/**
 * ViewPager для работы приветственных фрагментов
 */
class GreetingsActivity: AppCompatActivity() {
    private var _binding:ActivityGreetingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGreetingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Фрагменты для пролистывания ViewPager
        val greetingsFirst = GreetingsFirstFragment()
        val greetingsSecond = GreetingsSecondFragment()
        val greetingsTreeth = GreetingsThreethFragment()

        //Список фрагментов для пролистывания ViewPager
        val fragments = arrayOf(
            greetingsFirst, greetingsSecond, greetingsTreeth)

        //Инициализируем адаптер и навешиваем лисенер по которому будет отображаться анимация
        binding.viewPager.let { it ->
            it.adapter = GreetingsViewPagerAdapter(this,fragments)
            it.registerOnPageChangeCallback(
                object: ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        when(position){
                            1 -> {
                                greetingsSecond.setTextVisible()
                            }
                            2 -> {
                                greetingsTreeth.setTextVisible()
                            }
                        }
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}