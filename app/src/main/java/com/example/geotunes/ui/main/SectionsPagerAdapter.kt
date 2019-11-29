package com.example.geotunes.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.geotunes.LyricActivity
import com.example.geotunes.MapActivity
import com.example.geotunes.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return LyricActivity()
            }
            1 -> {
                return MapActivity()
            }
            else -> return LyricActivity()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "lyric"
            1 -> return "map"
        }
        return null
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}