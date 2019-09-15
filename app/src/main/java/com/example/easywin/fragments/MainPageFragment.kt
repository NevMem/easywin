package com.example.easywin.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.easywin.*
import kotlinx.android.synthetic.main.main_page.view.*
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.main_page.*
import java.lang.Exception
import javax.inject.Inject


class MainPageFragment : Fragment() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run{
            ViewModelProviders.of(this)[UserViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username_textview.text = "${viewModel.userHolder.currentUser()?.surname.toString()}\n${viewModel.userHolder.currentUser()?.name.toString()}"


        view.join_lobby_button.setOnClickListener {
            var intent = Intent(context, JoinActivity::class.java)
            startActivity(intent)
        }

        view.topPanel.setOnClickListener {
            var intent = Intent(context, UserInfoActivity::class.java)
            startActivity(intent)
        }

        view.create_lobby_button.setOnClickListener {
            //val intent = Intent(context, CreatemeetActivity::class.java)
            //startActivity(intent)
            val intent = Intent(context, EndOfCreatingMeeting::class.java)
            startActivity(intent)
            //val intent = Intent(context, AssignTheMount::class.java)
            //startActivity(intent)
        }
    }

}