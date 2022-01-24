package com.agilemed.myappoinments

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.agilemed.myappoinments.PreferenceHelper.get
import com.agilemed.myappoinments.PreferenceHelper.set
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val snackbar by lazy {
        Snackbar.make(mainLayout,R.string.press_back_again,Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //shared preferences
        /*val preferences= getSharedPreferences("general", Context.MODE_PRIVATE)
        val session=preferences.getBoolean("active_session",false)
        */
        val preferences=PreferenceHelper.defaultPrefs(this)

        if(preferences["session",false ])
            goToMenuActivity()

        btnLogin.setOnClickListener{
            //validates
            createSessionPreference()
         goToMenuActivity()

        }
        tvGoToRegister.setOnClickListener{
            Toast.makeText(this,getString(R.string.please_fill_your_register_data),Toast.LENGTH_SHORT).show()
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createSessionPreference(){
       /* val preferences= getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putBoolean("session",true)
        editor.apply()*/
        val preferences=PreferenceHelper.defaultPrefs(this)
        preferences["session"]=true

    }

    private fun goToMenuActivity(){
        val intent= Intent(this,MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if(snackbar.isShown)
            super.onBackPressed()
        else
            snackbar.show()
    }
}