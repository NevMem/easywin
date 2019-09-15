package com.example.easywin.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.easywin.R
import com.example.easywin.UserViewModel
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.fragment_top_bar.*
import kotlinx.android.synthetic.main.info_item.*
import java.lang.Exception

class UserInfoActivity : AppCompatActivity() {

    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        kek.text = "Информация обо мне"


        viewModel = ViewModelProviders.of(this)[UserViewModel::class.java]


        name_info_item.first_field.text = "Имя"
        surname_info_item.first_field.text = "Фамилия"
        id_info_item.first_field.text = "Id устройства"
        nickname_info_item.first_field.text = "Никнэйм"

        name_info_item.second_field.text = viewModel.userHolder.currentUser()?.name ?: "Василий"
        surname_info_item.second_field.text = viewModel.userHolder.currentUser()?.surname ?: "Пупкин"
        id_info_item.second_field.text = viewModel.userHolder.currentUser()?.deviceId ?: "vasiley-pupkin"
        nickname_info_item.second_field.text = viewModel.userHolder.currentUser()?.login ?: "vPupkin"
    }
}
