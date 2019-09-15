package com.example.easywin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.easywin.adapters.MainPageViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var userHolder: UserHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as EasyWinApp).appComponent.inject(this)

        val currentUser = userHolder.currentUser()
        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val adapter = MainPageViewPagerAdapter(lifecycle, supportFragmentManager)
        anchor.adapter = adapter
        anchor.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> main_page_navigation_view.selectedItemId = R.id.homeNavigation
                    1 -> main_page_navigation_view.selectedItemId = R.id.historyNavigation
                    2 -> main_page_navigation_view.selectedItemId = R.id.notificationsNavigation
                    else -> main_page_navigation_view.selectedItemId = R.id.settingsNavigation
                }
            }
        })

        main_page_navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeNavigation -> {
                    anchor.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.historyNavigation -> {
                    anchor.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notificationsNavigation -> {
                    anchor.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.settingsNavigation -> {
                    anchor.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        anchor.isUserInputEnabled = false

        main_page_navigation_view.selectedItemId = R.id.homeNavigation

    }

    override fun onResume() {
        super.onResume()

    }
}
