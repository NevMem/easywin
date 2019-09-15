package com.example.easywin.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.easywin.R
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.fragment_top_bar.*
import kotlinx.android.synthetic.main.info_item.*

class UserInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        kek.text = "Информация обо мне"

        name_info_item.first_field.text = "Имя"
        surname_info_item.first_field.text = "Фамилия"
        id_info_item.first_field.text = "Id устройства"
        nickname_info_item.first_field.text = "Никнэйм"

        name_info_item.second_field.text = "Василий"
        surname_info_item.second_field.text = "Пупкин"
        id_info_item.second_field.text = "azazazazazazazazazaz"
        nickname_info_item.second_field.text = "vPupkin"
    }
}
