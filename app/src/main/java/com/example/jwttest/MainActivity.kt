package com.example.jwttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.jwttest.data.UserPreferences
import com.example.jwttest.home.HomeActivity
import com.example.jwttest.ui.startNewActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)

        userPreferences.authLoginToken.asLiveData().observe(this, Observer {
            val activity = if(it == null) AuthActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)
        })
    }
}
