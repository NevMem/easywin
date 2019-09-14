package com.example.easywin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.easywin.JoinActivity
import com.example.easywin.R
import kotlinx.android.synthetic.main.main_page.view.*

class MainPageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.join_lobby_button.setOnClickListener {
            var intent = Intent(context, JoinActivity::class.java)
            startActivity(intent)
        }
    }

}