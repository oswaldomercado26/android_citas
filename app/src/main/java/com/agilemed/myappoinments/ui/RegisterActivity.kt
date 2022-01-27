package com.agilemed.myappoinments.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agilemed.myappoinments.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
     tvGoToLogin.setOnClickListener{
         val intent=Intent(this, MainActivity::class.java)
         startActivity(intent)
     }
    }
}