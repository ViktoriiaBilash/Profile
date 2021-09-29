package com.example.profile

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.profile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var setting : SharedPreferences

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setting = getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

        val user = intent.getStringExtra(USER_NAME)
        binding.nameTextView.text = user
    }

    override fun onDestroy() {
        super.onDestroy()
        val editor = setting.edit()
        editor.clear().apply()
    }
}