package com.example.easywin.fragments

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.easywin.R
import com.example.easywin.TestActivity
import kotlinx.android.synthetic.main.fragment_top_bar.view.*
import kotlinx.android.synthetic.main.settings_page.view.*

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.kek.text = getText(R.string.settings)
        view.test_activity_button.setOnClickListener{
            var intent = Intent(context, TestActivity::class.java)
            startActivity(intent)
        }

        view.qr_activity_button.setOnClickListener{
            /*var pm: PackageManager? = context?.packageManager
            var intent1: Intent? = pm?.getLaunchIntentForPackage("com.solarl.fastpay")
            if (intent1 != null)
                context?.startActivity(intent1)*/

            var intent = Intent(Intent.ACTION_SEND)
            intent.setComponent(ComponentName("com.solarl.fastpay.android.app","com.solarl.fastpay.android.app.navActivity.view.FileActivity"))
            startActivity(intent)
        }
    }


}