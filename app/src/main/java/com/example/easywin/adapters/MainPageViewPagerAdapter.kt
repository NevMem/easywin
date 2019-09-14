package com.example.easywin.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.easywin.fragments.HistoryFragment
import com.example.easywin.fragments.MainPageFragment
import com.example.easywin.fragments.NotificationsFragment
import com.example.easywin.fragments.SettingsFragment

class MainPageViewPagerAdapter(lifecycle: Lifecycle, fragmentManager: FragmentManager) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val historyFragment = HistoryFragment()
    private val notificationsFragment = NotificationsFragment()
    private val settingsFragment = SettingsFragment()
    private val mainPageFragment = MainPageFragment()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> mainPageFragment
            1 -> historyFragment
            2 -> notificationsFragment
            else -> settingsFragment
        }
    }

    override fun getItemCount() = 4
}