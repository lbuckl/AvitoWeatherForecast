package com.avito.avitoweatherforecast.ui.greetings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Адаптер ViewPager для работы приветственных фрагментов
 */
class GreetingsViewPagerAdapter(fragmentActivity: FragmentActivity, private val fragments: Array<Fragment>):
    FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val EARTH_FRAGMENT = 0
        private const val MARS_FRAGMENT = 1
        private const val WEATHER_FRAGMENT = 2
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                fragments[EARTH_FRAGMENT]
            }
            1 -> {
                fragments[MARS_FRAGMENT]
            }
            2 -> {
                fragments[WEATHER_FRAGMENT]
            }
            else -> fragments[EARTH_FRAGMENT]
        }
    }
}